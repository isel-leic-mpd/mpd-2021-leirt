package isel.leirt.mpd.quizz;

import java.io.File;
import java.util.stream.Stream;

public class StreamsComposition {
    /*
	public static Stream<String>
	tree(File dir, int maxLevel, int maxEntries)  {
		if (maxLevel <= 0) return _______________________;

		return Stream.of(dir.listFiles())

		____________________(f -> {

			if (f.isDirectory() && !f.getName().startsWith(".")) //sub-pasta

			return ________________________(

				___________________(f.getName()),

				tree(f, maxLevel-1, maxEntries)

			);

          else if (f.isFile()) // ficheiro regular

				return Stream.of(f.getName() + ": " + f.length());

			else return Stream.empty();

		})

			.map(f -> "  " + f))

		_______________________________________;

	}

     */
}
