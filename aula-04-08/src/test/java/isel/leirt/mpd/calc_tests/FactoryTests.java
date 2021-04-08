package isel.leirt.mpd.calc_tests;

import isel.leirt.mpd.expressions.Expr;
import isel.leirt.mpd.expressions.Number;
import org.junit.Test;

import static isel.leirt.mpd.calc.ExpressionFactory.createFrom;

public class FactoryTests {

	@Test
	public void create_add_operation_by_name_test() {
		Expr add = createFrom("Add", new Number(3), new Number(7));
		System.out.println(add.eval());
	}

	@Test(expected=RuntimeException.class)
	public void create_non_existent_operation_by_name_test() {
		Expr mul = createFrom("NewMul", new Number(3), new Number(7));
		System.out.println(mul.eval());
	}

	@Test(expected=RuntimeException.class)
	public void create_add_with_a_wrong_number_of_arguments_test() {
		Expr mul = createFrom("NewMul", new Number(3), new Number(7), new Number(5));
		System.out.println(mul.eval());
	}
}
