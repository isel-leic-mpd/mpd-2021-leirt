package isel.leirt.mpd.optionals;


import java.util.Optional;

public class Person {
	private  Optional<Car> car;

	public Person(Car car) {
		this.car = Optional.of(car);
	}

	public Person( ) {
		this.car = Optional.empty();
	}

	public Optional<Car> getCar() {
		return car;
	}
}
