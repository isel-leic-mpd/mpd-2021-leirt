package isel.leirt.mpd.moviesdb1.queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MapIterator<T,U>  implements Iterator<U> {
	private Iterator<T> itSrc;
	private Function<T,U> mapper;

	public MapIterator(Iterable<T> src, Function<T,U> mapper) {
		this.itSrc = src.iterator();
		this.mapper = mapper;
	}

	@Override
	public boolean hasNext() {
		return itSrc.hasNext();
	}

	@Override
	public U next() {
		if (!hasNext())  {
			throw new NoSuchElementException();
		}
		return mapper.apply(itSrc.next());
	}
}
