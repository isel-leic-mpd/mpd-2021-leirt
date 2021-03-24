package isel.leirt.math_utils.expressions;

import isel.leirt.exceptions.CircularDependencyException;
import isel.leirt.math_utils.notifications.Publisher;
import isel.leirt.math_utils.notifications.Subscriber;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class Var implements Expr, Publisher {

	private final List<Subscriber> subscribers;

	private final String name;
	private Expr expr;
	private boolean justPassed;

	public Var(String name, Expr initial) {
		this.name = name;
		this.expr = initial;

		subscribers = new LinkedList<>();
	}

	@Override
	public double eval() {
		if (justPassed)
			throw new CircularDependencyException();
		return expr.eval();
	}

	@Override
	public void write(Writer writer) {
		try {
			writer.write(name);
			writer.write(":");
			expr.write(writer);
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void set(Expr expr) {
		justPassed = true;
		try {
			expr.eval();
		}
		finally {
			justPassed = false;
		}
		this.expr = expr;

		// inform subscribers of the variable change
		notifyChange();
	}

	public Expr get() {
		return expr;
	}

	@Override
	public void subscribe(Subscriber s) {
		subscribers.add(s);
	}

	@Override
	public void unsubscribe(Subscriber s) {
		subscribers.remove(s);
	}

	@Override
	public void notifyChange() {
		for(Subscriber s: subscribers)
			s.onChange();
	}
}
