package isel.leirt.mpd.annotations;


import org.w3c.dom.Attr;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflexUtils {
	public static void checkAnnotations(Object obj) {
		Class<?> cls = obj.getClass();

		System.out.println("Class annotations");
		Annotation[] annotations = cls.getAnnotations();

		for(Annotation a : annotations) {
			System.out.println(a.getClass().getSimpleName());
		}

		for(Field f : cls.getDeclaredFields()) {
			Annotation[] fAnnotations = f.getAnnotations();
			System.out.println("Annotations for field " + f.getName());
			for(Annotation a : fAnnotations) {
				Class<? extends Annotation> annotationType = a.annotationType();
				System.out.println(annotationType.getSimpleName());
				Attribute attr = (Attribute) a;

				System.out.println(attr.dbName());
			}
		}
	}
}
