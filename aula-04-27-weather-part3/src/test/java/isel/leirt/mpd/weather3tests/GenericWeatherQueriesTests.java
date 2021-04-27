package isel.leirt.mpd.weather3tests;

import isel.leirt.mpd.weather3.OpenWeatherWebApi;
import isel.leirt.mpd.weather3.dto.PastWeatherInfo;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static isel.leirt.mpd.lazyqueries2.Queries.*;

public class GenericWeatherQueriesTests {
	private final static double LISBON_LAT  =  38.7071;
	private final static double LISBON_LONG = -9.1359;

	@Test
	public void maxTempForLisbonAtApril26Between1And8HoursUsingQueriesTest() {
		LocalTime startTime = LocalTime.of(8, 0);
		LocalTime endTime = LocalTime.of(19, 0);
		OpenWeatherWebApi webApi = new OpenWeatherWebApi();
		List<PastWeatherInfo> pastApril26 =
			webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021, 04, 26));


		Double maxTemp =
			maxDouble(
				map(
					filter(pastApril26,
						pwi -> pwi.obsTime().compareTo(startTime) >= 0 &&
							pwi.obsTime().compareTo(endTime) <= 0),
					pwi-> pwi.getTemp()
				)
			);



		System.out.println("max temp between " + startTime
			+ " and " + endTime
			+ " = " + maxTemp);
	}
}
