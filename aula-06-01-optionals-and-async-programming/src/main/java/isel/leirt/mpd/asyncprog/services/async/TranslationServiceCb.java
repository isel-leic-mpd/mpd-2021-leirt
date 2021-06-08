package isel.leirt.mpd.asyncprog.services.async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface TranslationServiceCb
{
	void translateAsync(String srcLang, String dstLang, String text,
	                    Consumer<Optional<String>> callback);
}
