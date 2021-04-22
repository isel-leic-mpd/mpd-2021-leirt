package isel.leirt.mpd.weather2.dto;

import java.util.Arrays;

public class PastWeatherInfoQuery {
	public  PastWeatherInfo current;

	private PastWeatherInfo[] hourly;

	public PastWeatherInfo current() {
		return current;
	}

	public PastWeatherInfo[] hourly() {
		return Arrays.copyOf(hourly, hourly.length);

	}

}