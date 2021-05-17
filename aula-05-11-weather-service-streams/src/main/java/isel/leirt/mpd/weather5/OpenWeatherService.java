package isel.leirt.mpd.weather5;

import isel.leirt.mpd.weather5.dto.ForecastHourlyInfo;
import isel.leirt.mpd.weather5.dto.ForecastWeatherInfoDto;
import isel.leirt.mpd.weather5.dto.LocationDto;
import isel.leirt.mpd.weather5.model.DayInfo;
import isel.leirt.mpd.weather5.model.Location;
import isel.leirt.mpd.weather5.model.WeatherInfo;
import isel.leirt.mpd.weather5.queries.MIterable;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class OpenWeatherService {
	private OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}

	public Supplier<Stream<Location>> search(String query) {
		return
			() -> api.search(query)
					.stream()
					.map(dto -> dtoToLocation(dto));
	}

	public Supplier<Stream<DayInfo>> forecastAt(Location loc) {
		return
			() -> api.forecastWeatherAt(loc.getLatitude(),loc.getLongitude())
			.stream()
			.map(dto -> dtoToDayInfo(dto, loc));

	}

	private Supplier<Stream<WeatherInfo>> temperatures(Double lat, Double lon, LocalDate day) {
		if (day.isAfter(LocalDate.now().plusDays(2)))
			return  () -> Stream.empty();

		return
			() -> api.forecastDetailAt(lat,lon)
			.stream()
			.filter(f -> day.equals(f.observationDate()))
			.map(dto -> dtoToWeatherInfo(dto));
	}

	public Supplier<Stream<WeatherInfo>> temperatures(Location loc, DayInfo di) {
		return temperatures(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}

	public  Location dtoToLocation(LocationDto dto) {
		return new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			(loc) -> forecastAt(loc).get()
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
			//() -> temperatures(loc.getLatitude(), loc.getLongitude(), dto.obsDate())
			//Iterable<WeatherInfo> temperatures(Location loc, DayInfo di)
			(di) -> temperatures(loc, di).get()
		);
	}
}
