package isel.leirt.mpd.weather5.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WeatherInfo {
	private final LocalDateTime time;
	private final double tempC;
	private final String description;
	private final int  humidity;
	private final double feelsLikeC;

	public WeatherInfo(LocalDateTime time, double tempC, String description, int humidity, double feelsLikeC) {
		this.time = time;
		this.tempC = tempC;
		this.description = description;
		this.humidity = humidity;
		this.feelsLikeC = feelsLikeC;
	}

	public LocalDateTime getLocalTime() { return time; }

	public double getTempC() { return tempC; }
	public String getDescription() { return description; }
	public int getHumidity() { return humidity; }
	public double getFeelsLikeC() { return feelsLikeC; }


	@Override
	public  String toString() {
		return "{" +
			"  time=" + time +
			", tempC=" + tempC +
			", '" + description + '\'' +
			", humidity=" + humidity +
			", feelsLikeC=" + feelsLikeC +
			'}';
	}
}
