package isel.leirt.mpd.asyncprog.implementations;

import isel.leirt.mpd.asyncprog.services.async.TranslateServiceFut;
import isel.leirt.mpd.asyncprog.services.async.TranslationServiceCb;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class BestTranslatorCb  extends BestTranslator implements TranslationServiceCb {
	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());

	@Override
	public void translateAsync(String srcLang, String dstLang, String text,
	                           Consumer<Optional<String>> callback) {
		pool.submit(() -> {
			Optional<String> res = translate(srcLang, dstLang, text);
			callback.accept(res);
		});
	}
}
