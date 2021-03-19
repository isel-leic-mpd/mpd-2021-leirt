package isel.leirt.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Mul extends  BinaryOper {


	public Mul(Expr left, Expr right) {
		super(left,right);
	}

	@Override
	protected String getOperator() {
		return "x";
	}

	@Override
	public double eval() {
		return left.eval()*right.eval();
	}


}
