package isel.leirt.mpd.moviesdb1.queries.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CacheIterator<T> implements Iterator<T> {
	Iterator<T> srcIt;
	List<T> cache;
	int idx;

	public CacheIterator(Iterator<T> srcIt, List<T> cache) {
		this.srcIt = srcIt;
		this.cache = cache;
	}

	@Override
	public boolean hasNext() {
		if (idx < cache.size()) return true;
		if (!srcIt.hasNext()) return false;
		cache.add(srcIt.next());
		return true;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return cache.get(idx++);
	}
}
