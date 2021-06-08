package isel.leirt.mpd.streams3.spliterators;

import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * split { 1,1, 3, 2, 2, 2, 1, 1, 7, 7}
 * collapse { 1, 3, 2, 1, 7 }
 *
 * @param <T>
 */
public class CollapseSpliterator<T> extends Spliterators.AbstractSpliterator<T>  {
	private final Spliterator<T> split;
	private Optional<T> previous = Optional.empty();

	public CollapseSpliterator(Spliterator<T> split) {
		super(Long.MAX_VALUE, Spliterator.ORDERED);
		this.split = split;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		boolean[] newElement = {false};
		while(!newElement[0] && split.tryAdvance(t ->  {
			if (previous.isEmpty() || !previous.get().equals(t)) {
				previous = Optional.of(t);
				action.accept(t);
				newElement[0] = true;
			}
		}));
		return newElement[0];
	}
}
