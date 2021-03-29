package isel.leirt.aula_03_23.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Mean implements Expr {

	private static String MeanOperator = "mean";

    private Expr[] exprs;

	public Mean(Expr... exprs) {
		// ensure that there are at least one operand to mean
		if (exprs.length == 0)
			throw new IllegalArgumentException("Empty array not allowed");
		this.exprs = Arrays.copyOf(exprs, exprs.length);
	}

	public Mean(List<Expr> exprs) {
		// ensure that there are at least one operand to mean
		if (exprs.size() == 0)
			throw new IllegalArgumentException("Empty list not allowed");
		this.exprs = exprs.toArray( i-> new Expr[i]);
	}


	@Override
	public double eval() {
		double sum = 0.0;
		for(Expr e : exprs)
			sum += e.eval();

		return sum/exprs.length;
	}

	@Override
	public void write(Writer writer) {
		try {

			writer.write(MeanOperator);
			writer.write("( ");

			Iterable<Expr> exprsSeq = Arrays.asList(exprs);
			Iterator<Expr> it = exprsSeq.iterator();

			// process first element
			it.next().write(writer);

			// process remaining elements
			while(it.hasNext()) {
				writer.write(", ");
				it.next().write(writer);
			}

			writer.write(" )");
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
