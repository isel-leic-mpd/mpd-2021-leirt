package isel.leirt.mpd.sequences;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

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
