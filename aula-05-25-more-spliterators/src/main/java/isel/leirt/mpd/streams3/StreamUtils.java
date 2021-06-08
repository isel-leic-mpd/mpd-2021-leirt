package isel.leirt.mpd.streams3;

import isel.leirt.mpd.streams3.spliterators.CollapseSpliterator;
import isel.leirt.mpd.streams3.spliterators.ConcatSpliterator;
import isel.leirt.mpd.streams3.spliterators.ZipSpliterator;

import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("unchecked")
public class StreamUtils {

	public static <T,U,V> Stream<V> zip(Stream<T> seq1, Stream<U> seq2,
	                                    BiFunction<T,U,V> combiner) {
		return StreamSupport.stream(new ZipSpliterator(seq1.spliterator(), seq2.spliterator(), combiner), false);
	}

	public static <T> Stream<T> concat(Stream<T> seq1, Stream<T> seq2) {
		return StreamSupport.stream(new ConcatSpliterator(seq1.spliterator(), seq2.spliterator()), false);
	}

	public static <T> Stream<T> collapse(Stream<T> seq) {
		return StreamSupport.stream(new CollapseSpliterator(seq.spliterator()), false);
	}
}
