package isel.leirt.mpd.quizz;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamConsumers {

	public static void names() {
	     Stream<Integer> numbers =
		        Stream.of("Patricio", "Cedric", "Ronaldo", "Moutinho")
				.peek(System.out::println)
				.map(String::length)
		        //.collect(Collectors.toList()).stream()
				.filter(l -> l >= 7)
				.limit(2);

		numbers.forEach(System.out::println);
    }
}
