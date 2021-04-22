package isel.leirt.mpd.weather2.dto;

public class Location {
	private final String name;
	private final  double lat, lon;
	private final String country;

	public Location(String name, double lat, double lon, String country) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.country = country;
	}

	public String getName() { return this.name;  }

	public double getLat() { return lat; }

	public double getLon() { return lon; }

	public String getCountry() { return country; }
	///new
	public String toString() {
		return "{ "
			+ "name = " + name
			+ ", lat = " + lat
			+ ", long = " + lon
			+ ", country = " + country
			+ " }";

	}
}