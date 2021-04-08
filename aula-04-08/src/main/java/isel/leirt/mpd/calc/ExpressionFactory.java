package isel.leirt.mpd.calc;

import isel.leirt.mpd.exceptions.ExprException;
import isel.leirt.mpd.expressions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpressionFactory {
	// the package where are the .class for new operations
	private static final String OPERS_PKG = "isel.leirt.mpd.operations";

	// the various parts of an operation description of a line of the configuration file
	private static final int DESCRIPTION_PARTS  = 3;
	private static final int NAME_PART = 0;
	private static final int SYMBOL_PART = 1;
	private static final int ARITY_PART = 2;


	// factory for an operation descriptor given the line description
	private static OpDescriptor fromDescription(String opStr) {
		String[] parts = opStr.split(",");
		if (parts.length != DESCRIPTION_PARTS)
			throw new IllegalStateException("Bad operation registration!");
		parts = Arrays.stream(parts).map(s -> s.trim())
			.toArray(n -> new String[n]);

		int arity = Integer.parseInt(parts[ARITY_PART]);
		OpDescriptor opd = new  OpDescriptor(parts[NAME_PART], parts[SYMBOL_PART], arity);
		return opd;
	}

	// inner class to represent a new operation descriptor
	private static class OpDescriptor {
		public final String name;
		public final String opSymb;
		public final int arity;


		private  OpDescriptor(String name, String opSymb, int arity) {
			this.name = name;
			this.opSymb = opSymb;
			this.arity = arity;
		}

		private void show() {
			System.out.printf("Name: %s; Symbol: %s;  Arity: %d\n", name, opSymb, arity);
		}
	}

	// the map of (operation name, operation descriptor) pairs
	private static final Map<String,OpDescriptor> operations;

	// static constructor for loading the configuration resource file into the hashmap
	static {
		operations = new HashMap<>();
		loadOpRegistrations();
	}

	/**
	 * Retrieve API-KEY from resources
	 * @return BufferedReader
	 */
	public static BufferedReader getRegistReader() throws IOException {
		URL registFile =
			ClassLoader.getSystemResource("expression_types.txt");
		BufferedReader reader =
			new BufferedReader(new InputStreamReader(registFile.openStream()));
		return reader;
	}

	/**
	 * Load the  configuration file resource
	 */
	private static void loadOpRegistrations() {
		try(BufferedReader reader = getRegistReader()) {
			String line;
			while((line= reader.readLine()) != null) {
				if (!line.startsWith("#")) { // ignore comments
					OpDescriptor opd = fromDescription(line);
					if (operations.containsKey(opd.name))
						throw new IllegalStateException("Operation already registered!");
					operations.put(opd.name, opd);
				}
			}
		}
		catch(IOException e) {
			System.out.println("I/O error loading operation registrations!");
			throw new UncheckedIOException(e);
		}
		catch(Exception e) {
			System.out.println("Generic error loading operation registrations!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * just for debug purposes
	 */
	public static void showRegistrations() {
		for(OpDescriptor opd: operations.values())
			opd.show();
	}

	/**
	 * This is the factory methos for retuning a new Expr given is name and
	 * construction arguments
	 * @param name the operation name
	 * @param args the expressions passed to constructor (the size must match the operation arity)
	 * @return a new expression of the given type
	 */
	public static Expr createFrom(String name, Expr... args)  {
		try {
			OpDescriptor opd = operations.get(name);

			if (opd == null)
				throw new ExprException("Inexistent operation name");

			// check if arity matches the args length
			if (opd.arity != args.length)
				throw new ExprException("Try create an expression with an invalid number of arguments");

			// get the operation class fullName
			String clsName = OPERS_PKG + "." + opd.name;

			// get the operation class representant
			Class<?> expClass = Class.forName(clsName);

			// check if the operation class implements Expr
			// Our ReflexUtils.instanceOf should do the same since
			// a class extending from other class that implements an interface A
			// is not marked as implementing an interface A (just the superclass is marked)
			boolean isExpr = false;
			Class<?> cls = expClass;
			while(cls != null && !isExpr) {
				for (Class<?> i : cls.getInterfaces()) {
					if (i == Expr.class) {
						isExpr = true;
						break;
					}
				}
				cls = cls.getSuperclass();
			}
			if (!isExpr)
				throw new ExprException("Operation class is not an Expr");

			// prepare the necessary constructor parameters
			Class<?>[] parms = new Class[args.length];

			Arrays.fill(parms, Expr.class);
			// get the necessary constructor
			Constructor<?> c = expClass.getConstructor(parms);

			// create the new expr object
			Expr expr  = (Expr) c.newInstance((Object[])args);
			return  expr;
		}
		catch(ClassNotFoundException e) {
			// Class.forName falhou
			throw new ExprException("Cannot find operation class");
		}
		catch(NoSuchMethodException e) {
			// expClass.getConstructor falhou
			throw new ExprException("Cannot find appropriate constructor");
		}
		catch(InstantiationException | InvocationTargetException | IllegalAccessException e) {
			// Invocação do constructor - c.newInstance((Object[])args) falhou
			throw new ExprException("Cannot instantiate expression");
		}
	}
}
