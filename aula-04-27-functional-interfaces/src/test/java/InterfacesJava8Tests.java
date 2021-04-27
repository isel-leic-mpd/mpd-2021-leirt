import org.junit.Test;

public class InterfacesJava8Tests {

	@Test
	public void callInterMethodTest() {
		I1 i1 = new C1();

		i1.dm1();
	}

	@Test
	public void callStaticMethodOnInterfaceTest() {
		I1 i1 = new C1();

		I1.sm1();
	}
}
