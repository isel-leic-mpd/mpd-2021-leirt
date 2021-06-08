package isel.leirt.mpd.asyncprog.utils;

public class Pair<T,U> {
	public final T first;
	public final U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public String toString() {
		return String.format("(%s, %s)", first, second);
	}
}
