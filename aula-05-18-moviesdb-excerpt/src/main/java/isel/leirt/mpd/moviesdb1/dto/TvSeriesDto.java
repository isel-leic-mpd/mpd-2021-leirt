package isel.leirt.mpd.moviesdb1.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Optional;


public class TvSeriesDto {
	private String first_air_date;
	private  int[] genre_ids;
	private  int id;
	private  String name;
	private  double popularity;
	private String character;

	public LocalDate getStartDate() {
		try {
			return first_air_date == null  || first_air_date.length()==0? LocalDate.now() : LocalDate.parse(first_air_date);
		}
		catch(Exception e) {
			return LocalDate.now();
		}
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

	public int[] getGenreIds() {
		return genre_ids;
	}

	public String toString() {
		return "{ "
			+ "series name=" + getName()
			+ ", start_date=" + getStartDate()
			+ ", series_id=" + id
			+ ", popularity=" + popularity
			+ getCharacter().map(c -> ", character=" +c).orElse("")
			+ " }";
	}

	public Optional<String> getCharacter() {
		return (character == null || character.length() ==0) ? Optional.empty() : Optional.of(character);
	}
}
