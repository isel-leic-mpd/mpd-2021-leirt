package isel.leirt.mpd.weather2.queries.generics.functional_interfaces;

import isel.leirt.mpd.weather2.dto.PastWeatherInfo;

public interface Predicate<T> {
	boolean test(T wi);
}
