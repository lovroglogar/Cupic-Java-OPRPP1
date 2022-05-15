package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa nasljeduje sucelje <b>List</b>
 * @author Lovro Glogar
 * @see hr.fer.oprpp1.custom.collections.Collection
 */
public class ArrayIndexedCollection<T> implements List<T>{
	
	/**
	 * Privatna clanska varijabla <b>size</b> prati broj elemenata u kolekciji
	 */
	private int size;
	
	/**
	 *  Privatna clanska varijabla <b>elements</b> je referenca na listu objekata koja
	 *  sadrzi elemente kolekcije
	 */
	private T[] elements;
	
	/**
	 * Privatna clanska varijabla <b>modificationCount</b> sluzi za pracenje koliko puta
	 * je kolekcija modificirana.
	 */
	private long modificationCount;
	
	/**
	 * Privatna konstantna varijabla <b>DEFAULT_CAPACITY</b> oznacava
	 * velicinu alocirane liste elemenata ako u konstruktoru nije specificirana ta vrijednost
	 */
	private final static int DEFAULT_CAPACITY = 16;
	
	/**
	 * Konstruktor klase
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Konstruktor klase koji alocira listu elemenata kolekcije velicine <b>inistialCapacity</b>
	 * @param initialCapacity velicina alocirane liste elemenata; pozitivan cijeli broj
	 * @throws IllegalArgumentException u slucaju ilegalne vrijednosti <b>initialCapacity</b>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException();
		this.size = 0;
		this.modificationCount = 0;
		this.elements = (T[]) new Object[initialCapacity];
	}
	
	/**
	 * Konstruktor klase koji stvara novu kolekciju koristeci elemente predane kolekcije <b>collection</b>
	 * @param collection kolekcija ciji se elementi dodaju u pozivajucu kolekciju
	 */
	public ArrayIndexedCollection(Collection<? extends T> collection) {
		this(collection, 1);
	}
	
	/**
	 * Konstruktor klase koji stvara novu kolekciju koristeci elemente predane kolekcije <b>collection</b>
	 * te alocira listu elemenata velicine <b>initalCapcity</b>, 
	 * u slucaju da je vrijednost <b>initialCapacity</b> manja od velicine kolekcije <b>collection</b>
	 * alocira se lista elemenata velicine jednake velicini kolekcije <b>collection</b>
	 * @param collection kolekcija ciji se elementi dodaju u pozivajucu kolekciju
	 * @param initialCapacity velicina alocirane liste elemenata; pozitivan cijeli broj
	 * @throws NullPointerException u slucaju da je <b>collection</b> == <code>null</code>
	 * @throws IllegalArgumentException u slucaju ilegalne vrijednosti <b>initialCapacity</b>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
		if(collection == null) {
			throw new NullPointerException();
		} else if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		} else {
			if(initialCapacity < collection.size())
				initialCapacity = collection.size();
			this.elements = (T[]) new Object[initialCapacity];
			this.addAll(collection);
			this.modificationCount = 0;
		}
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException u slucaju da je <b>value</b> == null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(T value) {
		if(value == null)
			throw new NullPointerException();
		int i;
		for( i = 0; i < this.size() || this.size == 0; i++) {
			if(this.elements[i] == null) {
				this.elements[i] = value;
				this.size++;
				this.modificationCount++;
				return;
			}
		}
		if(i == this.size()) {
			Object[] helperArray = elements;
			elements = (T[]) new Object[this.size * 2];
			System.arraycopy(helperArray, 0, elements, 0, this.size);
			elements[this.size] = value;
			this.size++;
			this.modificationCount++;
		}
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value)) {
				for(int j = i; j < this.size() - 1; j++) {
					elements[j] = elements[j + 1];
				}
				this.size--;
				this.modificationCount++;
				return true;
			}
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
		for(int i = 0; i < this.size; i++)
			returnArray[i] = this.elements[i];
		return returnArray;
	}

	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++)
			this.elements[i] = null;
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * @throws IndexOutOfBoundsException u slucaju ilegalne vrijednosti <b>index</b>
	 */
	@Override
	public T get(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * @throws NullPointerException u slucaju da je <b>value</b> == <code>null</code>
	 * @throws IndexOutOfBoundsException u slucaju ilegalne pozicije
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(T value, int position) {
		if(value == null)
			throw new NullPointerException();
		if(position < 0 || position > this.size)
			throw new IndexOutOfBoundsException();
		if(position == this.size) {
			this.add(value);
			return;
		}
		//Ako je lista puna, alociraj jos prostora
		if(this.size == elements.length) {
			Object[] helperArray = elements;
			elements = (T[]) new Object[this.size * 2];
			System.arraycopy(helperArray, 0, elements, 0, this.size);
		}
		
		for(int i = this.size - 1; i >= position; i--)
			elements[i+1] = elements[i];
		elements[position] = value;
		this.size++;
		this.modificationCount++;
	}
	
	@Override
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value))
				return i;
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
		for(int i = index; i < this.size - 1; i++) 
			this.elements[i] = this.elements[i+1];
		this.elements[this.size - 1] = null;
		this.size--;
		this.modificationCount++;
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIndexedElementsGetter<T>(this);
	}

	/**
	 *Klasa implementira sucelje <b>ElementsGetter</b> te sluzi za iteriranje po kolekciji. 
	 * @author Lovro Glogar
	 */
	private static class ArrayIndexedElementsGetter<E> implements ElementsGetter<E>{
		/**
		 * Predstavlja poziciju u listi na kojoj se ElementsGetter nalazi.
		 */
		private int nextElementIndex;
		
		/**
		 * Instanca kolekcije nad kojom ElementsGetter iterira.
		 */
		private ArrayIndexedCollection<E> collection;
		
		/**
		 * Poprima vrijednost od modificationCount od kolekcije nad kojom se stvara u trenturku instanciranja
		 */
		private long savedModificationCount;
		
		/**
		 * Konstruktor
		 * @param collection
		 */
		public ArrayIndexedElementsGetter(ArrayIndexedCollection<E> collection) {
			this.collection = collection;
			nextElementIndex = 0;
			savedModificationCount = this.collection.modificationCount;
		}
		
		/**
		 * @throws ConcurrentModificationException u slucaju da je pripadna kolekcija
		 * mijenjana nakon inicijalizacije ElementsGetter-a.
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != this.collection.modificationCount)
				throw new ConcurrentModificationException();
			if(collection.size == 0 || nextElementIndex == collection.size)
				return false;
			if(collection.elements[nextElementIndex] == null)
				return false;
			return true;
		}

		/**
		 * @throws NoSuchElementException u slucaju da je ElementsGetter dosao do kraja kolekcije
		 * @throws ConcurrentModificationException u slucaju da je pripadna kolekcija
		 * mijenjana nakon inicijalizacije ElementsGetter-a.
		 */
		@Override
		public E getNextElement() {
			if(savedModificationCount != this.collection.modificationCount)
				throw new ConcurrentModificationException();
			if(collection.elements[nextElementIndex] == null)
				throw new NoSuchElementException();
			nextElementIndex++;
			return collection.elements[nextElementIndex - 1];
		
		}
	}
}












































