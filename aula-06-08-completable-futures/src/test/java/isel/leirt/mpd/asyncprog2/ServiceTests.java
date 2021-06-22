package isel.leirt.mpd.asyncprog2;

import isel.leirt.mpd.asyncprog.implementations.BestTranslator;
import isel.leirt.mpd.asyncprog.implementations.BestTranslatorCF;
import isel.leirt.mpd.asyncprog.services.TranslateService;
import isel.leirt.mpd.asyncprog.services.async.TranslateServiceCF;
import isel.leirt.mpd.asyncprog2.implementations.SimpleDocumentService;
import isel.leirt.mpd.asyncprog2.services.DocumentServices;
import isel.leirt.mpd.asyncprog2.usecases.DocTransUseCases;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class ServiceTests {
	@Test
	public void simpleTranslatorTest() {
		TranslateServiceCF serv = new BestTranslatorCF();
		String expected = "Adeus";

		var futResult =
			serv
			.translateAsync("en", "pt", "GoodBye")
			.thenApply(os -> {
				/*
				if (os.isPresent() && os.get().equalsIgnoreCase(expected)) return true;
				else return false;
				*/
				System.out.println("Inn thenApply callback: " +LocalTime.now());
				return os.map(s -> s.equalsIgnoreCase(expected)).orElse(false);

			});

		System.out.println("Before join: " + LocalTime.now());

		Assert.assertTrue(futResult.join());

		System.out.println("After join: " +LocalTime.now());

	}

	@Test
	public void translationAndPublishTest() {
		TranslateServiceCF serv1 = new BestTranslatorCF();
		DocumentServices serv2 = new SimpleDocumentService();

		DocTransUseCases.produceDocument(serv1, serv2, "Good morning", "en", "pt", "de")
		.join();

	}

}
