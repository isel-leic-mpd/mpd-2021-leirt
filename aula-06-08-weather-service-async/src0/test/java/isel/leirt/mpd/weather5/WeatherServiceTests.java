package isel.leirt.mpd.weather5;

import isel.leirt.mpd.weather6.OpenWeatherService;
import isel.leirt.mpd.weather6.OpenWeatherWebApi;
import isel.leirt.mpd.weather6.model.DayInfo;
import isel.leirt.mpd.weather6.model.Location;
import isel.leirt.mpd.weather6.model.WeatherInfo;

import isel.leirt.mpd.weather6.requests.CounterRequest;
import isel.leirt.mpd.weather6.requests.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class WeatherServiceTests {

	private static CompletableFuture<Stream<DayInfo>> getForecastForPortugalCity(String location,
                                                                             OpenWeatherService service)  {

		return service.search(location)
				.thenApply(s ->
					s.filter(l -> l.getCountry().equals("PT"))
					.findFirst()
					.get())
				.thenCompose(loc -> loc.forecast());
	}

	private static CompletableFuture<Stream<WeatherInfo>> getForecastDetailForLisbon(OpenWeatherService service) {
		var dayInfos =
			getForecastForPortugalCity("Lisbon", service)
			.thenApply( s -> s.filter(d -> d.getDate().equals(LocalDate.now().plusDays(1))));



		return dayInfos
		.thenCompose(sdays ->  {
			  /*
			    var a =
				    sdays
					    .map( d -> d.temperatures())
					    .flatMap( cf -> cf.join())
					    .toArray(sz -> new WeatherInfo[sz]);
				return CompletableFuture.completedFuture(Arrays.stream(a));
				*/
			 CompletableFuture<Stream<WeatherInfo>>[] winfos=
				 sdays
				 .map( d -> d.temperatures())
				 .toArray(sz -> new CompletableFuture[sz]);

			 return CompletableFuture.allOf(winfos)
			 .thenApply(__ -> {
			 	/*
			 	Stream<WeatherInfo> result = Stream.empty();
			 	for(CompletableFuture<Stream<WeatherInfo>> cfwi : winfos) {
			 		result =  Stream.concat(result, cfwi.join());
			    }
			 	return result;
			 	*/

			 return
				        Arrays.stream(winfos)
				        .flatMap(cf -> cf.join());

			 });
		});
	}

	@Test
	public void getForecastForLisbonTest() {

		OpenWeatherService weather = new OpenWeatherService(new OpenWeatherWebApi());

        var futLisbonForecast =
	        getForecastForPortugalCity("Lisbon", weather)
	        .join()
	        .collect(toList());

		System.out.println("DayInfo list size: " + futLisbonForecast.size());
		futLisbonForecast.forEach(System.out::println);
	}

    /*
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
    */

}
