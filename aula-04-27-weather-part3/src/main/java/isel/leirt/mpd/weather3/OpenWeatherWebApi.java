package isel.leirt.mpd.weather3;

import com.google.gson.Gson;
import isel.leirt.mpd.weather3.dto.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static isel.leirt.mpd.weather3.utils.Utils.toUnixTime;

public class OpenWeatherWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.openweathermap.org/";

    private static final String WEATHER_TEMPLATE =
        "data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s";

    private static final String AREA_WEATHER_TEMPLATE =
        "data/2.5/box/city?bbox=%f,%f,%f,%f,50&units=metric&appid=%s";


    private static final String FORECAST_WEATHER_TEMPLATE =
        "data/2.5/onecall?lat=%f&lon=%f&exclude=hourly,minutely,current&units=metric&appid=%s";

    // New operations templates start

    private static final String LOCATION_SEARCH_TEMPLATE =
        "geo/1.0/direct?q=%s&limit=10&lang=pt&appid=%s";

    private static final String LOCATION_PAST_WEATHER_TEMPLATE =
        "data/2.5/onecall/timemachine?lat=%f&lon=%f&dt=%d&units=metric&appid=%s";

    // New operations templates end


    protected final Gson gson;

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
    public WeatherInfo weatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(WEATHER_TEMPLATE, lat, lon, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                WeatherInfo winfo =
                    gson.fromJson(reader, WeatherInfo.class);
                return winfo;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=37.017,-7.933&date=2019-01-01&enddate=2019-01-30&tp=24&format=csv&key=10a7e54b547c4c7c870162131192102
     * Get WeatherInfo's from a GPS local given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public List<ForecastWeatherInfo> forecastWeatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(FORECAST_WEATHER_TEMPLATE, lat, lon, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                ForecastWeatherInfoQuery winfo =
                    gson.fromJson(reader, ForecastWeatherInfoQuery.class);
                return Arrays.asList(winfo.getForecastInfo());
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
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
    public List<WeatherInfo> weatherAtArea(double lati, double longi, double latf, double longf) {
        String path =  WEATHER_SERVICE + String.format(AREA_WEATHER_TEMPLATE, lati, longi,latf,longf, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                AreaWeatherQuery winfo =
                    gson.fromJson(reader, AreaWeatherQuery.class);
                return Arrays.asList(winfo.list);
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // New Operations start

    /**
     * Get DayInfo's from a GPS local given a date interval
     * @param lat
     * @param lon
     * @param day
     * @return
     */
    public List<PastWeatherInfo> pastDayAt(double lat,
                                           double lon, LocalDate day) {
        long unixTime = toUnixTime(day);
        String path =  WEATHER_SERVICE + String.format(LOCATION_PAST_WEATHER_TEMPLATE, lat, lon, unixTime,API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                PastWeatherInfoQuery query =
                    gson.fromJson(reader, PastWeatherInfoQuery.class);
                return Arrays.asList(query.hourly());
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * e.g. http://api.worldweatheronline.com/premium/v1/search.ashx?query=Oporto&format=tab&key=10a7e54b547c4c7c870162131192102
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public Location[] search(String location) {
        try {
            String path =  WEATHER_SERVICE + String.format(LOCATION_SEARCH_TEMPLATE, location, API_KEY);
            URL url = new URL(path);

            try (Reader reader =  new InputStreamReader(url.openStream())) {
                Location[] search = gson.fromJson(reader, Location[].class);
                return search;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    // New Operations end

    public OpenWeatherWebApi() {

        gson = new Gson();
    }


}
