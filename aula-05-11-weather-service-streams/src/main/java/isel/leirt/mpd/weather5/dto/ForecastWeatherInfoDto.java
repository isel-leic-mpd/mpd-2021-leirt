package isel.leirt.mpd.weather5.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import static isel.leirt.mpd.weather5.dto.Utils.timeFromUnixTime;

public class ForecastWeatherInfoDto {
	private long dt;
	private long sunrise;
	private long sunset;
	private double moon_phase;
	private long moonrise;
	private long moonset;
	private TempDto temp;
	private WeatherDescriptionDto[] weather;

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

	public double maxTemp() { return temp.max; }

	public double minTemp() { return temp.min; }

	public double moonPhase() {
		return moon_phase;
	}
}
