package isel.leirt.mpd.asyncprog.services;

import java.util.Arrays;
import java.util.Optional;

public interface TranslateService {
	Optional<String> translate(String srcLang, String dstLang, String text);

}
