package isel.leirt.mpd.weather3.dto;

public class AreaWeatherQuery
{
	public final WeatherInfo[] list;

	public AreaWeatherQuery(WeatherInfo[] list) {
		this.list = list;
	}
}
