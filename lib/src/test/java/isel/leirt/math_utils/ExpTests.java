package isel.leirt.math_utils;

import isel.leirt.math_utils.expressions.Add;
import isel.leirt.math_utils.expressions.Expr;
import isel.leirt.math_utils.expressions.Number;
import static org.junit.Assert.*;
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
}
