package isel.leirt.mpd.weather.dto;

public class WeatherDescription {
	public final int id;
	public final String description;

	public WeatherDescription(int id, String description) {
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
