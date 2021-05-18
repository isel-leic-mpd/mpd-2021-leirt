package isel.leirt.mpd.moviesdb1.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
