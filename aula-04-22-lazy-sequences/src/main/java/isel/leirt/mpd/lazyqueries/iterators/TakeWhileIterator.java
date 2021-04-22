package isel.leirt.mpd.lazyqueries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class TakeWhileIterator<T> implements Iterator<T> {
	private Iterator<T> itSrc;
	Predicate<T> pred;
	boolean hasValue, done;
	T curr;

	public TakeWhileIterator(Iterable<T> src, Predicate<T> pred) {
		itSrc = src.iterator();
		this.pred = pred;
	}

	@Override
	public boolean hasNext() {
		if (done) return false;     // no more elements on source
		if (hasValue) return true;  // we have already a pending value
		if (!itSrc.hasNext() || !pred.test(curr = itSrc.next()) ) {
			// no more elements on source
			// or abort de takeWhile since the predicate not satisfied
			done = true;
			return false;
		}
		hasValue = true;            // mark that we have a pending value
		return true;
	}

	@Override
	public T next() {
		if (!hasNext())  {
			throw new NoSuchElementException();
		}
		hasValue = false;       // mark that the pending value was consumed
		return curr;            // consume the value
	}
}
