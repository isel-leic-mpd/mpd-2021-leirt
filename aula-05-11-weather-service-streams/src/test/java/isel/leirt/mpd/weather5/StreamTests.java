package isel.leirt.mpd.weather5;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;
import static java.util.stream.Stream.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamTests {
	/*
	@Test
	public void firstStreamTest() {
		Supplier<Stream<Integer>> nums =
			() -> iterate(2, n -> n*2)
			.peek(n -> System.out.println("received: " + n))
			.limit(20);

		System.out.println("After nums production");
		nums.get().forEach(System.out::println);

		assertEquals(20L, nums.get().count());
	}


	@Test
	public void reduceStreamTest() {
		Supplier<Stream<Integer>> nums =
			() -> iterate(2, n -> n*2)
				.peek(n -> System.out.println("received: " + n))
				.limit(20);

		Optional<Integer> num = nums.get().reduce((n1, n2) -> (n1 + n2));

		Optional<Integer> max = nums.get().reduce((n1,n2) -> n1.compareTo(n2) > 0 ? n1 : n2);

		System.out.println("max = " + max.get());

		int sumInts = nums.get()
			.mapToInt(n -> n)
			.sum();

		System.out.println("After nums production");
		System.out.println(num);
		assertEquals(sumInts, num.get().intValue());

		assertEquals(20L, nums.get().count());
	}


	*/









	private static boolean isPrime(long n) {

		if (n ==2) return true;
		if (n % 2 ==0 || n < 2) return false;
		for(long t = 3; t < sqrt(n); t += 2)
			if (n % t == 0) return false;
		return true;

	}

	private static boolean isPrime(Long n) {

		if (n ==2) return true;
		if (n % 2 ==0 || n < 2) return false;
		for(Long t = 3L; t < sqrt(n); t += 2)
			if (n % t == 0) return false;
		return true;

	}




	@Test
	public void getFirst100ThousandPrimesSequentialTest() {
		long start = System.currentTimeMillis();

		Stream<Long> candidates =
			Stream.concat(
				Stream.of( (long) 2),
				iterate((long) 3, n -> n +2));

		Stream<Long> primes =
			candidates.filter(StreamTests::isPrime)
				.limit(100000);

		Optional<Long> lastMillionPrime =
			primes.reduce((l1, l2) -> l2);


		assertTrue(lastMillionPrime.isPresent());

		assertEquals(1297003, (long) lastMillionPrime.get());
		System.out.println("In Stream<Long> sequential test, last million prime is: " + lastMillionPrime);
		long end = System.currentTimeMillis();
		System.out.println("done in " + (end-start) + "ms");
	}

	@Test
	public void getFirstMillionPrimesSequentialWithLongStreamTest() {
		long start = System.currentTimeMillis();

		LongStream candidates =
			LongStream.concat(
				LongStream.of( 2),
				LongStream.iterate( 3, n -> n +2));

		LongStream primes =
			candidates.filter(StreamTests::isPrime)
				.limit(100000);


		OptionalLong lastMillionPrime =
			primes.reduce((l1, l2) -> l2);

		assertTrue(lastMillionPrime.isPresent());

		assertEquals(1297003, lastMillionPrime.getAsLong());
		System.out.println("In LongStream sequential test, last million prime is: " + lastMillionPrime);
		long end = System.currentTimeMillis();
		System.out.println("done in " + (end-start) + "ms");
	}


	@Test
	public void getFirst100ThousandPrimesParallelTest() {
		long start = System.currentTimeMillis();

		Stream<Long> candidates =
			Stream.concat(
				Stream.of( 2L),
				iterate(3L, n -> n +2));

		Stream<Long> primes =
			candidates.filter(StreamTests::isPrime)

				.limit(100000);

		Optional<Long> lastMillionPrime =
			primes
				.parallel()
				.reduce((l1, l2) -> l2);


		assertTrue(lastMillionPrime.isPresent());

		assertEquals(1297003L, (long) lastMillionPrime.get());
		System.out.println("In Stream<Long> parallel test, last million prime is: " + lastMillionPrime);
		long end = System.currentTimeMillis();
		System.out.println("done in " + (end-start) + "ms");
	}

	@Test
	public void getFirst100ThousandPrimesParallelWithLongStreamTest() {
		long start = System.currentTimeMillis();

		LongStream candidates =
			LongStream.concat(
				LongStream.of( 2),
				LongStream.iterate( 3, n -> n +2));

		LongStream primes =
			candidates
				.parallel()
				.filter(StreamTests::isPrime)
				.limit(100000);


		OptionalLong lastMillionPrime =
			primes.reduce((l1, l2) -> l2);


		assertTrue(lastMillionPrime.isPresent());

		assertEquals(1297003, lastMillionPrime.getAsLong());
		System.out.println("In LongStream parallel test, last million prime is: " + lastMillionPrime);
		long end = System.currentTimeMillis();
		System.out.println("done in " + (end-start) + "ms");
	}

}
