package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Predstavlja operator
 * @author Lovro Glogar
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * Operator u obliku Stringa
	 */
	private String symbol;

	/**
	 * Konstruktor
	 * @param value
	 */
	public ElementOperator(String value) {
		this.symbol = value;
	}

	public String getValue() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
}
