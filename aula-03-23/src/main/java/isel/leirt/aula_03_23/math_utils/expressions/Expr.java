package isel.leirt.aula_03_23.math_utils.expressions;

import java.io.Writer;


public interface Expr {
	double eval();
	void write(Writer writer);
}
