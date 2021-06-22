package isel.leirt.mpd.quizz;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public interface Sequence<T> {
	boolean tryAdvance(Consumer<T> action);

	@SuppressWarnings("unchecked")
	static <T> Sequence<T> of(T...src) {
		int[] idx = {0};
		return action -> {
			if(idx[0] >= src.length) return false;
			action.accept(src[idx[0]]);
			return idx[0]++ < src.length;
		};
	}


	default void forEach(Consumer<T> action) {
		while(tryAdvance(action));
	}


	default List<T> toList() {
		List<T> list = new ArrayList<>();
		forEach(list::add);
		return list;
	}

	default Sequence<T>  odds(  ) {
		return action -> {
			boolean res = (tryAdvance(action));
			if (res)  tryAdvance(__ -> {});
			return res;
		};
	}
}
