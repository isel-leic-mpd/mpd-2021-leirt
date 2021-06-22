package isel.leirt.mpd.weather6.requests;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ResourceRequest implements AsyncRequest {

	@Override
	public CompletableFuture<Reader> get(String path) {
		try {
			path = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('&'))
				.replace('&', '-')
				.replace('=', '-')
				.replace('?', '-')
				.replace(',', '-') + ".txt";

			Reader reader = new InputStreamReader(ClassLoader.getSystemResource(path).openStream());
			return CompletableFuture.completedFuture(reader);
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
