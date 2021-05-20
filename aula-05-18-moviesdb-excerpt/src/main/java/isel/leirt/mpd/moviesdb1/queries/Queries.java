package isel.leirt.mpd.moviesdb1.queries;

import isel.leirt.mpd.moviesdb1.queries.iterators.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import static java.util.stream.StreamSupport.*;

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

	public static <T,R> Iterable<R> flatMap(Iterable<T> src,
	                                        Function<T,Iterable<R>> mapper) {


		Stream<T> s = stream(src.spliterator(), false);
		return () -> s.flatMap(m ->  stream(mapper.apply(m).spliterator(), false)).iterator();
	}


	public static <T> Iterable<T> takeWhile(Iterable<T> src,Predicate<T> p) {
		Stream<T> s = stream(src.spliterator(), false);
		return () -> s.takeWhile(p).iterator();

	}

	public static <T> Iterable<T> limit(Iterable<T> src, int n) {
		Stream<T> s = stream(src.spliterator(), false);
		return () -> s.limit(n).iterator();

	}

	public static Iterable<Integer> range(int li, int ls) {
		List<Integer> nums = new ArrayList<>();
		for(int i=li; i <= ls; ++i) {
			nums.add(i);
		}
		return nums;
	}

	public static <T,U> Iterable<U> map(
		Iterable<T> seq, Function<T,U> mapper) {

		return () -> new MapIterator<>(seq, mapper);
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