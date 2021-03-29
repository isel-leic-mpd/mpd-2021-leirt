package isel.leirt.aula_03_23.math_utils.expressions;

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
