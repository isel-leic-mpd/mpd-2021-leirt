package isel.leirt.exceptions;

public class CircularDependencyException extends RuntimeException {
	public CircularDependencyException() {
		super("circular dependency detected in expression");
	}
}
