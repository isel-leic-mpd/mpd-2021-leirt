package isel.leirt.mpd.weather3;


import static isel.leirt.mpd.lazyqueries2.Queries.*;

import isel.leirt.mpd.weather3.model.DayInfo;
import isel.leirt.mpd.weather3.model.Location;
import isel.leirt.mpd.weather3.model.WeatherInfo;

import isel.leirt.mpd.weather3.requests.CounterRequest;
import isel.leirt.mpd.weather3.requests.HttpRequest;
import isel.leirt.mpd.weather3.requests.Request;
import org.junit.Test;

import java.io.Reader;
import java.time.LocalDate;
import java.util.function.Function;


public class WeatherServiceTests {
	@Test
	public void getForecastDetailForLisbonTest() {
		OpenWeatherService serv = new OpenWeatherService(new OpenWeatherWebApi());
		Iterable<Location> locs = serv.search("Lisbon");

		Iterable<WeatherInfo> tomorrowTemps  = null; // =...;


		System.out.println("WeatherInfo list size: " + count(tomorrowTemps));
		tomorrowTemps.forEach(System.out::println);

	}

	@Test
	public void getForecastForLisbonTest() {
		OpenWeatherService weather = new OpenWeatherService(new OpenWeatherWebApi());

		Iterable<DayInfo> forecastWeather =

				flatMap(
					filter(
						weather.search("Lisbon"),
						l -> l.getCountry().equals("PT")
					),
					l ->  l.forecast()
				);



		System.out.println("DayInfo list size: " + count(forecastWeather));
		forecastWeather.forEach(System.out::println);

	}

	@Test
	public void checkTheLocationCreationDoesntInvokeTheDataSourceTest() {

		CounterRequest counterReq =
			new CounterRequest(new HttpRequest());

		OpenWeatherService weather =
			new OpenWeatherService(new OpenWeatherWebApi(counterReq));

		System.out.println("Initial counterReq= " + counterReq.getCount());

		Iterable<Location> locations = weather.search("Lisbon");

		System.out.println("Intermediate counterReq= " + counterReq.getCount());

		Location loc =  first(locations).get();

		System.out.println("Intermediate2 counterReq=" + counterReq.getCount());

		Iterable<DayInfo> forecast = loc.forecast();


		System.out.println("Final counterReq= " + counterReq.getCount());

		forecast.forEach(System.out::println);

	}

	@Test
	public void checkTheLocationCreationDoesntInvokeTheDataSourceUsingFunctionalReqCountTest() {
		Request r = new HttpRequest();
		int[] count = {0};
		Function<String, Reader> http = r::get;

		Function<String, String> counter = (p) -> {
			count[0]++;
			return p;
		};

		Function<String, Reader> freq = counter.andThen(http);

		Request counterReq = p -> freq.apply(p);

		OpenWeatherService weather =
			new OpenWeatherService(new OpenWeatherWebApi(counterReq));

		System.out.println("Initial counterReq= " + count[0]);

		Iterable<Location> locations = weather.search("Lisbon");

		System.out.println("Intermediate counterReq= " + count[0]);

		Location loc =  first(locations).get();

		System.out.println("Intermediate2 counterReq=" + count[0]);

		Iterable<DayInfo> forecast = loc.forecast();


		System.out.println("Final counterReq= " + count[0]);

		forecast.forEach(System.out::println);

	}
}
