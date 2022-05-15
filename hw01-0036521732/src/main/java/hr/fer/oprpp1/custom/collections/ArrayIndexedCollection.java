package hr.fer.oprpp1.custom.collections;

/**
 * Klasa nasljeduje klasu <b>Collection</b> te definira metode
 * <code>get(int), insert(Object, int), indexOf(Object), remove(int)</code>.
 * Implementira kolekciju koristeci <code>Object[]</code>
 * @author Lovro Glogar
 * @see hr.fer.oprpp1.custom.collections.Collection
 */
public class ArrayIndexedCollection extends Collection{
	
	/**
	 * Privatna clanska varijabla <b>size</b> prati broj elemenata u kolekciji
	 */
	private int size;
	/**
	 *  Privatna clanska varijabla <b>elements</b> je referenca na listu objekata koja
	 *  sadrzi elemente kolekcije
	 */
	private Object[] elements;
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
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException();
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Konstruktor klase koji stvara novu kolekciju koristeci elemente predane kolekcije <b>collection</b>
	 * @param collection kolekcija ciji se elementi dodaju u pozivajucu kolekciju
	 */
	public ArrayIndexedCollection(Collection collection) {
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
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(collection == null) {
			throw new NullPointerException();
		} else if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		} else {
			if(initialCapacity < collection.size())
				initialCapacity = collection.size();
			this.elements = new Object[initialCapacity];
			this.addAll(collection);
		}
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public void add(Object value) {
		if(value == null)
			throw new NullPointerException();
		int i;
		for( i = 0; i < this.size() || this.size == 0; i++) {
			if(this.elements[i] == null) {
				this.elements[i] = value;
				this.size++;
				return;
			}
		}
		if(i == this.size()) {
			Object[] helperArray = elements;
			elements = new Object[this.size * 2];
			java.lang.System.arraycopy(helperArray, 0, elements, 0, this.size);
			elements[this.size] = value;
			this.size++;
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
	public void forEach(Processor processor) {
		for(int i = 0; i < this.size; i++) 
			processor.process(elements[i]);
	}

	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++)
			this.elements[i] = null;
		this.size = 0;
	}
	
	/**
	 * Vraca objekt koji se nalazi na poziciji <b>index</b>.
	 * @param index pozicija trazenog objekta, legalne vrijednosti [0, velicinaKolekcije - 1]
	 * @return objekt na poziciji <b>index</b>
	 * @throws IndexOutOfBoundsException u slucaju ilegalne vrijednosti <b>index</b>
	 */
	public Object get(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * Umece objekt <b>value</b> na poziciju <b>position</b>.
	 * @param value objekt koji se umece
	 * @param position pozicija na koju se umece, legalne pozicije su [0, velicinaKolekcije]
	 * @throws NullPointerException u slucaju da je <b>value</b> == <code>null</code>
	 * @throws IndexOutOfBoundsException u slucaju ilegalne pozicije
	 */
	public void insert(Object value, int position) {
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
			elements = new Object[this.size * 2];
			java.lang.System.arraycopy(helperArray, 0, elements, 0, this.size);
		}
		
		for(int i = this.size - 1; i >= position; i--)
			elements[i+1] = elements[i];
		elements[position] = value;
		this.size++;
	}
	
	/**
	 * Vraca poziciju/indeks predanog objekta <b>value</b>.
	 * @param value trazeni objekt
	 * @return indeks predanog objekta; -1 u slucaju da objekt ne pripada kolekciji
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value))
				return i;
		}
		return -1;
	}
	
	/**
	 * Uklanja element na poziciji <b>index</b> iz kolekcije.
	 * @param index pozicija elementa koji se treba ukolini, legalne vrijednosti su [0, velicinaKolekcije - 1]
	 * @throws IndexOutOfBoundsException u slucaju ilegalne vrijednosti <b>index</b>
	 */
	public void remove(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		for(int i = index; i < this.size - 1; i++) 
			this.elements[i] = this.elements[i+1];
		this.elements[this.size - 1] = null;
		this.size--;
	}
}












































