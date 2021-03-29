package isel.leirt.aula_03_23.exceptions;

public class CircularDependencyException extends RuntimeException {
	public CircularDependencyException() {
		super("circular dependency detected in expression");
	}
}
