package isel.leirt.mpd.asyncprog.utils;

public class ThreadUtils {

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch(InterruptedException e) {}
	}

	public static void join(Thread thread) {
		try {
			thread.join();
		}
		catch(InterruptedException e) {}
	}
}
