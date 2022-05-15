package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Predstavlja obican tekst
 * @author Lovro Glogar
 *
 */
public class ElementString extends Element {
	
	/**
	 * Vrijednost izraza
	 */
	private String value;

	/**
	 * Konstruktor
	 * @param value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
