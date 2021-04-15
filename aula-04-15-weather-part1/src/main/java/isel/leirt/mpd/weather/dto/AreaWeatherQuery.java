package isel.leirt.mpd.weather.dto;

public class AreaWeatherQuery
{
	public final WeatherInfo[] list;

	public AreaWeatherQuery(WeatherInfo[] list) {
		this.list = list;
	}
}
