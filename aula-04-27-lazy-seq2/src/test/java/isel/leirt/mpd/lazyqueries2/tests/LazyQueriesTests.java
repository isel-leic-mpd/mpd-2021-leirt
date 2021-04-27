package isel.leirt.mpd.lazyqueries2.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Optional;

import static isel.leirt.mpd.lazyqueries2.Queries.*;

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
								p -> p < 1000
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

	@Test
	public void primesSequenceFirstWithUnitsDigitAs3Test() {
		Iterable<Long> primesSeq = primes();

		Iterable<Long> mappedPrimes =
			map(
				filter(
					takeWhile(
						primesSeq,
						p -> p < 10000000L
					),
					p -> (p % 10) == 3
				),
				p -> p*2
			);



		Optional<Long> firstMapped = first(mappedPrimes);

		Assert.assertTrue(firstMapped.isPresent());
		Assert.assertEquals(6L, firstMapped.get().longValue());

	}
}
