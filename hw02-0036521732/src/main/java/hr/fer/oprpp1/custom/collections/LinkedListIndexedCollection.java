package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa nasljeduje klasu <b>Collection</b> te definira metode
 * <code>get(int), insert(Object, int), indexOf(Object), remove(int)</code>.
 * Implementira kolekciju koristeci vezanu listu.
 * @author Lovro Glogar
 * @see hr.fer.oprpp1.custom.collections.Collection
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * Staticka ugnjezdena klasa koja sluzi kao pomocna struktura za cvor vezane liste
	 * @author Lovro Glogar
	 */
	private static class ListNode {
		/**
		 * Sadrzi referencu na cvor liste koji prethodi trenutnom cvoru
		 */
		ListNode previous = null;
		
		/**
		 * Sadrzi referencu na cvor liste koji slijedi trenutnom cvoru
		 */
		ListNode next = null;
		
		/**
		 * Vrijednost cvora vezane liste
		 */
		Object value;
		
		/**
		 * Konstruktor klase
		 * @param value postaje vrijednost cvora vezane liste
		 */
		ListNode(Object value){
			this.value = value;
		}
	}
	
	/**
	 * Privatna clanska varijabla koja predstavlja broj elemenata kolekcije
	 */
	private int size;
	
	/**
	 * Referenca na prvi element vezane liste
	 */
	private ListNode first;
	
	/**
	 * referenca na zadnji element vezane liste
	 */
	private ListNode last;
	
	/**
	 * Privatna clanska varijabla <b>modificationCount</b> sluzi za pracenje koliko puta
	 * je kolekcija modificirana.
	 */
	private long modificationCount;
	
	/**
	 * Konstruktor klase
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Konstruktor klase koji stvara novu vezanu listu koristeci elemente predane kolekcije <b>collection</b>
	 * @param collection kolekcija ciji se elementi koriste za punjenje nove vezane liste
	 * @throws NullPointerException u slucaju da je <b>collection</b> == <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection collection) {
		if(collection == null)
			throw new NullPointerException();
		this.addAll(collection);
		this.modificationCount = 0;
	}
	
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * {@inheritDoc}
	 * @throws NullPointerException u slucaju da je <b>value</b> == null
	 */
	@Override
	public void add(Object value) {
		if(value == null)
			throw new NullPointerException();
		
		ListNode newNode = new ListNode(value);
		if(first == null) {
			first = newNode;
			last = first;
			this.size++;
			this.modificationCount++;
			return;
		}
		last.next = newNode;
		newNode.previous = last;
		last = newNode;
		this.size++;
		this.modificationCount++;
	}

	@Override
	public boolean contains(Object value) {
		ListNode currentNode = first;
		for(;currentNode != null; currentNode = currentNode.next) {
			if(currentNode.value.equals(value))
				return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object value) {
		ListNode currentNode = first;
		while(currentNode != null && !currentNode.value.equals(value)) {
			currentNode = currentNode.next;
		}
		if(currentNode.value.equals(value)) {
			currentNode.previous.next = currentNode.next;
			this.size--;
			this.modificationCount++;
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		if(this.isEmpty())
			throw new UnsupportedOperationException();
		Object[] returnArray = new Object[this.size];
		ListNode currentNode = first;
		int indexOfArray = 0;
		for(;currentNode != null; currentNode = currentNode.next) {
			returnArray[indexOfArray] = currentNode.value;
			indexOfArray++;
		}
		return returnArray;
	}

	@Override
	public void clear() {
		ListNode currentNode = first;
		ListNode nextNode;
		while(currentNode != null) {
			nextNode = currentNode.next;
			currentNode.previous = null;
			currentNode.next = null;
			currentNode = nextNode;
		}
		first = null;
		last = null;
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * @throws IndexOutOfBoundsException u slucaju ilegalne vrijednosti <b>index</b>
	 */
	@Override
	public Object get(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		ListNode currentNode;
		if(this.size / 2 <= index) {
			currentNode = last;
			for(int i = this.size - 1; i > index; i--)
				currentNode = currentNode.previous;
			return currentNode.value;
		}else {
			currentNode = first;
			for(int i = 0; i < index; i++)
				currentNode = currentNode.next;
			return currentNode.value;
		}
	}
	
	/**
	 * @throws NullPointerException u slucaju da je <b>value</b> == <code>null</code>
	 * @throws IndexOutOfBoundsException u slucaju ilegalne pozicije
	 */
	@Override
	public void insert(Object value, int position) {
		if(value == null)
			throw new NullPointerException();
		if(position < 0 || position > size)
			throw new IndexOutOfBoundsException();
		if(position == this.size) {
			this.add(value);
			this.modificationCount++;
			return;
		}
		ListNode currentNode;
		ListNode newNode = new ListNode(value);
		if(this.size / 2 <= position) {
			currentNode = last;
			for(int i = this.size - 1; i > position; i--)
				currentNode = currentNode.previous;
		}else {
			currentNode = first;
			for(int i = 0; i < position; i++)
				currentNode = currentNode.next;
		}
		currentNode.previous.next = newNode;
		newNode.next = currentNode;
		newNode.previous = currentNode.previous;
		currentNode.previous = newNode;
		this.size++;
		this.modificationCount++;
	}
	
	@Override
	public int indexOf(Object value) {
		ListNode currentNode = first;
		int index = 0;
		for(; currentNode != null; currentNode = currentNode.next) {
			if(currentNode.value.equals(value))
				return index;
			index++;
		}
		return -1;
	}
	
	/**
	 * @throws IndexOutOfBoundsException u slucaju ilegalne vrijednosti <b>index</b>
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		ListNode currentNode;
		if(this.size / 2 <= index) {
			currentNode = last;
			for(int i = this.size - 1; i > index; i--)
				currentNode = currentNode.previous;
		}else {
			currentNode = first;
			for(int i = 0; i < index; i++)
				currentNode = currentNode.next;
		}
		currentNode.previous.next = currentNode.next;
		currentNode.next.previous = currentNode.previous;
		this.size--;
		this.modificationCount++;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
	
	/**
	 *Klasa implementira sucelje <b>ElementsGetter</b> te sluzi za iteriranje po kolekciji. 
	 * @author Lovro Glogar
	 */
	private static class LinkedListElementsGetter implements ElementsGetter{

		/**
		 * Referenca na sljedeci element kolekcije
		 */
		private ListNode nextElement;
		
		/**
		 * Instanca kolekcije nad kojom ElementsGetter iterira.
		 */
		private LinkedListIndexedCollection collection;
		
		/**
		 * Poprima vrijednost od modificationCount od kolekcije nad kojom se stvara u trenturku instanciranja
		 */
		private long savedModificationCount;
		
		/**
		 * Konstruktor
		 * @param collection
		 */
		public LinkedListElementsGetter(LinkedListIndexedCollection collection) {
			this.collection = collection;
			nextElement = this.collection.first;
			this.savedModificationCount = this.collection.modificationCount;
		}
		
		/**
		 * @throws ConcurrentModificationException u slucaju da je pripadna kolekcija
		 * mijenjana nakon inicijalizacije ElementsGetter-a.
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != this.collection.modificationCount)
				throw new ConcurrentModificationException();
			if(nextElement != null)
				return true;
			return false;
		}

		/**
		 * @throws NoSuchElementException u slucaju da je ElementsGetter dosao do kraja kolekcije
		 * @throws ConcurrentModificationException u slucaju da je pripadna kolekcija
		 * mijenjana nakon inicijalizacije ElementsGetter-a.
		 */
		@Override
		public Object getNextElement() {
			if(savedModificationCount != this.collection.modificationCount)
				throw new ConcurrentModificationException();
			ListNode temp = nextElement;
			nextElement = nextElement.next;
			return temp.value;
		}
		
	}
}










