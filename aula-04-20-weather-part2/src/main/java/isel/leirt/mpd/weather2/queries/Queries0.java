package isel.leirt.mpd.weather2.queries;

import isel.leirt.mpd.weather2.dto.PastWeatherInfo;
import isel.leirt.mpd.weather2.dto.WeatherInfo;

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


	public static Iterable<WeatherInfo> getRainyCities(Iterable<WeatherInfo> cities) {
		List<WeatherInfo> rainy_cities = new ArrayList<>();

		for(WeatherInfo wi: cities) {
			if (wi.description().contains("rain"))
				rainy_cities.add(wi);
		}
		return rainy_cities;

	}

	public static Iterable<WeatherInfo> getWeatherFromDescription(
		Iterable<WeatherInfo> cities, String key) {
		List<WeatherInfo> rainy_cities = new ArrayList<>();

		for(WeatherInfo wi: cities) {
			if (wi.description().contains(key))
				rainy_cities.add(wi);
		}
		return rainy_cities;

	}

	public static Iterable<WeatherInfo> filter(
		Iterable<WeatherInfo> winfos, WeatherPredicate pred ) {
		List<WeatherInfo>  accepted_winfos = new ArrayList<>();

		for(WeatherInfo wi: winfos) {
			if ( pred.test(wi) /* wi satisfaz o critério de pesquisa */)
				accepted_winfos.add(wi);
		}
		return accepted_winfos;
	}

	public static Iterable<PastWeatherInfo> pastWeatherInfoFilter(
		Iterable<PastWeatherInfo> winfos, PastWeatherPredicate pred ) {
		List<PastWeatherInfo>  accepted_winfos = new ArrayList<>();

		for(PastWeatherInfo wi: winfos) {
			if ( pred.test(wi) /* wi satisfaz o critério de pesquisa */)
				accepted_winfos.add(wi);
		}
		return accepted_winfos;
	}


	public static Iterable<Double> toTemperatures(
		Iterable<WeatherInfo> winfos ) {
		List<Double> temps = new ArrayList<>();

		for(WeatherInfo wi: winfos) {
			temps.add(wi.temp());
		}
		return temps;
	}

	public static Iterable<Double> weatherInfoToDoubles(
		Iterable<WeatherInfo> winfos, WeatherInfoToDouble mapper) {

		List<Double> temps = new ArrayList<>();

		for(WeatherInfo wi: winfos) {
			temps.add(mapper.apply(wi));
		}
		return temps;
	}

	public static Iterable<Double> pastWeatherInfoToDoubles(
		Iterable<PastWeatherInfo> winfos, PastWeatherInfoToDouble mapper) {

		List<Double> temps = new ArrayList<>();

		for(PastWeatherInfo wi: winfos) {
			temps.add(mapper.apply(wi));
		}
		return temps;
	}

	// terminal operations

	public static long count(Iterable<PastWeatherInfo> winfos) {
		long c = 0;
		for(PastWeatherInfo pwi : winfos)  {
			c++;
		}
		return c;
	}

	public static double maxDouble(Iterable<Double> doubles) {
		double max = Double.MIN_VALUE;

		for(double d : doubles)  {
			if (d > max) max = d;
		}
		return max;
	}

}
