package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa predstavlja jedan token kod leksicke analize teksta s Lexerom;
 * sadrzi tip i vrijednost
 * @author Lovro Glogar
 */
public class Token {
	
	/**
	 * Tip tokena
	 */
	private TokenType type;
	
	/**
	 * Vrijednost tokena
	 */
	private Object value;

	/**
	 * Konstruktor
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost tokena
	 * @return
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Vraca tip tokena
	 * @return
	 */
	public TokenType getType() {
		return this.type;
	}
}

