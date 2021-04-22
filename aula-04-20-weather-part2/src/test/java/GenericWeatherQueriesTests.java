import isel.leirt.mpd.weather2.OpenWeatherWebApi;
import isel.leirt.mpd.weather2.dto.PastWeatherInfo;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static isel.leirt.mpd.weather2.queries.generics.Queries.*;

public class GenericWeatherQueriesTests {
	private final static double LISBON_LAT  =  38.7071;
	private final static double LISBON_LONG = -9.1359;

	@Test
	public void maxTempForLisbonAtApril17Between1And8HoursUsingQueries0Test() {
		LocalTime startTime = LocalTime.of(8, 0);
		LocalTime endTime = LocalTime.of(19, 0);
		OpenWeatherWebApi webApi = new OpenWeatherWebApi();
		List<PastWeatherInfo> past3Days =
			webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021, 04, 17));


		Double maxTemp =
			maxDouble(
				mapToDoubles(
					filter(past3Days,
						pwi -> pwi.obsTime().compareTo(startTime) >= 0 &&
							pwi.obsTime().compareTo(endTime) <= 0),
					pwi-> pwi.getTemp()
				)
			);

		/*
		Double maxTemp =
			past3Days.filter(
							pwi -> pwi.obsTime().compareTo(startTime) >= 0 &&
							pwi.obsTime().compareTo(endTime) <= 0)
			.mapToDoubles(pwi-> pwi.getTemp())
			.maxDouble();
		 */

		System.out.println("max temp between " + startTime
			+ " and " + endTime
			+ " = " + maxTemp);
	}
}
