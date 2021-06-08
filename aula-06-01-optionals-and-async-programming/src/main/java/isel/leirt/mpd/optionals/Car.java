package isel.leirt.mpd.optionals;

import java.util.Optional;
import java.util.function.IntUnaryOperator;

public class Car {
	private Optional<Insurance> insurance;

	public Car(Insurance insurance) {

		this.insurance = Optional.of(insurance);
	}

	public Car() {
		insurance = Optional.empty();
	}

	public Optional<Insurance> getInsurance() {

		return insurance;
	}
}