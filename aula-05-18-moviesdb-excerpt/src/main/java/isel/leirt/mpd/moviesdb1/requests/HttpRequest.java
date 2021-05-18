package isel.leirt.mpd.moviesdb1.requests;

import java.io.*;
import java.net.URL;

public class HttpRequest  implements Request {

	@Override
	public Reader get(String path) {
		try {
			URL url = new URL(path);
			InputStream input = url.openStream();
			return new InputStreamReader(input);
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
