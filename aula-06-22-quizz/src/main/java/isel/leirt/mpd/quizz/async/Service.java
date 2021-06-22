package isel.leirt.mpd.quizz.async;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface Service {

	CompletableFuture<ProductPrice> getPrice(String productName);

	default CompletableFuture<Stream<ProductPrice>>  getPrices(Stream<String> productNames) {

		return null;
	}

	static CompletableFuture<Stream<ProductPrice>>
	lessExpensiveProducts(Service serv1, Service serv2, Stream<String> prodNames) {
		return null;
	}
}
