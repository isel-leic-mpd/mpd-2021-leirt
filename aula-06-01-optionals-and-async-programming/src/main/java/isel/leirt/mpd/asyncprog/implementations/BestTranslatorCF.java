package isel.leirt.mpd.asyncprog.implementations;

import isel.leirt.mpd.asyncprog.services.async.TranslateServiceCF;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

public class BestTranslatorCF  extends BestTranslator implements TranslateServiceCF {
	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());


	@Override
	public CompletableFuture<Optional<String>> translateAsync(String srcLang, String dstLang, String text) {
		CompletableFuture<Optional<String>> cfTranslation = new CompletableFuture<>();
		pool.submit(() -> {
			sleep(3000);
			cfTranslation.complete(translate(srcLang, dstLang, text));
		});
		return cfTranslation;
	}
}
