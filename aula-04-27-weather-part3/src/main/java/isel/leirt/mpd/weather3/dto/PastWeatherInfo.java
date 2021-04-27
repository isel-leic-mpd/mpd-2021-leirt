package isel.leirt.mpd.weather3.dto;



import isel.leirt.mpd.weather3.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class PastWeatherInfo {
	private long dt;
	private long sunrise;
	private long sunset;
	private double temp;
	private double feels_like;
	private int humidity;
	private WeatherDescription[] weather;

	@Override
	public String toString() {
		return "date= " + obsDate()
			+ ", time = " + obsTime()
			+ ", sunrise = " + sunRise()
			+ ", sunset = " + sunSet()

			+ ", temp = " + temp
			+ ", feels_like = " + feels_like
			+ ", humidity = " + humidity
			+ ", weather condition = " + weather[0].toString();
	}

	public double getTemp() { return temp; }

	public LocalDate obsDate() {
		return  Utils.dateFromUnixTime(dt);
	}

	public LocalTime obsTime() {
		return  Utils.timeFromUnixTime(dt);
	}

	public LocalTime sunRise() {
		return Utils.timeFromUnixTime(sunrise);
	}

	public LocalTime sunSet() {
		return Utils.timeFromUnixTime(sunset);
	}

}