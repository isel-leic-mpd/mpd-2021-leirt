package isel.leirt.mpd.weather4.model;

import isel.leirt.mpd.weather4.queries.MIterable;

import java.util.function.Function;

public class Location {

	private String name;
	private String country;
	private double latitude;
	private double longitude;

	Function<Location, MIterable<DayInfo>> forecast;

	public Location(String name,
	                String country,
	                double latitude,
	                double longitude,
	                Function<Location, MIterable<DayInfo>> forecast) {
		this.name = name;
		this.country = country;

		this.latitude = latitude;
		this.longitude = longitude;
		this.forecast = forecast;
	}

	// acessors
	public String getName()         { return name; }
	public String getCountry()      { return country; }
	public double getLatitude()     { return latitude; }
	public double getLongitude()    { return longitude; }


	public MIterable<DayInfo> forecast()  {
		return forecast.apply(this);
	}

	@Override
	public String toString() {
		return "{"
			+ name
			+ ", country=" + country
			+ ", latitude=" + latitude
			+ ", longitude=" + longitude
			+ "}";
	}

}
