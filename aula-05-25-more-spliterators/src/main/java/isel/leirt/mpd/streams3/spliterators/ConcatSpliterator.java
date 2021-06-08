package isel.leirt.mpd.streams3.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ConcatSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
	private final Spliterator<T> split1;
	private final Spliterator<T> split2;

	public ConcatSpliterator(Spliterator<T> split1, Spliterator<T> split2) {
		super(split1.estimateSize()+split2.estimateSize(),
			split1.characteristics() & split2.characteristics());
		this.split1 = split1;
		this.split2 = split2;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		return split1.tryAdvance(action) || split2.tryAdvance(action);
	}
}
