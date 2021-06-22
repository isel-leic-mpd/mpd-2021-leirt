package isel.leirt.mpd.weather6.model;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

public class Location {

	private String name;
	private String country;
	private double latitude;
	private double longitude;

	Function<Location, CompletableFuture<Stream<DayInfo>>> forecast;

	public static Location of(double latitude, double longitude) {
		return new Location(null,null,latitude,longitude,null);
	}

	public Location(String name,
	                String country,
	                double latitude,
	                double longitude,
	                Function<Location, CompletableFuture<Stream<DayInfo>>> forecast) {
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


	public CompletableFuture<Stream<DayInfo>> forecast()  {

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
