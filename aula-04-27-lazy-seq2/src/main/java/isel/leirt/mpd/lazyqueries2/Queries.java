package isel.leirt.mpd.lazyqueries2;

import isel.leirt.mpd.lazyqueries2.iterators.FilterIterator;
import isel.leirt.mpd.lazyqueries2.iterators.MapIterator;
import isel.leirt.mpd.lazyqueries2.iterators.PrimesIterator;
import isel.leirt.mpd.lazyqueries2.iterators.TakeWhileIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class Queries {
	// factories or sequence constructors
	public static Iterable<Long> primes() {
		return () -> {
			return new PrimesIterator();
		};
	}

	// intermediate operations
	public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred ) {
		return () -> new FilterIterator<>(src, pred);
	}

	public static <T> Iterable<T> takeWhile(Iterable<T> src, Predicate<T> pred ) {
	    return () -> new TakeWhileIterator<>(src, pred);
	}


	public static <T> Iterable<Double> mapToDoubles(
		Iterable<T> seq, ToDoubleFunction<T> mapper) {

		List<Double> temps = new ArrayList<>();

		for(T t: seq) {
			temps.add(mapper.applyAsDouble(t));
		}
		return temps;
	}

	public static <T,U> Iterable<U> map(
		Iterable<T> seq, Function<T,U> mapper) {

		return () -> new MapIterator<>(seq, mapper);
	}

	// terminal operations

	public static <T> long count(Iterable<T> seq) {
		long c = 0;
		for(T t : seq)  {
			c++;
		}
		return c;
	}

	public static double maxDouble(Iterable<Double> doubles) {
		double max = Double.MIN_VALUE;

		for(double d : doubles)  {
			if (d > max) max = d;
		}
		return max;
	}

	public static <T> Optional<T> first(Iterable<T> src) {
		Iterator<T> it = src.iterator();

		if (it.hasNext()) return Optional.of(it.next());
		else return Optional.empty();
	}
}
