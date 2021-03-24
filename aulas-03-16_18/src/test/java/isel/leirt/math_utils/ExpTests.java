package isel.leirt.math_utils;

import isel.leirt.math_utils.expressions.*;

import static org.junit.Assert.*;

import isel.leirt.math_utils.expressions.Number;
import org.junit.Test;

import java.io.StringWriter;

public class ExpTests {

	@Test
	public void simple_add_numbers_test() {
		Expr add = new Add(
						new Number(3),
					    new Number(5)
				   );
		assertEquals(8.0, add.eval(), 0.01);
		StringWriter writer = new StringWriter();
		add.write(writer);

		System.out.println(writer.toString());

	}

	@Test
	public void simple_mean_test() {

		double expected = (((double)3 +4) + (2 *5 ) + ((3 +4) + (2 *5 )))/3;

		Expr exp1 = new Add( new Number(3), new Number(4));
		Expr exp2 = new Mul(new Number(2), new Number(5));
		Expr exp3 = new Add(exp1, exp2);

		Expr mean = new Mean(exp1, exp2, exp3);

		assertEquals(expected, mean.eval(), 0.0001);

		StringWriter sw = new StringWriter();
		mean.write(sw);
		System.out.println(sw.toString());

	}
}
