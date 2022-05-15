package hr.fer.oprpp1.hw04.db;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParserException() {
		
	}
	
	public ParserException(String message) {
		super(message);
	}
	
	public ParserException(Throwable cause) {
		super(cause);
	}
	
	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
