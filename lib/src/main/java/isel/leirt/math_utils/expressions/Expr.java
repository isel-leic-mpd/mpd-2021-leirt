package isel.leirt.math_utils.expressions;

import java.io.Writer;

public interface Expr {
	double eval();
	void write(Writer writer);
}
