package hr.fer.zemris.java.gui.layouts;

/**
 * Iznimka koja se baca prilikom rada kalkulatora
 * @author Lovro Glogar
 *
 */
public class CalcLayoutExcpetion extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CalcLayoutExcpetion() {
		
	}
	
	public CalcLayoutExcpetion(String message) {
		super(message);
	}
	
	public CalcLayoutExcpetion(Throwable cause) {
		super(cause);
	}
	
	public CalcLayoutExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

}
