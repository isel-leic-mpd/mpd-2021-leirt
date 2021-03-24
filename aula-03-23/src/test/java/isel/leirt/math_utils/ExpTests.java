package isel.leirt.math_utils;

import isel.leirt.exceptions.CircularDependencyException;
import isel.leirt.math_utils.expressions.*;

import static org.junit.Assert.*;

import isel.leirt.math_utils.expressions.Number;
import isel.leirt.math_utils.notifications.Subscriber;
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

	@Test
	public void simple_var_test() {
		Var a = new Var("a",
			new Add(
					new Number(3),
					new Number(6)
			));

		StringWriter sw = new StringWriter();
		a.write(sw);
		System.out.println(sw.toString() + "= " + a.eval());
	}

	@Test(expected= CircularDependencyException.class)
	public void multiple_var_test() {
		Var a = new Var("a",
			new Add(
				new Number(3),
				new Number(6)
			));

		Var b = new Var ("b",
			new Add(
				new Number(3),
				a
			));

		a.set(b);

		StringWriter sw = new StringWriter();
		a.write(sw);
		System.out.println(sw.toString() + "= " + a.eval());
	}

	private static class VariableSubscriber implements Subscriber {
		private final Var pubVar;
		private double currValState;

		public VariableSubscriber(Var pubVar) {
			this.pubVar = pubVar;
		}
		public void onChange() {
			currValState = pubVar.eval();
			System.out.println("Variable changed to: " + currValState);
		}

		public double getState() {
			return currValState;
		}
	}

	@Test
	public void var_notification_simple_test() {
		double expected = 23.0;

		Var a = new Var("a",
			new Add(
				new Number(3),
				new Number(6)
			));

		VariableSubscriber subs1 = new VariableSubscriber(a);
		VariableSubscriber subs2 = new VariableSubscriber(a);
		a.subscribe(subs1);
		a.subscribe(subs2);


		a.set(new Number(expected));

		assertEquals(expected, subs1.currValState, 0.0001);
		assertEquals(expected, subs2.currValState, 0.0001);

	}

	private void checkObject(Object o) {
		Class<?> cls = o.getClass();

		System.out.println(cls.getName());
	}

	@Test
	public void first_reflection_test() {
		Number n = new Number(2);
		checkObject(n);

	}
}
