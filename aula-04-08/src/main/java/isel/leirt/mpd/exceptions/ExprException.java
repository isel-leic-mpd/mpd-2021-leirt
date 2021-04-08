package isel.leirt.mpd.exceptions;

public class ExprException extends RuntimeException {
	public ExprException(String msg) {
		this(msg, null);
	}

	public ExprException(String msg, Exception inner) {
		super(msg, inner);
	}
}