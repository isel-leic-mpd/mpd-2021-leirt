package isel.leirt.mpd.streams;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class StreamUtils {

	 public static <T,U,V>  Stream<V> zip(Stream<T> seq1, Stream<U> seq2,
	                                      BiFunction<T,U,V> combiner) {
	 	return null;
	 }
}
