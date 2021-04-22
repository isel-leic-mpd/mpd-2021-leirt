package isel.leirt.mpd.lazyqueries.tests;

import isel.leirt.mpd.lazyqueries.Queries;
import org.junit.Test;

import java.util.Iterator;

import static isel.leirt.mpd.lazyqueries.Queries.*;

public class LazyQueriesTests {

	@Test
	public void primesSequenceTest() {
		Iterable<Long> primes = primes();

		Iterator<Long> it = primes.iterator();

		long p = 0;
		while (it.hasNext() && (p  = it.next()) < 1000) {
			System.out.println(p);
		}
	}

	@Test
	public void primesSequenceCountTest() {
		long size = count(
						filter(
							takeWhile(
								primes(),
								p -> p < 1000L
							),
							p -> {
								if ((p % 10) == 3) {
									System.out.println(p);
									return true;
								}
								return false;
							}
						)
		);

		System.out.println(size);
	}
}
