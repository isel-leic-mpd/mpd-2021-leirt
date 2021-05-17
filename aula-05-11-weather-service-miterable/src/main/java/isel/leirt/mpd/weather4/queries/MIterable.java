package isel.leirt.mpd.weather4.queries;

import isel.leirt.mpd.weather4.queries.iterators.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public interface MIterable<T> {

	Iterator<T> iterator();

	// factories or sequence constructors
	static <T> MIterable<T> of(Iterable<T> seq) {
		return () -> seq.iterator();
	}

	static MIterable<Integer> range(int li, int ls) {
		return () -> new RangeIterator(li,ls);
	}

	// intermediate operations
	default MIterable<T> filter(Predicate<T> pred ) {
		return () -> new FilterIterator<>(this, pred);
	}

	default MIterable<T> takeWhile(Predicate<T> pred ) {
	    return () -> new TakeWhileIterator<>(this, pred);
	}

	default <U> MIterable<U> map(Function<T,U> mapper) {
		return () -> new MapIterator<>(this, mapper);
	}

	default <U> MIterable<U> flatMap(Function<T, MIterable<U>> mapper) {
		return () -> new FlatMapIterator<>(this, mapper);
	}




	// terminal operations


	default void forEach(Consumer<T> action) {
		Iterator<T> it = iterator();
		while(it.hasNext())
			action.accept(it.next());
	}

	default long count() {
		long c = 0;
		Iterator<T> it = iterator();
		while(it.hasNext()) {
			it.next();
			c++;
		}


		return c;
	}


	default Optional<T> first() {
		Iterator<T> it = iterator();

		if (it.hasNext()) return Optional.of(it.next());
		else return Optional.empty();
	}
}
