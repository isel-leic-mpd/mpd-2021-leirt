package isel.leirt.mpd.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public abstract class UnaryOper implements Expr {
	protected Expr exp;

	public UnaryOper(Expr exp) {
		this.exp = exp;
	}

	protected abstract String getOperator();

	@Override
	public void write(Writer writer) {

		try {
			exp.write(writer);
			writer.write( getOperator());

		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}

	}

}
