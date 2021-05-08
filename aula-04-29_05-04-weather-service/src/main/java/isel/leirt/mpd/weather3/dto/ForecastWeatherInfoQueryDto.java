package isel.leirt.mpd.weather3.dto;

import java.util.Arrays;

public class ForecastWeatherInfoQueryDto {
	private ForecastWeatherInfoDto[] daily;
	private ForecastHourlyInfo[] hourly;
	public ForecastWeatherInfoDto[] getForecastInfo() {
		return Arrays.copyOf(daily, daily.length);
	}
	public ForecastHourlyInfo[] getHourlyInfo() {
	    return Arrays.copyOf(hourly, hourly.length);
	}
}
