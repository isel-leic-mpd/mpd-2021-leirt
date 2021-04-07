package isel.leirt.mpd.reflection_utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XmlSerializer {

	private static Field[] getAllFields(Object obj) {
		LinkedList<Field> fields = new LinkedList<>();
		Class<?> c = obj.getClass();
		while(c != Object.class) {
			Field[] flds = c.getDeclaredFields();
			for(Field f : flds) {
				fields.addFirst(f);
			}
			c = c.getSuperclass();
		}
		return fields.toArray(n -> new Field[n]);
	}

	private static void addField(Element parent, Field f, Object obj) {
		try {
			Element child = parent.addElement(f.getName());
			child.addAttribute("type", f.getType().getName());
			child.addAttribute("declaringClass", f.getDeclaringClass().getName());
			f.setAccessible(true);
			Object value = f.get(obj);

			child.addText((value == null) ? "null" : value.toString());

		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Document toXml(Object obj) {
		Document doc = DocumentHelper.createDocument();
		if (obj == null) throw new IllegalArgumentException("obj is null!");

		Class<?> cls = obj.getClass();

		Element elem = doc.addElement("object");
		elem.addAttribute("type", cls.getName());
		Field[] fields = getAllFields(obj);
		for(Field f : fields) {
			addField(elem, f, obj);
		}
		return doc;
	}

	private static void setField(Element xmlField, Object obj) throws Exception {
		String declaredClassName = xmlField.attributeValue("declaringClass");
		Class<?> fieldDeclaredClass = Class.forName(declaredClassName);
		String fieldName = xmlField.getName();
		Field f = fieldDeclaredClass.getDeclaredField(fieldName);

		Object value = null;
		String strValue = xmlField.getStringValue();
		Class<?> fieldType = f.getType();
		if (fieldType == int.class) {
			value = Integer.parseInt(strValue);
		}
		else if (fieldType == double.class) {
			value = Double.parseDouble(strValue);
		}
		else if (fieldType == boolean.class) {
			value = Boolean.parseBoolean(strValue);
		}
		else {
			if (!strValue.equals("null"))
				value = strValue;
		}
		f.setAccessible(true);
		f.set(obj, value );
	}

	public static Object fromXml(String xmlText) {
		try {
			Document doc = DocumentHelper.parseText(xmlText);
			Element root = doc.getRootElement();
		    String className =  root.attributeValue("type");
		    Class<?> cls = Class.forName(className);
			Constructor<?> ctor = cls.getConstructor();
			Object newObj = ctor.newInstance();

			for(Element c : root.elements()) {
				setField(c, newObj);
			}

			return newObj;

		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
