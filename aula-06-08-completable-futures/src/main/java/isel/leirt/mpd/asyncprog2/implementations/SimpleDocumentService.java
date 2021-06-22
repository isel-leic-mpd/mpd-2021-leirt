package isel.leirt.mpd.asyncprog2.implementations;

import isel.leirt.mpd.asyncprog2.services.DocumentServices;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

public class SimpleDocumentService implements DocumentServices {
	private static final ExecutorService pool =
		Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors());


	@Override
	public CompletableFuture<Boolean> publish(String text) {
		CompletableFuture<Boolean> result = new CompletableFuture<>();
		pool.submit(() -> {
			sleep(2000);
			result.complete(true);
		});
		return result;
	}

	@Override
	public String build(String[] textParts) {
		StringBuilder textBuilder = new StringBuilder();
		for(String p : textParts)
			textBuilder.append(p);
		return textBuilder.toString();
	}
}
