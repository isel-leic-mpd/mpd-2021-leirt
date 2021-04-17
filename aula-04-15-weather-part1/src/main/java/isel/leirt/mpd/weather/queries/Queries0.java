package isel.leirt.mpd.weather.queries;

import isel.leirt.mpd.weather.dto.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class Queries0 {
	public static Iterable<WeatherInfo> getSunnyCities(Iterable<WeatherInfo> cities) {
		List<WeatherInfo> sunny_cities = new ArrayList<>();

		for(WeatherInfo wi: cities) {
			if (wi.description().contains("sky"))
				sunny_cities.add(wi);
		}
		return sunny_cities;

	}
}
