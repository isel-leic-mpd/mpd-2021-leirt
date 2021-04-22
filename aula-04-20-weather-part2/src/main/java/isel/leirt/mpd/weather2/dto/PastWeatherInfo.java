package isel.leirt.mpd.weather2.dto;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static isel.leirt.mpd.weather2.utils.Utils.*;

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
		return  dateFromUnixTime(dt);
	}

	public LocalTime obsTime() {
		return  timeFromUnixTime(dt);
	}

	public LocalTime sunRise() {
		return timeFromUnixTime(sunrise);
	}

	public LocalTime sunSet() {
		return timeFromUnixTime(sunset);
	}

}