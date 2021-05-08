package isel.leirt.mpd.weather3.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class WeatherInfoDto {
	@SerializedName("weather")
	private final WeatherDescriptionDto[] descriptors;

	@SerializedName("main")
	private final WeatherDto weather;
	private final long dt;
	private final String name;
	private final int timezone;

	public WeatherInfoDto(WeatherDescriptionDto[] descriptors, WeatherDto weather, long dt, String name, int timezone) {
		this.descriptors = descriptors;
		this.weather = weather;
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

	public LocalTime observationTime() {
		return Utils.timeFromUnixTime(dt);
	}

	public String observationLocal() {
		return name;
	}

	private WeatherDto weather() { return weather; }

	public double feelsLike() { return weather.feels_like; }

	public double temp() { return weather.temp; }

	public double minTemp() { return weather.temp_min; }

	public double maxTemp() { return weather.temp_max; }

	public int humidity() { return weather.humidity; }

	public String description() { return descriptors[0].description; }

	public int descriptionId() { return descriptors[0].id; }

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
