package data;

public class Address implements Comparable<Address> {
	public final String city;
	public final String zipCode;

	public Address(String city, String zipCode) {
		this.city = city;
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}


	@Override
	public int compareTo(Address o) {
		return city.compareTo(o.city);
	}
}
