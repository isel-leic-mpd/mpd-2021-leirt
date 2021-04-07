import isel.leirt.mpd.reflection_utils.XmlSerializer;
import isel.leirt.mpd.reflection_utils.test_classes.B;
import org.dom4j.Document;
import org.junit.Assert;
import org.junit.Test;

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


		Assert.assertTrue(b instanceof B);
		Assert.assertTrue(newB instanceof B);
		Assert.assertEquals(b.getClass(), newB.getClass());

		Assert.assertEquals(B.class , b.getClass());

		Assert.assertEquals(b, newB);
		Assert.assertTrue(b.equals(newB));
	}
}
