package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Predstavlja decimalni broj
 * @author Lovro Glogar
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Vrijednost broja
	 */
	private double value;

	/**
	 * Konstruktor
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}
}
