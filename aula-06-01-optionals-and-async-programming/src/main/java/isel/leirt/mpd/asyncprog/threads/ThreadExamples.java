package isel.leirt.mpd.asyncprog.threads;

import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.join;
import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

public class ThreadExamples {

	public static void helloThread() {

		Thread thread = new Thread(() -> {
			System.out.println("Hello from thread " + Thread.currentThread().getId());
		});

		thread.start();

		join(thread);
		System.out.println("Creator thread is " + Thread.currentThread().getId());
	}
}
