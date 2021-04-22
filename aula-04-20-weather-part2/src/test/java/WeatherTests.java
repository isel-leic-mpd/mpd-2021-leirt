import isel.leirt.mpd.weather2.dto.*;
import isel.leirt.mpd.weather2.OpenWeatherWebApi;
import isel.leirt.mpd.weather2.queries.Queries0;
import isel.leirt.mpd.weather2.queries.WeatherPredicate;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static isel.leirt.mpd.weather2.queries.Queries0.*;

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

        for(ForecastWeatherInfo fwi : winfo) {
           System.out.println(fwi);
        }
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
    public void getSunnyCities() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        Iterable<WeatherInfo> sunny_cities =
            Queries0.getWeatherFromDescription(cities, "sky");

        int total=0;
        for(WeatherInfo wi : sunny_cities)
            total++;

        System.out.println("total sunny cities: " + total);
        for(WeatherInfo wi : sunny_cities)
            System.out.println(wi);
    }

    @Test
    public void getSunnyCitiesFromFilter() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        /*
        WeatherPredicate sunnyPred = new WeatherPredicate() {
            @Override
            public boolean test(WeatherInfo wi) {
                return wi.description().contains("sky");
            }
        };
        */

        /*
        WeatherPredicate sunnyPred =
            (WeatherInfo wi) -> wi.description().contains("sky");


        WeatherPredicate sunnyPred =
            wi -> wi.description().contains("sky");
        */

        WeatherPredicate sunnyPred = wi ->  {
            return wi.description().contains("sky");
        };

        Iterable<WeatherInfo> sunny_cities1 =
            Queries0.filter(cities,sunnyPred);

        Iterable<WeatherInfo> sunny_cities =
            Queries0.filter(cities, wi ->  wi.description().contains("sky"));

        int total=0;
        for(WeatherInfo wi : sunny_cities)
            total++;

        System.out.println("total sunny cities: " + total);
        for(WeatherInfo wi : sunny_cities)
            System.out.println(wi);
    }

    @Test
    public void getRainyCitiesTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        Iterable<WeatherInfo>  rainy_cities =
            Queries0.getWeatherFromDescription(cities, "rain");

        int total=0;
        for(WeatherInfo wi : rainy_cities)
            total++;

        System.out.println("total rainy cities: " + total);
        for(WeatherInfo wi : rainy_cities)
            System.out.println(wi);
    }

    @Test
    public void getRainyCitiesFromFilterTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfo> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        WeatherPredicate rainyPred = new WeatherPredicate() {
            @Override
            public boolean test(WeatherInfo wi) {
                return wi.description().contains("rain");
            }
        };

        Iterable<WeatherInfo> rainy_cities =
            Queries0.filter(cities, rainyPred);

        int total=0;
        for(WeatherInfo wi : rainy_cities)
            total++;

        System.out.println("total rainy cities: " + total);
        for(WeatherInfo wi : rainy_cities)
            System.out.println(wi);
    }





    /// Tests for new OpenWeatherWebApi operations

    @Test
    public void getWeatherForLisbonAtApril17Test() {

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021,04,17));

        Assert.assertEquals(24, past3Days.size());
        for(PastWeatherInfo pwi : past3Days) {
            System.out.println(pwi);
        }
    }

    @Test
    public void getHourlyTemperaturesForLisbonAtApril17Test() {

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021,04,17));

        Iterable<Double> temps =
            Queries0.pastWeatherInfoToDoubles(past3Days, wi -> wi.getTemp());

        int total=0;
        for(Double d : temps)
            total++;

        Assert.assertEquals(24, total);
        for(double d : temps) {
            System.out.println(d);
        }
    }

    @Test
    public void maxTempForLisbonAtApril17Between1And8HoursUsingQueries0Test() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021, 04, 17));

        /*


        Iterable<PastWeatherInfo> pwiFrom8To19 =
            pastWeatherInfoFilter(past3Days,
                pwi -> pwi.obsTime().compareTo(startTime) >= 0 &&
                    pwi.obsTime().compareTo(endTime) <= 0);

        Iterable<Double> temps = pastWeatherInfoToDoubles(
            pwiFrom8To19,
            pwi-> pwi.getTemp()
        );
        */

        Double maxTemp =
            maxDouble(
                pastWeatherInfoToDoubles(
                    pastWeatherInfoFilter(past3Days,
                        pwi -> pwi.obsTime().compareTo(startTime) >= 0 &&
                            pwi.obsTime().compareTo(endTime) <= 0),
                pwi-> pwi.getTemp()
            )
        );

        /*
        double maxTemp = Double.MIN_VALUE;


        for(Double d : temps) {
            if (d > maxTemp)
                maxTemp = d;
        }

        */

        System.out.println("max temp between " + startTime
            + " and " + endTime
            + " = " + maxTemp);
    }

    @Test
    public void maxTempForLisbonAtApril17Between1And8HoursTest() {

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(19, 0);
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<PastWeatherInfo> past3Days =
            webApi.pastDayAt(LISBON_LAT, LISBON_LONG, LocalDate.of(2021,04,17));

        Assert.assertEquals(24, past3Days.size());

        double maxTemp = Double.MIN_VALUE;


        for(PastWeatherInfo pwi : past3Days) {
           if (pwi.obsTime().compareTo(startTime) >= 0 &&
              pwi.obsTime().compareTo(endTime) <= 0) {
               System.out.println(pwi);
               if (pwi.getTemp() > maxTemp)
                   maxTemp = pwi.getTemp();

           }
        }
        System.out.println("max temp between " + startTime
                            + " and " + endTime
                            + " = " + maxTemp);
    }

    @Test
    public void getLocationTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        Location[] locs = webApi.search("Lisbon");

        for(Location loc: locs) {
            System.out.println(loc);
        }
    }

}
