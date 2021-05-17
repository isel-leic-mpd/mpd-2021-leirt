package isel.leirt.mpd.weather4.queries.iterators;

import isel.leirt.mpd.weather4.queries.MIterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
	private Iterator<T> itSrc;
	Predicate<T> pred;

	boolean hasValue;
	T curr;

	public FilterIterator(MIterable<T> src, Predicate<T> pred) {
		itSrc = src.iterator();
		this.pred = pred;
	}

	@Override
	public boolean hasNext() {
		 T next;

		 if (hasValue) return true;
		 while(itSrc.hasNext()) {
		 	next = itSrc.next();
		 	if (pred.test(next)) {
		 		curr = next;
		 		hasValue = true;
		 		return true;
		    }
		 }
		 return false;
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
