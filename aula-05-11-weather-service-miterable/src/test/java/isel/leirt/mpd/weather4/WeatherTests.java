package isel.leirt.mpd.weather4;


import isel.leirt.mpd.weather4.OpenWeatherWebApi;
import isel.leirt.mpd.weather4.dto.*;
import isel.leirt.mpd.weather4.queries.MIterable;
import isel.leirt.mpd.weather4.requests.ResourceRequest;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDate;

import java.util.List;

import static isel.leirt.mpd.weather4.queries.MIterable.of;


public class WeatherTests {
    @Test
    public void getLocationTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        List<LocationDto> locs = webApi.search("Portimão");

        for(LocationDto loc: locs) {
           System.out.println(loc);
        }
    }

    @Test
    public void getWeatherForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        Assert.assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        WeatherInfoDto winfo = webApi.weatherAt(loc.getLat(), loc.getLon());

        System.out.println(winfo);
    }
    @Test
    public void getForecastWeatherForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        Assert.assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastWeatherInfoDto> winfo = webApi.forecastWeatherAt(loc.getLat(), loc.getLon());

        for(ForecastWeatherInfoDto fwi : winfo) {
           System.out.println(fwi);
        }
    }

    @Test
    public void getForecastDetailForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        Assert.assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastHourlyInfo> winfo = webApi.forecastDetailAt(loc.getLat(), loc.getLon());

        System.out.println("WeatherInfo list size: " + winfo.size());
        for(ForecastHourlyInfo fwi : winfo) {
            System.out.println(fwi);
        }
    }



    @Test
    public void getWeatherForLisbon3DaysAgoTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<LocationDto> locs = webApi.search("Lisbon");
        Assert.assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<PastWeatherInfoDto> past3Days =
            webApi.pastDayAt(loc.getLat(), loc.getLon(), LocalDate.now().minusDays(3));

        Assert.assertEquals(24, past3Days.size());

        for(PastWeatherInfoDto pwi : past3Days) {
            System.out.println(pwi);
        }
    }

    @Test
    public void portugalCitiesWeatherTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new ResourceRequest());
        List<WeatherInfoDto> cities = webApi.weatherAtArea(longi, lati,  longf, latf);
        Assert.assertTrue(cities.size() > 0);

        for(WeatherInfoDto wi: cities)
            System.out.println(wi);
    }



    @Test
    public void portugalCitiesWithWeekCloudsWeatherTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfoDto> cities = webApi.weatherAtArea(longi, lati,  longf, latf);
        Assert.assertTrue(cities.size() > 0);

        for(WeatherInfoDto wi: cities) {
            if (wi.description().equals("few clouds"))
                System.out.println(wi);
        }
    }


    @Test
    public void PortimaoInPortugalCitiesWeatherTest() {
        double lati = 37.0168, longi = -8.9406;
        double latf = 41.8061, longf = -6.7567;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfoDto> cities = webApi.weatherAtArea(longi, lati,  longf, latf);
        Assert.assertTrue(cities.size() > 0);

        WeatherInfoDto portWeather =
            cities.stream().filter(wi -> wi.observationLocal().equals("Portimao")).findFirst().get();

        System.out.println(portWeather);
    }



    @Test
    public void getRainyCities2Test() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfoDto> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        MIterable<WeatherInfoDto> sunny_cities =
            of(cities)
            .filter(wi ->  wi.description().contains("rain"));

        long total= sunny_cities.count();

        System.out.println("total rainy cities: " + total);
        sunny_cities.forEach((System.out::println));
    }

    /***
     * to test method references
     * @param wi
     * @return
     */
    public double temp(WeatherInfoDto wi) {
        return 23;
    }

    @Test
    public void maxTempTestTest() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        List<WeatherInfoDto> cities = webApi.weatherAtArea(longi, lati,  longf, latf);

        MIterable<Double> temps  =
            of(cities)
            .map(WeatherInfoDto::temp);

        long total= temps.count();

        System.out.println("total temps: " + total);
        temps.forEach(System.out::println);

    }


}
