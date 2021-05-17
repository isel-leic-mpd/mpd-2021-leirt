package isel.leirt.mpd.weather4.dto;

public class WeatherDescriptionDto {
	public final int id;
	public final String description;

	public WeatherDescriptionDto(int id, String description) {
		this.id = id;
		this.description = description;
	}

	@Override
	public String toString() {
		return  "{ "
			+"id = " + id + ", "
			+ "description = " + "'" + description  + "'"
			+ " }";
	}
}
