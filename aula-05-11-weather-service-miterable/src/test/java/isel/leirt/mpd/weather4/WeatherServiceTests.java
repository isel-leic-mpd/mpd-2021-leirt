package isel.leirt.mpd.weather4;



import static isel.leirt.mpd.weather4.queries.MIterable.*;

import isel.leirt.mpd.weather4.OpenWeatherService;
import isel.leirt.mpd.weather4.OpenWeatherWebApi;
import isel.leirt.mpd.weather4.model.DayInfo;
import isel.leirt.mpd.weather4.model.Location;
import isel.leirt.mpd.weather4.model.WeatherInfo;

import isel.leirt.mpd.weather4.queries.MIterable;
import isel.leirt.mpd.weather4.requests.CounterRequest;
import isel.leirt.mpd.weather4.requests.HttpRequest;
import org.junit.Test;


public class WeatherServiceTests {
	@Test
	public void getForecastDetailForLisbonTest() {
		OpenWeatherService serv = new OpenWeatherService(new OpenWeatherWebApi());
		MIterable<Location> locs = serv.search("Lisbon");

		MIterable<WeatherInfo> tomorrowTemps  = null; // =...;


		System.out.println("WeatherInfo list size: " + tomorrowTemps.count());
		tomorrowTemps.forEach(System.out::println);

	}

	@Test
	public void getForecastForLisbonTest() {
		OpenWeatherService weather = new OpenWeatherService(new OpenWeatherWebApi());

		/* Old way
		Iterable<DayInfo> forecastWeather =

				flatMap(
					filter(
						weather.search("Lisbon"),
						l -> l.getCountry().equals("PT")
					),
					l ->  l.forecast()
				);

		*/

		// new way
		MIterable<DayInfo> forecastWeather =
			weather.search("Lisbon")
			.filter(l -> l.getCountry().equals("PT"))
			.flatMap(	l ->  l.forecast());

		System.out.println("DayInfo list size: " + forecastWeather.count());
		forecastWeather.forEach(System.out::println);

	}

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
			locations
			.filter(l -> l.getCountry().equals("PT") )
			.flatMap(l -> l.forecast());

		System.out.println("Final counterReq= " + counterReq.getCount());

	}

}
