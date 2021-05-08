package isel.leirt.mpd.weather3.dto;

import java.time.*;

public class Utils {

	private static final long SECS_IN_DAY = (3600*24);

	private volatile static ZoneOffset zoneOffset;

	private static ZoneOffset getLocalOffset() {
		if (zoneOffset == null) {
			zoneOffset = OffsetDateTime.now().getOffset();
		}
		return zoneOffset;
	}

	public static long toUnixTime(LocalDateTime d) {
		return d.toEpochSecond(getLocalOffset());
	}


	public static long toUnixTime(int year, int month, int day) {
		return toUnixTime(LocalDateTime.of(year, month, day,0,0,0));
	}

	public static long toUnixTime( ) {
		return toUnixTime(LocalDateTime.now());
	}

	public static long toUnixTime( LocalDate dt ) {
		return dt.toEpochSecond(LocalTime.of(0,0), getLocalOffset());
	}

	public static LocalDateTime fromUnixTime(long unixTime, ZoneOffset ofs) {
		return LocalDateTime.ofEpochSecond(unixTime, 0, ofs);
	}

	public static LocalDate dateFromUnixTime(long unixTime) {
		return LocalDate.ofEpochDay((unixTime+3600)/SECS_IN_DAY);
	}

	public static LocalDateTime fromUnixTime(long unixTime) {
		return fromUnixTime(unixTime, getLocalOffset());
	}

	public static LocalTime timeFromUnixTime(long unixTime, ZoneOffset ofs) {
		Instant i = fromUnixTime(unixTime).toInstant(ofs);
		return LocalTime.ofInstant(i, ofs);
	}

	public static LocalTime timeFromUnixTime(long unixTime) {
		return timeFromUnixTime(unixTime, getLocalOffset());
	}
}
