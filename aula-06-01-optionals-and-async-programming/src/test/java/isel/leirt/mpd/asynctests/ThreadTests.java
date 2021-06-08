package isel.leirt.mpd.asynctests;

import org.junit.Test;

import static isel.leirt.mpd.asyncprog.threads.ThreadExamples.helloThread;

public class ThreadTests {

	@Test
	public void threadHelloTest() throws InterruptedException {
		helloThread();
	}

}
