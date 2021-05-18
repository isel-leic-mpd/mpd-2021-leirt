package isel.leirt.mpd.moviesdb1.model;

public class Genre {
	private int id;
	private String name;

	private Iterable<TvSeries> series;

	public Genre(int id, String name, Iterable<TvSeries> series) {
		this.id = id;
		this.name =  name;
		this.series = series;
	}

	public Iterable<TvSeries> getSeries() {
		return series;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
