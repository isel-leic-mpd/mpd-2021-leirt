package isel.leirt.mpd.lazyqueries2.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static isel.leirt.mpd.lazyqueries2.Queries.*;

public class CombinationsTests {

	private static class Pair<T,U> {
		public final T first;
		public final U second;

		public Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public String toString() {
			return "( " + first + "," + second + ")";
		}
	}

	@Test
	public void gen1To10CombinationsPairsImperativeModeTest() {
		List<Pair<Integer,Integer>> combs = new ArrayList<>();

		for(int i=1; i <= 10; i++) {
			for(int j = i+1 ; j <= 10; j++) {
				combs.add(new Pair(i, j));
			}
		}

		combs.forEach(System.out::println);
	}


	@Test
	public void gen1To10LazyCombinationsPairsDeclarativeModeTest() {

		Iterable<Pair<Integer,Integer>> combs =
			flatMap(
					range(1, 10),
					i ->
						map (
							range(i+1, 10),
							j -> new Pair(i,j)
						)
			);


		combs.forEach(System.out::println);

	}
}
