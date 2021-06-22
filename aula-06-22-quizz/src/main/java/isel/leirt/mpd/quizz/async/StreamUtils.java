package isel.leirt.mpd.quizz.async;

import jdk.jshell.spi.ExecutionControl;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class StreamUtils {

	public static <T,U,R> Stream<R> zip(Stream<T> s1, Stream<U> s2, BiFunction<T,U,R> combiner) {
		throw new UnsupportedOperationException();
	}
}
