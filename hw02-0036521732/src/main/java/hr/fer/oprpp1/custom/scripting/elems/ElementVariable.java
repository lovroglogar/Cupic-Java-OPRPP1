package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Predstavlja izraz koji je varijabla
 * @author Lovro Glogar
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * Ime varijable
	 */
	private String name;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Vraca ime varijable
	 * @return String ime varijable
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
