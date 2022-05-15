package hr.fer.oprpp1.custom.collections;

/**
 * Klasa predstavlja stog koristeci klasu <code>ArrayIndexedCollection</code>.
 * Implementira metode stoga <code>isEmpty, size, push(Object), pop(Object), peek(Object), clear</code>
 * @author Lovro Glogar
 * @see hr.fer.oprpp1.custom.collections.ArrayIndexedCollection
 */
public class ObjectStack {
	
	/**
	 * Privatna clanska varijabla <b>stack</b> predstavlja stog pomocu klase <code>ArrayIndexedCollection</code>. 
	 */
	private ArrayIndexedCollection stack;
	
	/**
	 * Konstruktor klase.
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Vraca vrijednost <code>True</code> ako je kolekcija prazna.
	 * @return <code>True</code>
	 */
	public boolean isEmpty() {
		return this.stack.isEmpty();
	}
	
	/**
	 * Vraca broj elemenata u kolekciji.
	 * @return broj elemenata u kolekciji
	 */
	public int size() {
		return this.stack.size();
	}
	
	/**
	 * Stavlja objekt <b>value</b> na vrh stoga.
	 * @param value element koji se stavlja na vrh stoga
	 */
	public void push(Object value) {
		this.stack.add(value);
	}
	
	/**
	 * Skida element s vrha stoga te ga vraca.
	 * @return element skinut s vrha stoga
	 * @throws EmptyStackException ako je stog vec prazan
	 */
	public Object pop() {
		if(this.size() == 0)
			throw new EmptyStackException("Stog je prazan");
		int indexOfLastElement = stack.size() - 1;
		Object popedObject = stack.get(indexOfLastElement);
		this.stack.remove(indexOfLastElement);
		return popedObject;
	}
	
	/**
	 * Vraca element koji se nalazi na vrhu stoga, a da ga pritom ne skida.
	 * @return element sa vrha stoga
	 * @throws EmptyStackException ako je stog prazan
	 */
	public Object peek() {
		if(this.size() == 0)
			throw new EmptyStackException("Stog je prazan");
		return this.stack.get(stack.size() - 1);
	}
	
	/**
	 * Skida sve elemente sa stoga.
	 */
	public void clear() {
		stack.clear();
	}
}
