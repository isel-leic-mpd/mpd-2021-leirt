package isel.leirt.mpd.asyncprog.services.async;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface TranslateServiceCF {
	CompletableFuture<Optional<String>> translateAsync(String srcLang, String dstLang, String text);

}
