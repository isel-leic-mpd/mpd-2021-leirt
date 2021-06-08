package isel.leirt.mpd.asyncprog.implementations;

import isel.leirt.mpd.asyncprog.services.TranslateService;

import java.util.*;

import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

public class BestTranslator implements TranslateService {

	private static class LangText implements Comparable<LangText> {
		private static final int NO_CODE = -1;

		public final String text;
		public final String lang;


		public LangText(String text, String lang) {
			this.text = text; this.lang = lang;
		}

	    @Override
	    public boolean equals(Object o) {
			if (o.getClass() != LangText.class) return false;
			LangText other = (LangText) o;
			return text.equalsIgnoreCase(other.text) && lang.equalsIgnoreCase(other.lang);
	    }

	    @Override
	    public int hashCode() {
			return text.toLowerCase().hashCode() ^ lang.toLowerCase().hashCode();
	    }

		@Override
		public int compareTo(LangText other) {
			int res = text.toLowerCase().compareTo(other.text.toLowerCase());
			if (res != 0) return res;
			return lang.toLowerCase().compareTo(other.lang.toLowerCase());
		}
	}

	private final LangText[] db = {
		new LangText("Bom dia", "pt"),
		new LangText("Good morning", "en"),
		new LangText("Guten Morgen", "de"),
		new LangText("Buongiorno", "it"),

		new LangText("Adeus", "pt"),
		new LangText("Goodbye", "en"),
		new LangText("Auf Wiedersehen", "de"),
		new LangText("addio", "it")
	};

	private final Map<LangText, Integer> phrases;

	private final Map<Integer, List<LangText>> transDB;


	public BestTranslator() {
		phrases = new HashMap<>();
		for(int i=0; i < 4; ++i) phrases.put(db[i], 1);
		for(int i=4; i < 8; ++i) phrases.put(db[i], 2);

		transDB = Map.of(
			1,
			List.of(
				db[0], db[1], db[2], db[3]
			),
			2,
			List.of(
				db[4], db[5], db[6], db[7]
			)
		);
	}

	private static Optional<String> searchTextOfLang(List<LangText> l, String lang) {
		return l.stream()
				.filter(lt -> lt.lang.equalsIgnoreCase(lang))
				.map(lt-> lt.text)
				.findFirst();
	}

	@Override
	public Optional<String> translate(String srcLang, String dstLang, String text) {
		// simulate time
		sleep(3000);
		Integer key = phrases.get(new LangText(text, srcLang));
		if (key == null) return Optional.empty();
		List<LangText> translations = transDB.get(key);
		if (translations == null) return Optional.empty();
		return searchTextOfLang(translations, dstLang);
	}
}
