package isel.leirt.aula_03_23.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public abstract class BinaryOper implements Expr {

	protected final Expr left;
	protected final Expr right;

	public BinaryOper(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	protected abstract String getOperator();

	@Override
	public void write(Writer writer) {

		try {
			left.write(writer);
			writer.write( getOperator());
			right.write(writer);
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
