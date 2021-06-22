package isel.leirt.mpd.weather6.requests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.CompletableFuture;

public interface AsyncRequest {
	CompletableFuture<Reader> get(String path);

	static Reader readerFromInputStream(InputStream input) {
		return new InputStreamReader(input);
	}
}
