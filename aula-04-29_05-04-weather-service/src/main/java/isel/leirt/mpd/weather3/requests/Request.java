package isel.leirt.mpd.weather3.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
