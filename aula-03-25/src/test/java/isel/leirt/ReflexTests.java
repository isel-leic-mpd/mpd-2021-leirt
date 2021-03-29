package isel.leirt;

import isel.leirt.aula_03_23.math_utils.expressions.Add;
import isel.leirt.aula_03_23.math_utils.expressions.Expr;
import isel.leirt.aula_03_23.math_utils.expressions.Number;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflexTests {
	private void checkObject(Object o) {
		Class<?> cls = o.getClass();

		System.out.println(cls.getName());
	}

	private static Field[] getAllfields(Object obj) {
		List<Field> fields = new ArrayList<>();
		Class<?> c = obj.getClass();
		while(c != null) {
			Field[] flds = c.getDeclaredFields();
			for(Field f : flds) {
				fields.add(f);
			}
			c = c.getSuperclass();
		}
		return fields.toArray(n -> new Field[n]);
	}

	private void showObjectFields(Object o) {
		Class<?> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();

		for(Field f : fields) {
			String s = String.format("%s:%s in %s", f.getName(), f.getType().getSimpleName(), f.getDeclaringClass().getSimpleName());
			System.out.println(s);
		}
		System.out.println(cls.getName());
	}

	@Test
	public void first_reflection_test() {
		Class<Number> cls = Number.class;

		Number n = new Number(3);

		checkObject(n);
	}

	@Test
	public void get_fields_reflection_test() {

		Expr a = new Add( new Number(3), new Number(7));


		Field[] fields = getAllfields(a);

		for(Field f : fields) {
			String s = String.format("%s:%s in %s", f.getName(), f.getType().getSimpleName(), f.getDeclaringClass().getSimpleName());
			System.out.println(s);
		}
	}
}
