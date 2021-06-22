package isel.leirt.mpd.weather6.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

public class DayInfo {
	private LocalDate date;
	private double maxTempC;
	private double minTempC ;
	private LocalTime sunrise;
	private LocalTime sunset;
	private LocalTime moonrise;
	private LocalTime moonset;
	private double moon_phase;

	private Function<DayInfo, CompletableFuture<Stream<WeatherInfo>>> temperatures;
	//private Iterable<WeatherInfo> temperatures;
	//private Supplier<Iterable<WeatherInfo>> temperatures;
	public DayInfo(LocalDate date, double maxTempC, double minTempC,
	               LocalTime sunrise, LocalTime sunset,
	               LocalTime moonrise, LocalTime moonset,
	               double moon_phase,
	               Function<DayInfo, CompletableFuture<Stream<WeatherInfo>>>  temperatures) {
		this.date = date;
		this.maxTempC = maxTempC;
		this.minTempC = minTempC;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.moonrise = moonrise;
		this.moonset = moonset;
		this.moon_phase = moon_phase;
		this.temperatures = temperatures;
	}

	// accessors
	public LocalDate getDate()      { return date; }
	public double getMaxTemp()         { return maxTempC; }
	public double getMinTemp()         { return minTempC; }
	public LocalTime getSunrise()   { return sunrise; }
	public LocalTime getSunset()    { return sunset; }
	public LocalTime getMoonrise()  { return moonrise; }
	public LocalTime getMoonset()   { return moonset; }
	public double getMoonPhase()    { return moon_phase; }


	public CompletableFuture<Stream<WeatherInfo>> temperatures() {
		return temperatures.apply(this);
	}

	@Override
	public String toString() {
		return "{"
			+ date
			+ ", max="          + maxTempC
			+ ", min="          + minTempC
			+ ", sunrise="      + sunrise
			+ ", sunset="       + sunset
			+ ", moonrise="     + moonrise
			+ ", moonset="      + moonset
			+ ", moon_phase="   + moon_phase
			+ "}";
	}
}
