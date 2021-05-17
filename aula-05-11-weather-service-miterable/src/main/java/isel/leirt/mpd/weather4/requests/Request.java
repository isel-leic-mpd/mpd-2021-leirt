package isel.leirt.mpd.weather4.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
