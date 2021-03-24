package isel.leirt.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Number implements Expr {

	private final Double number;

	public Number(double number) {
		this.number = number;
	}

	@Override
	public double eval() {
		return number;
	}

	@Override
	public void write(Writer writer) {
		try {
			writer.write(number.toString());
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
