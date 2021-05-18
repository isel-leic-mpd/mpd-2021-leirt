package isel.leirt.mpd.moviesdb1.queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
	private T[] elems;
	private int curr = 0;

	public ArrayIterator(T[] elems) {
		this.elems = elems;
	}

	@Override
	public boolean hasNext() {
		return curr < elems.length;
	}

	@Override
	public T next() {
		if (!hasNext()) throw new NoSuchElementException();
		return elems[curr++];
	}
}
