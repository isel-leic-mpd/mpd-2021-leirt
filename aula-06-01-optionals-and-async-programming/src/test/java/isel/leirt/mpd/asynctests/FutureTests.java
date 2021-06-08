package isel.leirt.mpd.asynctests;

import org.junit.Test;

import static isel.leirt.mpd.asyncprog.futures.FutureExamples.helloFuture;
import static isel.leirt.mpd.asyncprog.threads.ThreadExamples.helloThread;

public class FutureTests {

	@Test
	public void threadHelloTest() throws Exception {
		helloFuture();
	}

}
