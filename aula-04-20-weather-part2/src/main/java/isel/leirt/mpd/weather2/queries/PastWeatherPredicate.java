package isel.leirt.mpd.weather2.queries;

import isel.leirt.mpd.weather2.dto.PastWeatherInfo;
import isel.leirt.mpd.weather2.dto.WeatherInfo;

public interface PastWeatherPredicate {
	boolean test(PastWeatherInfo wi);
}
