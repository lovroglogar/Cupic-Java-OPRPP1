package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Predstavlja echo tag
 * @author Lovro Glogar
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Elementi echo nodea
	 */
	private Element[] elements;

	/**
	 * Konstruktor
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Vraca <b>elements</b>
	 * @return
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Vraca string reprezentaciju echo nodea
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int i = 0; i < elements.length; i++) 
			sb.append(elements[i].asText() + " ");
		return sb.toString();
	}
}
