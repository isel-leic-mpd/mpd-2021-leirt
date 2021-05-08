package isel.leirt.mpd.weather3.dto;

public class AreaWeatherQueryDto
{
	public final WeatherInfoDto[] list;

	public AreaWeatherQueryDto(WeatherInfoDto[] list) {
		this.list = list;
	}
}
