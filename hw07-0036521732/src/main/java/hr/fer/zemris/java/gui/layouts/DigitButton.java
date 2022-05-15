package hr.fer.zemris.java.gui.layouts;

/**
 * Klasa nasljeduje {@link MyButton}.
 * Predstavlja gumb koji unosi znamenke u kalkulator
 * @author Lovro Glogar
 *
 */
public class DigitButton extends MyButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Znamenka
	 */
	private int digit;
	
	/**
	 * Konstruktor
	 * @param digit znamenka koju gumb unosi
	 */
	public DigitButton(int digit) {
		super(String.valueOf(digit));
		this.digit = digit;
		this.setFont(this.getFont().deriveFont(30f));
	}

	public int getDigit() {
		return digit;
	}
	
}
