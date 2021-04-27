package isel.leirt.mpd.weather3.dto;

public class Weather {
	public final double temp;
	public final double feels_like;
	public final double temp_min;
	public final double temp_max;
	public final int humidity;

	public Weather(double temp, double feels_like, double temp_min, double temp_max, int humidity) {
		this.temp = temp;
		this.feels_like = feels_like;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return  "{ "
				+"temp = " + temp
				+ ", feels_like = " + feels_like
				+ ", temp_min = " + temp_min
				+ ". temp_max = " + temp_max
				+ ", humidity = " + humidity
				+ " }";
	}



}
