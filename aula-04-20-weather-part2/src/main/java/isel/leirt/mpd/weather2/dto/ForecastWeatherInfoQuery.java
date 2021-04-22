package isel.leirt.mpd.weather2.dto;

import java.util.Arrays;

public class ForecastWeatherInfoQuery {
	private ForecastWeatherInfo[] daily;

	public ForecastWeatherInfo[] getForecastInfo() {
		return Arrays.copyOf(daily, daily.length);
	}
}
