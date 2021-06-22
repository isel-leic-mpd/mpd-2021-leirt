package isel.leirt.mpd.weather6.requests;


import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.asynchttpclient.Dsl.asyncHttpClient;


public class HttpRequest implements AsyncRequest {
	private static void ahcClose(AsyncHttpClient client) {
		try {
			client.close();
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}


	@Override
	public CompletableFuture<Reader>
	get(String path) {

		AsyncHttpClient client = asyncHttpClient();

		return  client.prepareGet(path)
			.execute()
			.toCompletableFuture()
			.thenApply( response -> {
				Reader reader = new InputStreamReader(response.getResponseBodyAsStream());
				return reader;
			})
			.whenComplete((s,e) -> ahcClose(client));
	}
}
