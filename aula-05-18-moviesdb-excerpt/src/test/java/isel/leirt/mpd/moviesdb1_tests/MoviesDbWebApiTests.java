package isel.leirt.mpd.moviesdb1_tests;

import isel.leirt.mpd.moviesdb1.MoviesDbWebApi;

import isel.leirt.mpd.moviesdb1.dto.TvSeriesDto;
import isel.leirt.mpd.moviesdb1.requests.HttpRequest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MoviesDbWebApiTests
{

	@Test
	public void getTvSeriesByNameTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<TvSeriesDto> series =
			api.searchTvSeries(1, "Breaking");
		assertEquals(20, series.size());
		series.forEach(System.out::println);
	}

	/*
	@Test
	public void getGenresTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<GenreDto> genres = api.getGenres();
		genres.forEach(System.out::println);
		assertEquals(16, genres.size());
	}

	@Test
	public void getAnimationTvSeriesTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());
		int animationGenreId = 16;
		List<TvSeriesDto> series =
			 api.discoveryTvSeries(1, animationGenreId);
		assertEquals(20, series.size());
	}

	@Test
	public void getActorsOfWestWorldSeriesTest() {
		int westWorldSeriesId = 63247;
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		List<ActorDto> actors =
			api.tvSeriesActors(westWorldSeriesId);
		assertEquals(7, actors.size());
	}

	@Test
	public void getRachelWoodTvSeriesTest() {
		MoviesDbWebApi api = new MoviesDbWebApi(new HttpRequest());

		int rachelWoodId = 38940;
		List<TvSeriesDto> series =
			api.actorTvSeries(rachelWoodId);
		assertEquals(18, series.size());
	}
	*/
}