package app;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import isel.leirt.mpd.weather6.model.DayInfo;
import isel.leirt.mpd.weather6.model.Location;
import isel.leirt.mpd.weather6.requests.HttpRequest;
import mappers.DaysMappers;

import mappers.LocationMappers;
import isel.leirt.mpd.weather6.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Stream;

import static app.View.*;


public class Main {

    private static OpenWeatherWebApi api = new OpenWeatherWebApi(new HttpRequest());

    private static OpenWeatherService service = new OpenWeatherService(api);

    public static void main(String[] args) throws Exception {

        Router router = Router.router(Vertx.vertx());

        router.route("/").handler(Main::root);
        router.route("/location").handler(Main::getLocation);
        router.route("/weather/:coords/forecast").handler(Main::getForecast);
        router.route("/css/:name").handler(Main::getResource);
        router.route("/images/moon_phases/:name").handler(Main::getImage);

        Vertx.vertx()
            .createHttpServer()
            .requestHandler(router)
            .listen(8000);
    }


    //
    // controllers
    //

    private static void root(RoutingContext ctx) {
        HttpServerResponse resp = ctx.response();
        System.out.println(
            "root handler, thread " +
                Thread.currentThread().getId()
        );

        buildDefaultPage(resp);
    }


    private static void getLocation(RoutingContext ctx) {
        HttpServerResponse resp = ctx.response();
        String locationName = ctx.request().getParam("name");
        Optional<String>
            country = Optional.ofNullable(ctx.request().getParam("country"));

        CompletableFuture<Stream<Location>> locs =
                service.search(locationName)
                .thenApply( sl ->
                        sl.filter(loc ->
                                 country.map(c -> loc.getCountry().equalsIgnoreCase(c))
                                 .orElse(true)
                        ));


        buildTableView(
                resp,
                String.format("locations named %s", locationName),
                locs,

                LocationMappers::mapToTableRaw,
                "Name", "Country", "Latitude", "Longitude", "Forecast");
    }

    private static void getResource(RoutingContext ctx) {
        HttpServerResponse resp = ctx.response();
        String resourceName = ctx.request().getParam("name");
        sendResource(resp, resourceName);

    }

    private static void getImage(RoutingContext ctx) {
        HttpServerResponse resp = ctx.response();
        String resourceName = ctx.request().getParam("name");
        sendImage(resp, "moon_phases/" + resourceName);

    }

    private static void getForecast(RoutingContext ctx)  {
        HttpServerResponse resp = ctx.response();
        String[] latLong = ctx.request().getParam("coords").split(":");
        String locName = ctx.request().getParam("loc");
        double latitude, longitude;

        latitude =  Double.valueOf(latLong[0]).doubleValue();
        longitude = Double.valueOf(latLong[1]).doubleValue();

        LocalDate today = LocalDate.now();

        Location loc = Location.of(latitude, longitude);
        CompletableFuture<Stream<DayInfo>>  days =  service.forecastAt(loc);

        buildTableView(resp,
                String.format("Weather forecast for %s at %s", locName, today),
                days,
                DaysMappers::mapToTableRaw,
        "Date","Icon", "Min Temp", "Max Temp", "Moon<br/>Phase", "Sunrise ", "Sunset ");
    }


}
