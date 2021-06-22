package isel.leirt.mpd.weather6.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static isel.leirt.mpd.weather6.dto.Utils.timeFromUnixTime;

public class PastWeatherInfoDto {
	private long dt;
	private long sunrise;
	private long sunset;
	private double temp;
	private double feels_like;
	private int humidity;

	@SerializedName("weather")
	private WeatherDescriptionDto[] description;

	@Override
	public String toString() {
		return "dateTime= " + obsDateTime()
			+ ", sunrise = " + sunRise()
			+ ", sunset = " + sunSet()

			+ ", temp = " + temp
			+ ", feels_like = " + feels_like
			+ ", humidity = " + humidity
			+ ", weather condition = " + description[0].toString();
	}

	public double getTemp() { return temp; }

	public LocalDateTime obsDateTime() {
		return  Utils.fromUnixTime(dt);
	}

	public LocalTime sunRise() {
		return Utils.timeFromUnixTime(sunrise);
	}

	public LocalTime sunSet() {
		return Utils.timeFromUnixTime(sunset);
	}

}
