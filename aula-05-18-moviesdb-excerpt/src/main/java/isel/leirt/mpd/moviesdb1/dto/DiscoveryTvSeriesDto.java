package isel.leirt.mpd.moviesdb1.dto;

import java.util.List;

public class DiscoveryTvSeriesDto {
	private int page;
	private List<TvSeriesDto> results;
	private int total_pages;
	private int total_results;

	public List<TvSeriesDto> getResults() {
		return results;
	}
}
