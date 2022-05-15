package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Predstavlja strukturu dokumenta
 * @author Lovro Glogar
 *
 */
public class Node {
	
	/**
	 * Djeca nodea
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Dodaje jedno dijete Node
	 * @param child
	 */
	public void addChildNode(Node child) {
		if(this.children == null)
			this.children = new ArrayIndexedCollection();
		children.add(child);
	}
	
	/**
	 * Vraca broj djece 
	 * @return
	 */
	public int numberOfChildren() {
		return this.children != null ? this.children.size() : 0;
	}
	
	/**
	 * Vraca dijete na indexu
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException{
		if(this.children != null)
			return (Node) this.children.get(index);
		else throw new NullPointerException();
	}
	
}
