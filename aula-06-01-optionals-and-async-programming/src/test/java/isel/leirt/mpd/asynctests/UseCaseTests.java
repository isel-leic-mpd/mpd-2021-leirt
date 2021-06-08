package isel.leirt.mpd.asynctests;

import isel.leirt.mpd.asyncprog.implementations.BestTranslator;
import isel.leirt.mpd.asyncprog.implementations.BestTranslatorCb;
import isel.leirt.mpd.asyncprog.implementations.BestTranslatorFuture;
import org.junit.Test;

import java.util.concurrent.Semaphore;

import static isel.leirt.mpd.asyncprog.usecases.TranslationOperations.*;
import static isel.leirt.mpd.asyncprog.utils.ThreadUtils.sleep;

public class UseCaseTests {

	@Test
	public void sequentialTwoLanguagesTranslationTest() {
		BestTranslator serv = new BestTranslator();

		long start = System.currentTimeMillis();
		var result =
			twoLanguagesSequentialTranslation(serv,"pt", "it",
				"de", "adeus");
		System.out.println("result: " + result);
		System.out.println("passed time: " + (System.currentTimeMillis() - start) + "ms" );
	}



	@Test
	public void parallelTwoLanguagesTranslationTest() {
		BestTranslator serv = new BestTranslator();

		long start = System.currentTimeMillis();
		var result =
			twoLanguagesParallelTranslation(serv,"pt", "it",
				"de", "adeus");
		System.out.println("result: " + result);
		System.out.println("passed time: " + (System.currentTimeMillis() - start) + "ms" );
	}

	@Test
	public void TwoLanguagesWithRawFuturesTranslationTest() throws Exception  {
		BestTranslator serv = new BestTranslator();

		long start = System.currentTimeMillis();
		var result =
			twoLanguagesWithRawFuturesTranslation(serv,"pt", "it",
				"de", "adeus");
		System.out.println("result: " + result);
		System.out.println("passed time: " + (System.currentTimeMillis() - start) + "ms" );
	}

	@Test
	public void TwoLanguagesWithFuturesServiceTranslationTest() throws Exception  {
		BestTranslatorFuture serv = new BestTranslatorFuture();

		long start = System.currentTimeMillis();
		var result =
			twoLanguagesWithFuturesServicesTranslation(serv,"pt", "it",
				"de", "adeus");
		System.out.println("result: " + result);
		System.out.println("passed time: " + (System.currentTimeMillis() - start) + "ms" );
	}

	@Test
	public void TwoLanguagesAsyncWithCallbacksTranslationTest()  throws Exception {
		BestTranslatorCb serv = new BestTranslatorCb();

		long start = System.currentTimeMillis();

		Semaphore sem = new Semaphore(0);

		twoLanguagesAsyncWithCallbacks(serv,"pt", "it",
			"de", "adeus",
			p -> {
				System.out.println("Pair received: " + p);
				sem.release();
			});

		sem.acquire();

		System.out.println("passed time: " + (System.currentTimeMillis() - start) + "ms" );
	}

}
