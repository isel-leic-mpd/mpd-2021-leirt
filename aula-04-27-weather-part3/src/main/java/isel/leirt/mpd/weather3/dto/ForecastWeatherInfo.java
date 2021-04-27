package isel.leirt.mpd.weather3.dto;

import isel.leirt.mpd.weather3.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

import static isel.leirt.mpd.weather3.utils.Utils.timeFromUnixTime;

public class ForecastWeatherInfo {
	private long dt;
	private long sunrise;
	private long sunset;
	private double moon_phase;
	private long moonrise;
	private long moonset;
	private Temp temp;
	private WeatherDescription[] weather;

	@Override
	public String toString() {
		return "date= " + obsDate()
			+ ", sunrise = " + Utils.timeFromUnixTime(sunrise)
			+ ", sunset = " + Utils.timeFromUnixTime(sunset)
			+ ", moon_phase = " + moon_phase
			+ ", moonrise = " + Utils.timeFromUnixTime( moonrise)
			+ ", moonset = " + Utils.timeFromUnixTime(moonset)
			+ ", maxTemp = " + temp.max
			+ ", minTemp = " + temp.min
			+ ", weather condition = " + weather[0].toString();
	}

	public LocalDate obsDate() {
		return  Utils.dateFromUnixTime(dt);
	}

	public LocalTime sunRise() {
		return Utils.timeFromUnixTime(sunrise);
	}

	public LocalTime sunSet() {
		return Utils.timeFromUnixTime(sunset);
	}

	public LocalTime moonRise() {
		return Utils.timeFromUnixTime(moonrise);
	}

	public LocalTime moonSet() {
		return Utils.timeFromUnixTime(moonset);
	}

	public double moonPhase() {
		return moon_phase;
	}
}
