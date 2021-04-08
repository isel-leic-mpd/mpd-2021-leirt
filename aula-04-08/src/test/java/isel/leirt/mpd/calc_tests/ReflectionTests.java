package isel.leirt.mpd.calc_tests;

import isel.leirt.mpd.ReflexUtils;
import isel.leirt.mpd.calc.ExpressionFactory;
import isel.leirt.mpd.expressions.*;
import isel.leirt.mpd.expressions.Number;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionTests {
	@Test
	public void instanceOfTest() {
		Assert.assertFalse(null instanceof Expr);
		Assert.assertTrue(new Number(2) instanceof Expr);
		Expr add = ExpressionFactory.createFrom("Add", new Number(3), new Number(6));
		Assert.assertTrue(add instanceof Expr);
	}


	@Test
	public void ourInstanceOfTest() {
		Assert.assertTrue(ReflexUtils.instanceOf(new Number(2), Expr.class));
		Assert.assertFalse(ReflexUtils.instanceOf(new Number(2), String.class));

		// this assertion fails because our instanceOf method is incomplete. We tell this on next lecture
		Expr add = ExpressionFactory.createFrom("Add", new Number(3), new Number(6));
		Assert.assertTrue(ReflexUtils.instanceOf(add, Expr.class));
	}


}
