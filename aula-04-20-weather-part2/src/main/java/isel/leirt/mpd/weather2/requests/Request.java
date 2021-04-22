package isel.leirt.mpd.weather2.requests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public interface Request {
	Reader getReader(String path);
}
