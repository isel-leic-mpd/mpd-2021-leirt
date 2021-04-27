package isel.leirt.mpd.weather3tests;


import isel.leirt.mpd.lazyqueries2.Queries;
import isel.leirt.mpd.weather3.OpenWeatherWebApi;
import isel.leirt.mpd.weather3.dto.ForecastWeatherInfo;
import isel.leirt.mpd.weather3.dto.PastWeatherInfo;
import isel.leirt.mpd.weather3.dto.WeatherInfo;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static isel.leirt.mpd.lazyqueries2.Queries.*;

public class WeatherTests {

    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;


    @Test
    public void getWeatherForLisbonTest() {

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        WeatherInfo winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );

        System.out.println(winfo);
    }

    @Test
    public void getForecastWeatherForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        Iterable<ForecastWeatherInfo> winfo = webApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);

        winfo.forEach(System.out::println);

    }


    @Test
    public void portugalCitiesWeatherTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);
        Assert.assertTrue(cities.size() > 0);

        System.out.println("found " + cities.size() + " cities.");
        for(WeatherInfo wi: cities)
            System.out.println(wi);
    }


    @Test
    public void getSunnyCitiesFromFilter() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);


        Iterable<WeatherInfo> sunny_cities =
            filter(cities, wi ->  wi.description().contains("sky"));


        System.out.println("total sunny cities: " +
                            count(sunny_cities));

        sunny_cities.forEach(wi -> System.out.println(wi));

    }




    @Test
    public void getRainyCitiesFromFilterTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        Iterable<WeatherInfo> rainy_cities =
            filter(cities, wi->  wi.description().contains("rain"));


        System.out.println("total rainy cities: " +
                    count(rainy_cities));
        rainy_cities.forEach(System.out::println);

    }


    /// Tests for new OpenWeatherWebApi operations

    @Test
    public void getWeatherForLisbonAtApril17Test() {

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021,04,17));

        Assert.assertEquals(24, past3Days.size());

        past3Days.forEach(System.out::println);

    }

    @Test
    public void getHourlyTemperaturesForLisbonAtApril17Test() {

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021,04,17));

        Iterable<Double> temps =
            Queries.mapToDoubles(past3Days, wi -> wi.getTemp());


        Assert.assertEquals(24,  count(temps));
        temps.forEach(System.out::println);

    }





}
