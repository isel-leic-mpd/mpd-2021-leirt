package isel.leirt.mpd.weather4;

import isel.leirt.mpd.weather4.dto.ForecastHourlyInfo;
import isel.leirt.mpd.weather4.dto.ForecastWeatherInfoDto;
import isel.leirt.mpd.weather4.dto.LocationDto;
import isel.leirt.mpd.weather4.model.DayInfo;
import isel.leirt.mpd.weather4.model.Location;
import isel.leirt.mpd.weather4.model.WeatherInfo;
import isel.leirt.mpd.weather4.queries.MIterable;

import java.time.LocalDate;
import java.util.List;


import static isel.leirt.mpd.weather4.queries.MIterable.*;


public class OpenWeatherService {
	private OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}

	public MIterable<Location> search(String query) {
		MIterable<LocationDto>  locSeq = () -> api.search(query).iterator();
		return
			locSeq
			.map(dto -> dtoToLocation(dto));
	}

	public MIterable<DayInfo> forecastAt(Location loc) {
		MIterable<ForecastWeatherInfoDto> forecastApi =
			() -> api.forecastWeatherAt(loc.getLatitude(),loc.getLongitude()).iterator();
		return
			forecastApi
			.map(dto -> dtoToDayInfo(dto, loc));

	}

	private MIterable<WeatherInfo> temperatures(Double lat, Double lon, LocalDate day) {
		if (day.isAfter(LocalDate.now().plusDays(2)))
			return  of(List.of());
		MIterable<ForecastHourlyInfo> forecastDetail = ()->api.forecastDetailAt(lat,lon).iterator();
		return forecastDetail
			.filter(f -> day.equals(f.observationDate()))
			.map(dto -> dtoToWeatherInfo(dto));
	}

	public MIterable<WeatherInfo> temperatures(Location loc, DayInfo di) {
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
			(di) -> temperatures(loc, di)
		);
	}
}
