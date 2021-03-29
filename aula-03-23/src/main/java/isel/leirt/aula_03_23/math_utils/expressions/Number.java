package isel.leirt.aula_03_23.math_utils.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Number implements Expr {
	public int x;
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
