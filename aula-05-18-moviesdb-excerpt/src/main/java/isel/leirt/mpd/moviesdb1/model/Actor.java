package isel.leirt.mpd.moviesdb1.model;

public class Actor {
	private int id;
	private String name;
	private double popularity;

	private Iterable<TvSeries> series;

	public Actor(int id, String name, double popularity, Iterable<TvSeries> series) {
		this.id = id;
		this.name = name;
		this.popularity = popularity;
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

	public double getPopularity() {
		return popularity;
	}
}
