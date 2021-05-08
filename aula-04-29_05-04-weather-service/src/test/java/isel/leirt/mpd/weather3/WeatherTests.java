package isel.leirt.mpd.weather3;

import isel.leirt.mpd.weather3.dto.*;
import isel.leirt.mpd.weather3.requests.ResourceRequest;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDate;

import java.util.List;

import static isel.leirt.mpd.lazyqueries2.Queries.filter;
import static isel.leirt.mpd.lazyqueries2.Queries.map;

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

        Iterable<WeatherInfoDto> sunny_cities =
            filter(cities, wi -> wi.description().contains("rain"));

        int total=0;
        for(WeatherInfoDto wi : sunny_cities)
            total++;

        System.out.println("total rainy cities: " + total);
        for(WeatherInfoDto wi : sunny_cities)
            System.out.println(wi);
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

        Iterable<Double> temps  =  map(cities, this::temp);

        int total=0;
        for(Double d : temps)
            total++;

        System.out.println("total temps: " + total);
        for(double wi : temps)
            System.out.println(wi);
    }


}
