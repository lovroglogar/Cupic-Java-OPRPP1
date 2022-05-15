package hr.fer.oprpp1.custom.scripting.lexer;


/**
 * Klasa predstavlja jedan token kod leksicke analize teksta s SmartScriptLexerom;
 * sadrzi tip i vrijednost
 * @author Lovro Glogar
 */
public class SmartScriptToken {
	
	/**
	 * Tip tokena
	 */
	private SmartScriptTokenType type;
	
	/**
	 * Vrijednost tokena
	 */
	private Object value;

	/**
	 * Konstruktor
	 * @param type
	 * @param value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost tokena
	 * @return Object
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Vraca tip tokena
	 * @return
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}
}
