package isel.leirt.mpd.weather4.requests;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.URL;

public class HttpRequest  implements Request {

	@Override
	public Reader get(String path) {
		try {
			URL url = new URL(path);
			return new InputStreamReader(url.openStream());
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
