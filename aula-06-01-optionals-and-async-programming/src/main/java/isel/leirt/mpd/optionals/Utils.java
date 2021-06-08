package isel.leirt.mpd.optionals;


import java.util.Optional;

public class Utils {
	public static String getCarInsuranceName(Optional<Person> person) {
		return
				person
				.flatMap(p -> p.getCar())
				.flatMap(c -> c.getInsurance())
				.map(insurance -> insurance.getName())
				.orElse("Unknown");
	}
}
