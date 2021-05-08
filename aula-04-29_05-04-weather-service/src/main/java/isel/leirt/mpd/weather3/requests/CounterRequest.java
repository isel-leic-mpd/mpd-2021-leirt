package isel.leirt.mpd.weather3.requests;

import java.io.Reader;

public class CounterRequest implements Request {

	private final Request origReq;
	private int count;

	public CounterRequest(Request origReq) {
		this.origReq =origReq;
	}

	@Override
	public Reader get(String path) {
		count++;
		return origReq.get(path);
	}

	public int getCount() {
		return count;
	}
}
