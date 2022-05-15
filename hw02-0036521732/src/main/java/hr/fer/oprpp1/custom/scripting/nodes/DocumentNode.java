package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Predstavlja izorni dokument
 * @author Lovro Glogar
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Tijelo dokumenta
	 */
	private String documentBody;
	
	/**
	 * Konstruktor
	 */
	public DocumentNode() {
		super();
	}
	
	/**
	 * Konstruktor
	 * @param documentBody
	 */
	public DocumentNode(String documentBody) {
		super();
		this.documentBody = documentBody;
	}
	
	/**
	 * Postavlja <b>documentBody</b>
	 * @param documentBody
	 */
	public void setDocumentBody(String documentBody) {
		this.documentBody = documentBody;
	}
	
	/**
	 * Vraca string interpretaciju dokumenta
	 */
	@Override
	public String toString() {
		return this.documentBody;
	}
}
