package isel.leirt.mpd.optionals;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalTests {

	@Test
	public void getPersonCarWithNoInsurance() {
		String expected = "Unknown";
		Car c = new Car();
		Person p = new Person(c);
		assertEquals(expected, Utils.getCarInsuranceName(Optional.of(p)));
	}
}
