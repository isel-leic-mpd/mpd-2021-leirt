package isel.leirt.mpd.weather2.queries.generics.functional_interfaces;

public interface Function<T,V> {
	V apply( T t);
}
