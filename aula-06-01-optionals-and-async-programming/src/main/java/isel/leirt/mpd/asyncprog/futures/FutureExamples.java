package isel.leirt.mpd.asyncprog.futures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureExamples {

	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());

	public static void helloFuture() throws Exception {
		var future = pool.submit(() -> {
			long tid = Thread.currentThread().getId();
			System.out.println("Hello from thread " + tid);
			if (true)
				throw new RuntimeException("Just for illustration purposes");
			return tid;
		});

		long futId = future.get();
		System.out.println("future " + futId + " launcher thread is " +
			Thread.currentThread().getId());
	}
}
