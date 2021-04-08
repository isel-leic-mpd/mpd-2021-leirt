import isel.leirt.aula_03_23.math_utils.expressions.Expr;
import isel.leirt.aula_03_23.math_utils.expressions.Number;
import isel.leirt.mpd.reflection_utils.XmlSerializer;
import isel.leirt.mpd.reflection_utils.test_classes.B;
import org.dom4j.Document;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XmlSerializerTests {

	@Test
	public void simpleSerializeTest() {
		B b = new B(10);

		Document doc = XmlSerializer.toXml(b);

		String xmlText = doc.asXML();

		System.out.println(xmlText);

		B newB = (B) XmlSerializer.fromXml(xmlText);

		System.out.println(b);
		System.out.println(newB);


		assertTrue(b instanceof B);
		assertTrue(newB instanceof B);
		Assert.assertEquals(b.getClass(), newB.getClass());

		Assert.assertEquals(B.class , b.getClass());

		Assert.assertEquals(b, newB);
		assertTrue(b.equals(newB));
	}


}
