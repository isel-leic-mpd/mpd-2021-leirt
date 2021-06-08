package isel.leirt.mpd.nulls;

public class Utils {

	public static String getCarInsuranceName(Person person) {
		if (person != null ) {
			Car car = person.getCar();
			if (car != null) {
				Insurance insurance = car.getInsurance();
				if (insurance != null) {
					return  insurance.getName();
				}
			}
		}
		return "Unknown";
	}
}
