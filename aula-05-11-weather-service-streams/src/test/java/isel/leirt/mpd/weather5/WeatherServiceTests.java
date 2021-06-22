package isel.leirt.mpd.weather5;

import isel.leirt.mpd.weather5.model.DayInfo;
import isel.leirt.mpd.weather5.model.Location;
import isel.leirt.mpd.weather5.model.WeatherInfo;

import isel.leirt.mpd.weather5.queries.MIterable;
import isel.leirt.mpd.weather5.requests.CounterRequest;
import isel.leirt.mpd.weather5.requests.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class WeatherServiceTests {
	@Test
	public void getForecastDetailForLisbonTest() {
		CounterRequest counterReq = new CounterRequest(new HttpRequest());

		OpenWeatherService serv = new OpenWeatherService(new OpenWeatherWebApi(counterReq));
		Supplier<Stream<Location>> locs = serv.search("Lisbon");

		Assert.assertEquals(0, counterReq.getCount());

		Stream<Location> slocs = locs.get();

		Assert.assertEquals(1, counterReq.getCount());

		// get temperature detail for tomorrow at Lisbon
		Supplier<Stream<WeatherInfo>> tomorrowTemps  =
			() ->
			locs.get()
			.filter(l -> l.getCountry().equals("PT"))
			.flatMap(l -> l.forecast())
			.filter(d -> d.getDate().equals(LocalDate.now().plusDays(1)))
			.flatMap(d -> d.temperatures());

		Assert.assertEquals(1, counterReq.getCount());

		System.out.println("WeatherInfo list size: " + tomorrowTemps.get().count());

		tomorrowTemps.get().forEach(System.out::println);

	}


	@Test
	public void getForecastForLisbonTest() {
		OpenWeatherService weather = new OpenWeatherService(new OpenWeatherWebApi());



		Stream<DayInfo> forecastWeather =
			weather.search("Lisbon").get()
			.filter(l -> l.getCountry().equals("PT"))
			.flatMap(	l ->  l.forecast());

		System.out.println("DayInfo list size: " + forecastWeather.count());
		forecastWeather.forEach(System.out::println);

	}

	/*
	@Test
	public void checkTheLocationCreationDoesntInvokeTheDataSourceTest() {

		CounterRequest counterReq =
			new CounterRequest(new HttpRequest());

		OpenWeatherService weather =
			new OpenWeatherService(new OpenWeatherWebApi(counterReq));

		System.out.println("Initial counterReq= " + counterReq.getCount());

		MIterable<Location> locations = weather.search("Lisbon");

		System.out.println("Intermediate counterReq= " + counterReq.getCount());

		MIterable<DayInfo> forecast =
			locations.first().get().forecast();

		System.out.println("Final counterReq= " + counterReq.getCount());

	}
*/
}
