package isel.leirt.mpd.moviesdb1;

import isel.leirt.mpd.moviesdb1.dto.ActorDto;
import isel.leirt.mpd.moviesdb1.dto.GenreDto;
import isel.leirt.mpd.moviesdb1.dto.TvSeriesDto;
import isel.leirt.mpd.moviesdb1.model.Actor;
import isel.leirt.mpd.moviesdb1.model.Genre;
import isel.leirt.mpd.moviesdb1.model.TvSeries;
import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static isel.leirt.mpd.moviesdb1.queries.Queries.*;

public class MoviesDbService {
	private final MoviesDbWebApi api;

	public Iterable<TvSeries> searchTvSeries(String name, int maxSeries) {
		int maxPages;

		maxPages = maxSeries/MoviesDbWebApi.API_PAGE_SIZE;
		if (maxSeries % MoviesDbWebApi.API_PAGE_SIZE  != 0 )
			maxPages++;


		maxPages = (maxSeries-1)/MoviesDbWebApi.API_PAGE_SIZE +1;

		/*
		Iterable<Integer> nums = range(1, maxPages);
		Iterable<List<TvSeriesDto>> seriesLists = map(nums, n -> api.searchTvSeries(n, name));
		var  nonEmptySeriesLists = takeWhile(seriesLists, list -> list.size() > 0);
		var tvSeriesDtos = flatMap(nonEmptySeriesLists, list -> list);
		var tvSeries = map(tvSeriesDtos, this::dtoToTvSeries);
		return tvSeries;
		*/

		return  map(
					limit(
						flatMap(
							takeWhile(
								map(
									range(1, maxPages),
									p -> api.searchTvSeries(p, name)
								),
								l -> l.size() > 0
							),
							l -> l
						),
						maxSeries
					),
					this::dtoToTvSeries
				);


	}

	private TvSeries dtoToTvSeries(TvSeriesDto dto) {
		return new TvSeries(
			dto.getStartDate(),
			dto.getName(),
			dto.getId(),
			dto.getPopularity(),
			dto.getCharacter()
		);

			//getSeriesActors(dto.getId()),
			//getGenres(dto.getGenreIds()));
	}

	public MoviesDbService(MoviesDbWebApi api) {
		this.api = api;
	}
}
