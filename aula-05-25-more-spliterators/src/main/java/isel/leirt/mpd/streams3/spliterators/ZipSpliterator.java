package isel.leirt.mpd.streams3.spliterators;

import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static java.util.Spliterators.*;

public class ZipSpliterator<T, U, V>  extends AbstractSpliterator<V> {
	private final Spliterator<T> split1;
	private final Spliterator<U> split2;
	private final BiFunction<T,U,V> combiner;

	public ZipSpliterator(Spliterator<T> split1, Spliterator<U> split2, BiFunction<T,U,V> combiner) {
		super(Math.min(split1.estimateSize(), split2.estimateSize()), Spliterator.SIZED | Spliterator.ORDERED);
        this.split1 = split1;
        this.split2 = split2;
        this.combiner = combiner;
	}



	@Override
	public boolean tryAdvance(Consumer<? super V> action) {
		boolean[] newElement = {false};
		split1.tryAdvance(t -> {
			split2.tryAdvance(u -> {
				action.accept(combiner.apply(t,u));
				newElement[0] = true;
			});
		});
		return newElement[0];

	}
}
