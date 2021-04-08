package isel.leirt.mpd.expressions;

import java.io.Writer;

public interface Expr {
	double eval();
	void write(Writer writer);

}
