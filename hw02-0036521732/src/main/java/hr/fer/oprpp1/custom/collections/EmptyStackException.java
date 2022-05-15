package hr.fer.oprpp1.custom.collections;

/**
 * Klasa predstavlja iznimku koja se baca ako korisnik nad klasom 
 * <code>ObjectStack</code> pozove metodu <code>code</code> 
 * @author LovroGlogar
 *
 */
public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public EmptyStackException() {
		
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
	
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
