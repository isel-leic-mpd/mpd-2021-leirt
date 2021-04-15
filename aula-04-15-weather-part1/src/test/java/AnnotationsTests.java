import isel.leirt.mpd.annotations.Person;
import isel.leirt.mpd.annotations.ReflexUtils;
import org.junit.Test;

public class AnnotationsTests {
	@Test
	public void checkAnnotationsTest() {
		ReflexUtils.checkAnnotations(new Person());
	}
}
