package isel.leirt.mpd.weather4.dto;

public class AreaWeatherQueryDto
{
	public final WeatherInfoDto[] list;

	public AreaWeatherQueryDto(WeatherInfoDto[] list) {
		this.list = list;
	}
}
