package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Predstavlja cijeli broj
 * @author Lovro Glogar
 *
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * Vrijednost cijelog broja
	 */
	private int value;

	/**
	 * Konstruktor
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
