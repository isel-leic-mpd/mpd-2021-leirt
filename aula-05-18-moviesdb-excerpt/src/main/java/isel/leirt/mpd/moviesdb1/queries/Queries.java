package isel.leirt.mpd.moviesdb1.queries;

import isel.leirt.mpd.moviesdb1.queries.iterators.*;

import java.util.*;
import java.util.function.*;

public class Queries {
	// factories or sequence constructors

	public static Iterable<Long> primes() {
		return () -> {
			return new PrimesIterator();
		};
	}


	public static  <T> Iterable<T> iterate( T seed, UnaryOperator<T> nextValue) {
		throw new UnsupportedOperationException();
	}

	public static  <T> Iterable<T> generate( Supplier<T> supplier) {
		throw new UnsupportedOperationException();
	}


	public static  <T> Iterable<T> of( T... params) {
		return () -> new ArrayIterator<>(params);
	}

	// intermediate operations

	public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred ) {
		return () -> new FilterIterator<>(src, pred);
	}

	public static <T> Iterable<T> takeWhile(Iterable<T> src, Predicate<T> pred ) {
		throw new UnsupportedOperationException();
	}

	public static Iterable<Integer> range(int li, int ls) {
		throw new UnsupportedOperationException();
	}

	public static <T,U> Iterable<U> map(
		Iterable<T> seq, Function<T,U> mapper) {

		return () -> new MapIterator<>(seq, mapper);
	}

	public static <T,U> Iterable<U> flatMap(
		Iterable<T> seq, Function<T, Iterable<U>> mapper) {

		throw new UnsupportedOperationException();
	}

	public static <T> Iterable<T> skipWhile(Iterable<T> src, Predicate<T> pred ) {
		return () ->  {
			return new Iterator<T>() {
				boolean first = true;
				boolean hasValue;
				Iterator<T> itSrc = src.iterator();
				T curr;

				@Override
				public boolean hasNext() {
					if (hasValue) return true;
					if (first) {
						while (itSrc.hasNext())  {
							T val = itSrc.next();
							if (pred.test(val)) {
								first = false;
								curr = val;
								hasValue = true;
								return true;
							}
						}
						return false;
					}
					return itSrc.hasNext();
				}

				@Override
				public T next() {
					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					if (hasValue) {
						hasValue = false;
						return curr;
					}
					return itSrc.next();
				}
			};

		};
	}

	public static <T> Iterable<T> limit(Iterable<T> src, int n) {
		throw new UnsupportedOperationException();
	}

	// terminal operations

	public static <T> long count(Iterable<T> seq) {
		long c = 0;
		for(T t : seq)  {
			c++;
		}
		return c;
	}

	public static <T> List<T>  toList(Iterable<T> src) {
		throw new UnsupportedOperationException();
	}


	public static <T>  Iterable<T>  cache(Iterable<T> src) {
		Iterator<T> srcIt = src.iterator();
		List<T> cached_elements = new ArrayList<>();
		return () -> new CacheIterator<>(srcIt, cached_elements);
	}
















	public static <T,U> U  reduce(Iterable<T> src, U initial, BiFunction<T,U,U> acum) {
		U result = initial;
		for(T e : src)  result = acum.apply(e, result);
		return result;
	}
}