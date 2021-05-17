package isel.leirt.mpd.weather5.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
