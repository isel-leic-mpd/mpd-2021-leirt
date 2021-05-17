package isel.leirt.mpd.weather4;

import com.google.gson.Gson;
import isel.leirt.mpd.weather4.requests.HttpRequest;
import isel.leirt.mpd.weather4.requests.Request;
import isel.leirt.mpd.weather4.dto.*;


import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OpenWeatherWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.openweathermap.org/";

    private static final String LOCATION_SEARCH_TEMPLATE =
        "geo/1.0/direct?q=%s&limit=10&lang=pt&appid=%s";

    private static final String LOCATION_PAST_WEATHER_TEMPLATE =
        "data/2.5/onecall/timemachine?lat=%.3f&lon=%.3f&dt=%d&units=metric&appid=%s";

    private static final String AREA_WEATHER_TEMPLATE =
        "data/2.5/box/city?bbox=%.3f,%.3f,%.3f,%.3f,50&units=metric&appid=%s";

    private static final String WEATHER_TEMPLATE =
            "data/2.5/weather?lat=%.3f&lon=%.3f&units=metric&appid=%s";

    private static final String FORECAST_WEATHER_TEMPLATE =
        "data/2.5/onecall?lat=%.3f&lon=%.3f&exclude=hourly,minutely,current&units=metric&appid=%s";

    private static final String FORECAST_HOURLY_TEMPLATE =
        "data/2.5/onecall?lat=%.3f&lon=%.3f&exclude=minutely,current&units=metric&appid=%s";

    protected final Gson gson;
    private final Request req;

    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                    ClassLoader.getSystemResource("openweatherapi-app-key.txt");
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key.txt");
        }
    }

    /**
     * Static Constructor
     */
    static {
        API_KEY = getApiKeyFromResources();
    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=37.017,-7.933&date=2019-01-01&enddate=2019-01-30&tp=24&format=csv&key=10a7e54b547c4c7c870162131192102
     * Get WeatherInfo's from a GPS local given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public WeatherInfoDto weatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(WEATHER_TEMPLATE, lat, lon, API_KEY);

        return gson.fromJson(req.get(path), WeatherInfoDto.class);
    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=37.017,-7.933&date=2019-01-01&enddate=2019-01-30&tp=24&format=csv&key=10a7e54b547c4c7c870162131192102
     * Get WeatherInfo's from a GPS local given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public List<ForecastWeatherInfoDto> forecastWeatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(FORECAST_WEATHER_TEMPLATE, lat, lon, API_KEY);

        ForecastWeatherInfoQueryDto winfo =
            gson.fromJson(req.get(path), ForecastWeatherInfoQueryDto.class);
        return Arrays.asList(winfo.getForecastInfo());

    }

    public List<ForecastHourlyInfo> forecastDetailAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(FORECAST_HOURLY_TEMPLATE, lat, lon, API_KEY);
        ForecastWeatherInfoQueryDto winfo =
            gson.fromJson(req.get(path), ForecastWeatherInfoQueryDto.class);
        return Arrays.asList(winfo.getHourlyInfo());

    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=37.017,-7.933&date=2019-01-01&enddate=2019-01-30&tp=24&format=csv&key=10a7e54b547c4c7c870162131192102
     * Get WeatherInfo's from a GPS local given a date interval
     * @param lati
     * @param longi
     * @param latf
     * @param longf
     * @return
     */
    public List<WeatherInfoDto> weatherAtArea(double lati, double longi, double latf, double longf) {
        String path =  WEATHER_SERVICE + String.format(AREA_WEATHER_TEMPLATE, lati, longi,latf,longf, API_KEY);
        Reader reader = req.get(path);
        AreaWeatherQueryDto winfo =
            gson.fromJson(reader, AreaWeatherQueryDto.class);
        return Arrays.asList(winfo.list);
    }


    /**
     * Get DayInfo's from a GPS local given a date interval
     * @param lat
     * @param lon
     * @param day
     * @return
     */
    public List<PastWeatherInfoDto> pastDayAt(double lat,
                                              double lon, LocalDate day) {
        long unixTime = Utils.toUnixTime(day);
        String path =  WEATHER_SERVICE + String.format(LOCATION_PAST_WEATHER_TEMPLATE, lat, lon, unixTime,API_KEY);

        PastWeatherInfoQueryDto query =
            gson.fromJson(req.get(path), PastWeatherInfoQueryDto.class);
        return Arrays.asList(query.hourly());

    }

    /**
     * e.g. http://api.worldweatheronline.com/premium/v1/search.ashx?query=Oporto&format=tab&key=10a7e54b547c4c7c870162131192102
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public List<LocationDto> search(String location) {
        String path =  WEATHER_SERVICE + String.format(LOCATION_SEARCH_TEMPLATE, location, API_KEY);

        LocationDto[] search = gson.fromJson(req.get(path), LocationDto[].class);
        return Arrays.asList(search);
    }

    public OpenWeatherWebApi(Request req ) {
        this.req = req;
        gson = new Gson();
    }

    public OpenWeatherWebApi() {
       this(new HttpRequest());
    }


}
