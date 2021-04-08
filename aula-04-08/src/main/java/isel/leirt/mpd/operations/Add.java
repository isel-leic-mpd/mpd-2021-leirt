package isel.leirt.mpd.operations;

import isel.leirt.mpd.expressions.BinaryOper;
import isel.leirt.mpd.expressions.Expr;

public class Add extends BinaryOper {

	public Add(Expr left, Expr right) {
		super(left,right);
	}

	@Override
	protected String getOperator() {
		return "+";
	}

	@Override
	public double eval() {
		return left.eval()+right.eval();
	}
}
