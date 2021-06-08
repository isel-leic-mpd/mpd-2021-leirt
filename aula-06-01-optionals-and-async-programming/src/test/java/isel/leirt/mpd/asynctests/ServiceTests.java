package isel.leirt.mpd.asynctests;

import isel.leirt.mpd.asyncprog.implementations.BestTranslator;
import isel.leirt.mpd.asyncprog.services.TranslateService;
import org.junit.Test;


public class ServiceTests {

	@Test
	public void simpleTranslatorTest() {
		TranslateService serv = new BestTranslator();

		System.out.println(serv.translate("pt", "es", "Bom dia").orElse("Unknown"));
		System.out.println(
			serv.translate("en", "it", "Good Morning").orElse("Unknown"));
	}
}
