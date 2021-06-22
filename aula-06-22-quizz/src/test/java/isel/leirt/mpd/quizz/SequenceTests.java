package isel.leirt.mpd.quizz;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SequenceTests {


	@Test
	public void SequenceOddOperationTest() {
		Object[] expected = { 4, 8, 15};

		List<Integer> list =
			Sequence
			.of(4, 6, 8, 17, 15, 8)
			.odds()
			.toList();

		Assert.assertArrayEquals(expected, list.toArray() );

	}
}
