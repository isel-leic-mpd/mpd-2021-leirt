package isel.leirt.aula_03_23.math_utils.expressions;

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
