package isel.leirt.mpd.weather3;

import isel.leirt.mpd.weather3.dto.ForecastHourlyInfo;
import isel.leirt.mpd.weather3.dto.ForecastWeatherInfoDto;
import isel.leirt.mpd.weather3.dto.LocationDto;
import isel.leirt.mpd.weather3.model.DayInfo;
import isel.leirt.mpd.weather3.model.Location;
import isel.leirt.mpd.weather3.model.WeatherInfo;

import java.time.LocalDate;
import java.util.List;

import static isel.leirt.mpd.lazyqueries2.Queries.*;


public class OpenWeatherService {
	private OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}

	public Iterable<Location> search(String query) {
		return
			map (
				() -> {
					return api.search(query).iterator();
				},
				dto -> dtoToLocation(dto)
		);
	}

	public Iterable<DayInfo> forecastAt(Location loc) {
		return map(
			api.forecastWeatherAt(loc.getLatitude(),loc.getLongitude()),
			dto -> dtoToDayInfo(dto, loc)
		);
	}

	private Iterable<WeatherInfo> temperatures(Double lat, Double lon, LocalDate day) {
		if (day.isAfter(LocalDate.now().plusDays(2)))
			return List.of();

		return map(
			filter(
				api.forecastDetailAt(lat,lon),
				f -> day.equals(f.observationDate())
			),
			dto -> dtoToWeatherInfo(dto)

		);
	}

	public Iterable<WeatherInfo> temperatures(Location loc, DayInfo di) {
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
			//() -> temperatures(loc.getLatitude(), loc.getLongitude(), dto.obsDate())
			//Iterable<WeatherInfo> temperatures(Location loc, DayInfo di)
			(di) -> temperatures(loc, di)
		);
	}
}
