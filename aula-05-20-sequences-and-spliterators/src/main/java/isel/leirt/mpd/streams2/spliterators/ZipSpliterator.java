package isel.leirt.mpd.streams.spliterators;

import java.util.Spliterators;
import java.util.function.Consumer;

public class ZipSpliterator<T,U,V> extends Spliterators.AbstractSpliterator<V> {

	/**
	 * Creates a spliterator reporting the given estimated size and
	 * additionalCharacteristics.
	 *
	 * @param est                       the estimated size of this spliterator if known, otherwise
	 *                                  {@code Long.MAX_VALUE}.
	 * @param additionalCharacteristics properties of this spliterator's
	 *                                  source or elements.  If {@code SIZED} is reported then this
	 *                                  spliterator will additionally report {@code SUBSIZED}.
	 */
	protected ZipSpliterator(long est, int additionalCharacteristics) {
		super(est, additionalCharacteristics);
	}

	@Override
	public boolean tryAdvance(Consumer<? super V> action) {
		return false;
	}
}
