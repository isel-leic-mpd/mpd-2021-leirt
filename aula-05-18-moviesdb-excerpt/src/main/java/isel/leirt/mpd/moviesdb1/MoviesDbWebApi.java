package isel.leirt.mpd.moviesdb1;

import com.google.gson.Gson;
import isel.leirt.mpd.moviesdb1.dto.*;
import isel.leirt.mpd.moviesdb1.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;


public class MoviesDbWebApi {

	public final static int API_PAGE_SIZE=20;

	private static final String API_KEY;
	private static final String MOVIES_DB_ENDPOINT =  "https://api.themoviedb.org/3/";

	private static final String SEARCH_TV_SERIES = "search/tv?query=%s&page=%d&api_key=%s";
	private final Request req;
	private final Gson gson;

	/**
	 * Retrieve API-KEY from resources
	 * @return
	 */
	private static String getApiKeyFromResources() {
		try {
			URL keyFile =
				ClassLoader.getSystemResource("movies_db_api_key.txt");
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

	public List<TvSeriesDto> searchTvSeries(int page, String seriesName) {
		String path =  MOVIES_DB_ENDPOINT + String.format(SEARCH_TV_SERIES,  seriesName, page, API_KEY);

		DiscoveryTvSeriesDto searchDto =
			gson.fromJson(req.get(path), DiscoveryTvSeriesDto.class);
		return searchDto.getResults();
	}
	
	public MoviesDbWebApi(Request req) {
		this.req = req;
		this.gson = new Gson();
	}
}
