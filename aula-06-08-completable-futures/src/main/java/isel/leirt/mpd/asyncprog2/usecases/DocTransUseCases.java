package isel.leirt.mpd.asyncprog2.usecases;

import isel.leirt.mpd.asyncprog.services.async.TranslateServiceCF;
import isel.leirt.mpd.asyncprog2.services.DocumentServices;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

public class DocTransUseCases {

	public static CompletableFuture<Boolean> produceDocument(TranslateServiceCF translateService, DocumentServices docService,
	                                           String text, String srcLang, String dstLang1, String dstLang2) {

		var t1 = translateService.translateAsync(srcLang, dstLang1, text);
		var t2 = translateService.translateAsync(srcLang, dstLang2, text);

		return t1
				.whenComplete((os, t) -> System.out.println("on t1 completion:  " + LocalTime.now()))
				.thenCombine(t2, (os1, os2) -> docService.build(text, os1.get(), os2.get()))
				.whenComplete((os, t) -> System.out.println("on build completion:  " + LocalTime.now()))
				.thenCompose(s -> docService.publish(s))
				.whenComplete((os, t) -> System.out.println("on publish completion:  " + LocalTime.now()));

	}
}
