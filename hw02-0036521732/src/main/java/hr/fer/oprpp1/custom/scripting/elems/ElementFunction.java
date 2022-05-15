package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Prestavlja funkciju
 * @author Lovro Glogar
 *
 */
public class ElementFunction extends Element {
	
	/**
	 * Ime funkcije
	 */
	private String name;

	/**
	 * Konstruktor
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	public String getValue() {
		return name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}
}
