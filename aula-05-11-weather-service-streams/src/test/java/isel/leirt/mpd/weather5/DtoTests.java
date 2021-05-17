package isel.leirt.mpd.weather5;

import static isel.leirt.mpd.weather5.dto.Utils.*;
import org.junit.Test;

import java.time.*;

public class DtoTests {

	@Test
	public void toUnixTimesTest() {
		long t2 = toUnixTime(LocalDateTime.now().minusDays(1));
		long t1 = toUnixTime(LocalDateTime.now().minusDays(4));

		System.out.println(t1);
		System.out.println(t2);
	}

	@Test
	public void fromUnixTimesTest() {
		System.out.println(fromUnixTime(1617633311).toString());
		System.out.println(
			LocalDateTime
			.ofEpochSecond(1617633311,0,ZoneOffset.of("+1"))
			.toString()
		);

	}

	@Test
	public void checkZoneOffsetIdsTest() {
		ZoneOffset.getAvailableZoneIds().stream().sorted().forEach(System.out::println);

	}

	@Test
	public void getLocalDateTimeTest() {
		System.out.println(LocalDateTime.now());
		System.out.println(OffsetDateTime.now().getOffset());
	}

	@Test
	public void getTimeFromUnixTimeTest() {
		LocalTime time = timeFromUnixTime(1617633311);
		System.out.println(time);
	}
}
