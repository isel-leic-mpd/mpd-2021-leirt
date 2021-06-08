package isel.leirt.mpd.sequences2;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public interface Sequence<T> {
	boolean tryAdvance(Consumer<T> action);

	// generators

	static <U> Sequence<U> from(Iterable<U> src) {
		Iterator<U> it = src.iterator();
		Sequence<U> newSeq = action -> {
			if (!it.hasNext()) return false;
			action.accept(it.next());
			return true;
		};

		return newSeq;
	}

	static <U> Sequence<U> iterate(U seed, UnaryOperator<U> func) {
		throw new UnsupportedOperationException();
	}

	static <U> Sequence<U> from(U ... elems) {
		int[] idx = {0};
		return action -> {
			if (idx[0] == elems.length) return false;
			action.accept(elems[idx[0]]);
			idx[0]++;
			return true;
		};
	}


	// intermediate operations
	default Sequence<T> limit(int n) {
		int[] remaining = {n};
		return action -> {
			if (remaining[0] == 0) return false;
			if (tryAdvance(action)) {
				remaining[0]--;
				return true;
			}
			else {
				remaining[0] = 0;
				return false;
			}
		};
	}

	default Sequence<T> filter(Predicate<T> pred) {
		 return action -> {
			    boolean[] done = {false};
		 	    Consumer<T> myAction = t -> {
			        if (pred.test(t)) {
				        action.accept(t);
				        done[0] = true;
			        }
		        };

		 	    while(!done[0] && tryAdvance(myAction));
		 	    return done[0];
		 };
	}

	default Sequence<T> collapse() {

		throw new UnsupportedOperationException();
	}

	default Sequence<T> skip(int n) {
		throw new UnsupportedOperationException();
	}

	default Sequence<T> concat(Sequence<T> other) {

		return action -> tryAdvance(action) || other.tryAdvance(action);
	}

	default <U> Sequence<U> map(Function<T, U> mapper) {

		return action -> {
			Consumer<U> cons = action;
			Consumer<T>  taction = t -> action.accept(mapper.apply(t));

			return tryAdvance( taction);

		};
	}

	// terminal operations

	default void forEach(Consumer<T> cons) {
		while(tryAdvance(cons));
	}

}
