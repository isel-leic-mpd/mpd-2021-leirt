package isel.leirt.mpd.weather5.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ForecastHourlyInfo {
	private long dt;

	private double temp;
	private double feels_like;
	private int humidity;
	@SerializedName("weather")
	private WeatherDescriptionDto[] description;

	public LocalDate observationDate() {
		return Utils.dateFromUnixTime(dt);
	}

	public LocalTime observationTime() {
		return Utils.timeFromUnixTime(dt);
	}

	public LocalDateTime observationDateTime() {
		return Utils.fromUnixTime(dt);
	}


	public double feelsLike() { return feels_like; }

	public double temp() { return temp; }



	public int humidity() { return humidity; }

	public String description() { return description[0].description; }


	@Override
	public String toString() {
		return  "{\n "
			+ "\tobservationDate = " + observationDate() + "\n"
			+ "\tobservationTime= " + observationTime() + "\n"
			+ "\ttemp = " + temp() + "\n"
			+ "\tfeelsLike = " + feelsLike() + "\n"
			+ "\tdescription = " + description() + "\n"
			+ "\thumidity = " + humidity() + "\n"
			+ " }";
	}
}
