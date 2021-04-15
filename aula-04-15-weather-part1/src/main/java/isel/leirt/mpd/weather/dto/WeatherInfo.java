package isel.leirt.mpd.weather.dto;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class WeatherInfo {
	private final WeatherDescription[] weather;
	private final Weather  main;
	private final long dt;
	private final String name;
	private final int timezone;

	public WeatherInfo(WeatherDescription[] weather, Weather main, long dt, String name, int timezone) {
		this.weather = weather;
		this.main = main;
		this.name = name;
		this.dt = dt;
		this.timezone = timezone;
	}

	private  ZoneOffset offset() {

		if (timezone != 0) {
			return ZoneOffset.of((timezone > 0 ? "+" : "-" )+ timezone / 3600);
		}
	    return ZoneOffset.UTC;
	}

	public LocalDate observationDate() {
		return Utils.dateFromUnixTime(dt);
	}

	public String observationLocal() {
		return name;
	}

	public Weather weather() {
		return main;
	}

	public WeatherDescription description() {
		return weather[0];
	}

	@Override
	public String toString() {
		return  "{\n "
			+ "\tobservationDate = " + observationDate() + "\n"
			+ "\tobservationLocal = " + observationLocal() + "\n"
			+ "\tdescription = " + description() + "\n"
			+ "\tweather = " + weather() + "\n"
			+ " }";
	}
}
