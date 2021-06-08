package isel.leirt.mpd.streams3;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import static isel.leirt.mpd.streams3.StreamUtils.*;
import static org.junit.Assert.*;
public class StreamUtilsTests {

	private static class Pair<T,U> {
		public final T first;
		public final U second;

		public Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}
	}

	private static Stream<Path> getPathStream(String folder) {
		int searchDepth = 20;

		try {
			return Files.walk(Path.of(folder), searchDepth);
		}
		catch(IOException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private static Stream<String> lines(Path p) {

			try {
				return Files.lines(p);
			}
			catch(IOException e) {
				throw new UncheckedIOException((e));
			}

	}

	@Test
	public void ourConcatTest() {
		Stream<Integer> s1 = Stream.of(1,2,3);
		Stream<Integer> s2 = Stream.of(4,5,6,7);

		Integer[] expectedValues = { 1, 2, 3, 4, 5, 6, 7 };

		Stream<Integer> seqr = StreamUtils.concat(s1, s2);

		Assert.assertArrayEquals(expectedValues, seqr.toArray(n -> new Integer[n]));

	}

	@Test
	public void collapseTest() {
		Stream<Integer> repNumbers = Stream.of(2,2, 3, 1, 1, 1, 7, 7, 7);

		Stream<Integer>  numbers  = collapse(repNumbers);

		//numbers.forEach(System.out::println);

		Integer[] expected = { 2, 3, 1, 7};

		Assert.assertArrayEquals(expected, numbers.toArray(n -> new Integer[n]));
	}


	@Test
	public void zipSimpleTest() {
		Stream<Integer> seq2 = Stream.of(4,5,6,7);
		Stream<String>  seq1 = Stream.of("A", "B", "C" );

		String[] expected = { "A4", "B5", "C6" };
		//String[] expected = {  "B5" };
		Stream<String> pairs = zip( seq1,
									seq2,
									(s, n) -> s + n
									).peek(System.out::println)
									.filter(s -> s.contains("B"));

		//Spliterator<String> pairsIterator = pairs.spliterator();

		System.out.println("Start");
		//while(pairsIterator.tryAdvance(System.out::println));
		assertEquals("B5", pairs.findFirst().get());
		assertArrayEquals(expected, pairs.toArray(size -> new String[size]));
		System.out.println("End");
	}

	@Test
	public void findNamesInFileTest() {
		String toFind = "pedro";

		Stream<String> fileLines = lines(Path.of("students.txt"));
		Stream<String> foundLines =
			zip(Stream.iterate(1, n -> n+1),
				fileLines,
				(n, s) -> n + ":" + s
			)
			//.peek(System.out::println)
			.map(line -> line.toLowerCase())
			//.peek(System.out::println)
			.filter(line -> line.contains(toFind));

		foundLines.forEach(System.out::println);

	}


}
