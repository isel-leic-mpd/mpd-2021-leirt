package isel.leirt.mpd.asyncprog2.services;

import java.util.concurrent.CompletableFuture;

public interface DocumentServices {
	CompletableFuture<Boolean> publish(String text);

	String build(String ... textParts);
}
