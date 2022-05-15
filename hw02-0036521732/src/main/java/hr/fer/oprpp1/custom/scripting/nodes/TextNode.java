package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Predstavlja tekst izvan taga
 * @author Lovro Glogar
 *
 */
public class TextNode extends Node {
	
	/**
	 * Vrijdnost
	 */
	private String text;
	
	/**
	 * Konstruktor
	 * @param text
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Vraca <b>text</b>
	 * @return <b>text</b>
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Usporeduje text nodeove ili text node i string
	 */
	@Override
	public boolean equals(Object obj) {
		String objString = (String) obj;
		return text.equals(objString);
	}
	
	/**
	 * Vraca string reprezentaciju text nodea
	 */
	@Override
	public String toString() {
		return this.text;
	}
}
