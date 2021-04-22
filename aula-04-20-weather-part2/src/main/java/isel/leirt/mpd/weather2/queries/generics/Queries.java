package isel.leirt.mpd.weather2.queries.generics;



import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class Queries {

	public static <T> Iterable<T> filter(
		Iterable<T> winfos, Predicate<T> pred ) {
		List<T> accepted_winfos = new ArrayList<>();

		for(T wi: winfos) {
			if (pred.test(wi) /* wi satisfaz o critério de pesquisa */)
				accepted_winfos.add(wi);
		}
		return accepted_winfos;
	}

	public static <T> Iterable<Double> mapToDoubles(
		Iterable<T> seq, ToDoubleFunction<T> mapper) {

		List<Double> temps = new ArrayList<>();

		for(T t: seq) {
			temps.add(mapper.applyAsDouble(t));
		}
		return temps;
	}

	public static <T,U> Iterable<U> map(
		Iterable<T> seq, Function<T,U> mapper) {

		List<U> temps = new ArrayList<>();

		for(T t: seq) {
			temps.add(mapper.apply(t));
		}
		return temps;
	}

	// terminal operations

	public static <T> long count(Iterable<T> seq) {
		long c = 0;
		for(T t : seq)  {
			c++;
		}
		return c;
	}

	public static double maxDouble(Iterable<Double> doubles) {
		double max = Double.MIN_VALUE;

		for(double d : doubles)  {
			if (d > max) max = d;
		}
		return max;
	}
}
