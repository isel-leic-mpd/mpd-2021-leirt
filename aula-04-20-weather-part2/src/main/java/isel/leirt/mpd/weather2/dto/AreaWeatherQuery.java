package isel.leirt.mpd.weather2.dto;

public class AreaWeatherQuery
{
	public final WeatherInfo[] list;

	public AreaWeatherQuery(WeatherInfo[] list) {
		this.list = list;
	}
}
