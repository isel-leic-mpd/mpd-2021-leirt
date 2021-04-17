import isel.leirt.mpd.weather.dto.ForecastWeatherInfo;
import isel.leirt.mpd.weather.dto.WeatherInfo;
import isel.leirt.mpd.weather.OpenWeatherWebApi;
import isel.leirt.mpd.weather.queries.Queries0;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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

        Iterable<WeatherInfo> sunny_cities = Queries0.getSunnyCities(cities);

        int total=0;
        for(WeatherInfo wi : sunny_cities)
            total++;

        System.out.println("total sunny cities: " + total);
        for(WeatherInfo wi : sunny_cities)
            System.out.println(wi);
    }

}
