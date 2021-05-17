package isel.leirt.mpd.weather5.queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RangeIterator implements Iterator<Integer> {
	int ls, curr;

	public RangeIterator(int li, int ls) {
		this.curr = li-1;
		this.ls = ls;

	}

	@Override
	public boolean hasNext() {
		return curr < ls;
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return ++curr;
	}
}
