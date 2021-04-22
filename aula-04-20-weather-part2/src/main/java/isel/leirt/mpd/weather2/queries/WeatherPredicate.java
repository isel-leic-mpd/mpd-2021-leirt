package isel.leirt.mpd.weather2.queries;

import isel.leirt.mpd.weather2.dto.WeatherInfo;

public interface WeatherPredicate {
	boolean test(WeatherInfo wi);
}
