package isel.leirt.mpd.weather6.requests;

import java.io.Reader;
import java.util.concurrent.CompletableFuture;

public class CounterRequest implements AsyncRequest {

	private final AsyncRequest origReq;
	private int count;

	public CounterRequest(AsyncRequest origReq) {
		this.origReq =origReq;
	}

	@Override
	public CompletableFuture<Reader> get(String path) {
		count++;
		return origReq.get(path);
	}

	public int getCount() {
		return count;
	}
}
