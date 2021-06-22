package isel.leirt.mpd.weather6;

import isel.leirt.mpd.weather6.dto.ForecastHourlyInfo;
import isel.leirt.mpd.weather6.dto.ForecastWeatherInfoDto;
import isel.leirt.mpd.weather6.dto.LocationDto;
import isel.leirt.mpd.weather6.model.DayInfo;
import isel.leirt.mpd.weather6.model.Location;
import isel.leirt.mpd.weather6.model.WeatherInfo;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class OpenWeatherService {
	private OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}


	public CompletableFuture<Stream<Location>> search(String query) {
		return api.search(query)
			   .thenApply(list ->
					list.stream()
					.map(dto -> dtoToLocation(dto)));
	}

	public CompletableFuture<Stream<DayInfo>> forecastAt(Location loc) {

		return
			api.forecastWeatherAt(loc.getLatitude(),loc.getLongitude())
			.thenApply(list ->
						list.stream()
						.map(dto -> dtoToDayInfo(dto, loc)));
	}

	private CompletableFuture<Stream<WeatherInfo>>
			temperatures(Double lat, Double lon, LocalDate day) {

		if (day.isAfter(LocalDate.now().plusDays(2)))
			return  CompletableFuture.completedFuture(Stream.empty());

		return
			api.forecastDetailAt(lat,lon)
			.thenApply(list -> {
				return list.stream()
					.filter(f -> day.equals(f.observationDate()))
					.map(dto -> dtoToWeatherInfo(dto));
			});
	}

	public CompletableFuture<Stream<WeatherInfo>> temperatures(Location loc, DayInfo di) {
		return temperatures(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}

	public  Location dtoToLocation(LocationDto dto) {
		return new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			(loc) -> forecastAt(loc)
		);
	}

	public  WeatherInfo dtoToWeatherInfo(ForecastHourlyInfo dto) {
		return new WeatherInfo(
			dto.observationDateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}

	public DayInfo dtoToDayInfo(ForecastWeatherInfoDto dto, Location loc) {
		return new DayInfo(
			dto.obsDate(),
			dto.maxTemp(),
			dto.minTemp(),
			dto.sunRise(),
			dto.sunSet(),
			dto.moonRise(),
			dto.moonSet(),
			dto.moonPhase(),
			dto.icon(),
			//() -> temperatures(loc.getLatitude(), loc.getLongitude(), dto.obsDate())
			//Iterable<WeatherInfo> temperatures(Location loc, DayInfo di)
			(di) -> temperatures(loc, di)
		);
	}
}
