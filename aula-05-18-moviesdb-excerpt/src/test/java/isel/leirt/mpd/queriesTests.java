package isel.leirt.mpd;

import isel.leirt.mpd.moviesdb1.queries.iterators.PrimesIterator;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static isel.leirt.mpd.moviesdb1.queries.Queries.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class queriesTests {

	@Test
	public void primesCachedTest() {
		int[] count = {0};
		Iterable<Long>  cachedPrimes =
			cache(
				limit(
					iterate(2L, (v -> {
						count[0]++;
						return PrimesIterator.nextPrime(v);
					})),
					10
				)
			);


		toList(cachedPrimes).forEach(System.out::println);
		System.out.println("total src accesses: " + count[0]);

		Iterator<Long> it = cachedPrimes.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
		}

		System.out.println("total src accesses: " + count[0]);

	}


	@Test
	public void cacheTest() {
		Random r = new Random();
		Iterable<Integer> nrs = generate(() -> r.nextInt(100));
		Iterable<Integer>  cachedNumbers  = cache(nrs);
		List<Integer> expected = toList(limit(cachedNumbers, 10));
		List<Integer> actual = toList(limit(cachedNumbers, 10));

		assertEquals(expected, actual);
	}


}
