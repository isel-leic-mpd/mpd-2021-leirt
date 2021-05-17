package isel.leirt.mpd.weather5.dto;

import java.util.Arrays;

public class PastWeatherInfoQueryDto {
	private PastWeatherInfoDto current;

	private PastWeatherInfoDto[] hourly;

	public PastWeatherInfoDto current() {
		return current;
	}

	public PastWeatherInfoDto[] hourly() {
		return Arrays.copyOf(hourly, hourly.length);

	}

}
