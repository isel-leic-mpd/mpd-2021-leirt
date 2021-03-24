package isel.leirt.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Add  extends BinaryOper {

	public Add(Expr left, Expr right) {
		super(left, right);
	}

	@Override
	protected String getOperator() {
		return "+";
	}

	@Override
	public double eval() {
		return left.eval() + right.eval();
	}

}
