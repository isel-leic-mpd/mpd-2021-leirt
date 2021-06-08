package isel.leirt.mpd.asyncprog.implementations;

import isel.leirt.mpd.asyncprog.services.async.TranslateServiceFut;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BestTranslatorFuture extends BestTranslator implements TranslateServiceFut {
	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());


	@Override
	public Future<Optional<String>> translateAsync(String srcLang, String dstLang, String text) {
		return pool.submit(() -> translate(srcLang, dstLang, text));
	}
}
