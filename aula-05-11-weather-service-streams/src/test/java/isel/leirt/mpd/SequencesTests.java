package isel.leirt.mpd;

import isel.leirt.mpd.sequences.Sequence;
import org.junit.Test;

import java.util.List;

public class SequencesTests {
	@Test
	public void simpleSequenceTest() {
		List<Integer> nums = List.of(2,4,6);

		Sequence<Integer> snums = Sequence.from(nums);

		snums.forEach(System.out::println);
	}
}
