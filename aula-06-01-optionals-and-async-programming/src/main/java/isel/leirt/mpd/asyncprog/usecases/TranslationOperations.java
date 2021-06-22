package isel.leirt.mpd.asyncprog.usecases;

import isel.leirt.mpd.asyncprog.services.TranslateService;
import isel.leirt.mpd.asyncprog.services.async.TranslateServiceCF;
import isel.leirt.mpd.asyncprog.services.async.TranslateServiceFut;
import isel.leirt.mpd.asyncprog.services.async.TranslationServiceCb;
import isel.leirt.mpd.asyncprog.utils.Pair;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.join;
import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

@SuppressWarnings("unchecked")
public class TranslationOperations {

	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());

	/**
	 * Sequential operation
	 * @param serv
	 * @param srcLang
	 * @param dstLang1
	 * @param dstLang2
	 * @param text
	 * @return
	 */
	public static Pair<Optional<String>, Optional<String>> twoLanguagesSequentialTranslation(
		TranslateService serv, String srcLang, String dstLang1, String dstLang2, String text) {

		return new Pair<>(serv.translate(srcLang, dstLang1, text),
						serv.translate(srcLang, dstLang2, text)
		);
	}

	/**
	 * Parallelization with threads
	 * @param serv
	 * @param srcLang
	 * @param dstLang1
	 * @param dstLang2
	 * @param text
	 * @return
	 */
	public static Pair<Optional<String>, Optional<String>> twoLanguagesParallelTranslation(
		TranslateService serv, String srcLang, String dstLang1, String dstLang2, String text) {

		Optional<String>[] results = new Optional[2];

		Thread t = new Thread(() -> {
			results[1] = serv.translate(srcLang, dstLang2, text);
		});

		t.start();

		results[0] = serv.translate(srcLang, dstLang1, text);


		join(t);

		return new Pair(results[0], results[1]);
	}

	public static Pair<Optional<String>, Optional<String>> twoLanguagesWithRawFuturesTranslation(
		TranslateService serv, String srcLang, String dstLang1, String dstLang2, String text) throws Exception {

		Future<Optional<String>> translation2 =
			pool.submit(
				() -> {
					return serv.translate(srcLang, dstLang2, text);
				});
		return new Pair<>(serv.translate(srcLang, dstLang1, text), translation2.get());
	}

	public static Pair<Optional<String>, Optional<String>> twoLanguagesWithFuturesServicesTranslation(
		TranslateServiceFut serv, String srcLang, String dstLang1, String dstLang2, String text) throws Exception {

		System.out.println("Before service calls");
		var fut1 = serv.translateAsync(srcLang, dstLang1, text);
		var fut2 = serv.translateAsync(srcLang, dstLang2, text);
		System.out.println("After service calls");
		var p =  new Pair<>(fut1.get(), fut2.get());
		System.out.println("After pair construction ");
		return p;
	}

	public static void twoLanguagesAsyncWithCallbacks(
		TranslationServiceCb serv, String srcLang, String dstLang1, String dstLang2, String text,
		Consumer<Pair<Optional<String>, Optional<String>>> callback )   {

		AtomicInteger ncalls = new AtomicInteger();
		Optional<String>[] res = new Optional[2];

		serv.translateAsync(srcLang, dstLang1, text, o -> {
			System.out.println("Result received: " + o);

			res[0] = o;

			if (ncalls.incrementAndGet() == 2)
				callback.accept(new Pair<>(res[0], res[1]));
		});

		serv.translateAsync(srcLang, dstLang2, text, o -> {
			System.out.println("Result received: " + o);
			res[1] = o;

			if (ncalls.incrementAndGet() == 2)
				callback.accept(new Pair<>(res[0], res[1]));
		});
	}

	public static CompletableFuture<Pair<Optional<String>, Optional<String>>>
		twoLanguagesAsyncWithCompletableFutures (
		TranslateServiceCF serv, String srcLang, String dstLang1, String dstLang2, String text) {

		var cf1 = serv.translateAsync(srcLang, dstLang1, text);
		var cf2 = serv.translateAsync(srcLang, dstLang2, text);

		return cf1.thenCombine(cf2, (os1, os2) -> new Pair<>(os1, os2));

	}
}
