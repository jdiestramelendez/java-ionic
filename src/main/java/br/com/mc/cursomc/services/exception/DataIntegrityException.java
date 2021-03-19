package br.com.mc.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityException() {
		super();
	}

	public DataIntegrityException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrityException(String message) {
		super(message);
	}

	public DataIntegrityException(Throwable cause) {
		super(cause);
	}

}
