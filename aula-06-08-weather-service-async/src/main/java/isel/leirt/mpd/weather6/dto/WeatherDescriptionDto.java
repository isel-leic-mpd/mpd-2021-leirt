package isel.leirt.mpd.weather6.dto;

public class WeatherDescriptionDto {
	public final int id;
	public final String description;
	public final String  icon;
	public WeatherDescriptionDto(int id, String description, String icon) {
		this.id = id;
		this.description = description;
		this.icon = icon;
	}

	@Override
	public String toString() {
		return  "{ "
			+"id = " + id + ", "
			+ "description = " + "'" + description  + "'"
			+ " }";
	}
}
