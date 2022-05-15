package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja tablicu raspršenog 
 * adresiranja koja omogućava pohranu uređenih parova (ključ, vrijednost).
 * @author Lovro Glogar
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * Pomocna struktura koja sluzi za povezanu listu parova (ključ, vrijednost) koji imaju
	 * isti {@code hashCode} kljuca.
	 * @author Lovro Glogar
	 *
	 * @param <K> tip kljuca
	 * @param <V> tip vrijednosti
	 */
	public static class TableEntry<K, V>{
		
		/**
		 * Predstavlja kljuc
		 */
		private K key;
		
		/**
		 * Predstavlja vrijednost
		 */
		private V value;
		
		/**
		 * Pokazivac na sljedeci par (ključ, vrijednost) u listi (ista hash vrijednost kljuca) 
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Konstruktor
		 * @param key
		 * @param value
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter vrijednosti
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Postavlja vrijednost <b>value</b>
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter kljuca
		 * @return kljuc
		 */
		public K getKey() {
			return key;
		}
	}
	
	/**
	 * Tablica rasprsenog adresiranja
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Broj elemenata u tablici
	 */
	private int size;
	
	/**
	 * Popunjenost tablice definirana kao size/table.length
	 */
	private float popunjenost;
	
	/**
	 * Prati broj izmjena tablice
	 */
	private int modificationCount;
	
	/**
	 * Velicina tablice ako je pozvan konstruktor bez argumenata
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Konstruktor
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table =  (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
		size = 0;
		popunjenost = 0;
		modificationCount = 0;
	}
	
	/**
	 * Konstruktor; zadaje velicinu table na prvu potenciju broja 2 koja je veca od <b>initialCapacityLow</b>
	 * @param initialCapacityLow
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacityLow) {
		if(initialCapacityLow < 1)
			throw new IllegalArgumentException();
		int initialCapacity = initialCapacityLow;
		int currentPower = 0;
		int potencijaOd2;
		
		while(true) {
			potencijaOd2 = (int) Math.pow(2, currentPower);
			if(initialCapacity <= potencijaOd2) {
				break;
			}
			currentPower++;
		}
		table = (TableEntry<K, V>[]) new TableEntry[potencijaOd2];
		size = 0;
		popunjenost = 0;
		modificationCount = 0;
	}
	
	/**
	 * Vraca size
	 * @return size
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Stvara par (<b>key</b>, <b>value</b>) u tablici ako ne postoji kljuc;
	 * U slucaju da kljuc vec postoji u tablici onda se stara vrijednost zamjenjuje novom.
	 * @param key
	 * @param value
	 * @return null ako kljuc nije prije postojao u tablici, inace vraca prebrisanu vrijednost
	 * @throws NullPointerException u slucaju da je <b>key</b> == null
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException();
		
		TableEntry<K, V>[] array;
		if(this.popunjenost >= 0.75 && !this.containsKey(key)) { // provjera popunjenosti; ako postoji vec kljuc u hash tablici, onda ne treba prosiriti table
			array = this.toArray();
			this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
			this.popunjenost = 0;
			this.size = 0;
			for(int i = 0; i < array.length; i++)
				this.put(array[i].getKey(), array[i].getValue());
		}
		
		int position = Math.abs(key.hashCode()) % table.length;
		
		if(table[position] != null) {
			TableEntry<K, V> entry = table[position];
			while(entry != null) {
				if(entry.getKey().equals(key)) { // overrideanje
					V oldValue = entry.getValue();
					entry.setValue(value);
					return oldValue;
				}
				if(entry.next == null) {
					entry.next = new TableEntry<>(key, value);
					this.size++;
					this.popunjenost = (float) this.size/this.table.length;
					modificationCount++;
					return null;
				}
				entry = entry.next;
			}
		} else {
			table[position] = new TableEntry<>(key, value);
			this.size++;
			this.popunjenost = (float) this.size/this.table.length;
			modificationCount++;
			return null;
		}
		return null;
	}
	
	/**
	 * Vraca vrijednost pridruzenu kljucu <b>key</b>, ako kljuc ne postoji vraca null
	 * @param key
	 * @return null ako kljuc ne postoji, inace vrijednost pridruzenu kljucu
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		
		int position = Math.abs(key.hashCode()) % table.length;
		
		if(table[position] == null)
			return null;
		else {
			TableEntry<K, V> entry = table[position];
			while(entry != null) {
				if(entry.getKey().equals(key))
					return entry.getValue();
				entry = entry.next;
			}
		}
		
		return null;
	}
	
	/**
	 * Vraca true ako postoji kljuc <b>key</b> u tablici
	 * @param key
	 * @return true ako kljuc postoji u tablici
	 * @throws NullPointerException u slucaju da je <b>key</b> == null
	 */
	public boolean containsKey(Object key) {
		if(key == null)
			throw new NullPointerException();
		int position = Math.abs(key.hashCode()) % table.length;
		
		if(table[position] == null) {
			return false;
		}else {
			TableEntry<K, V> entry = table[position];
			while(entry != null) {
				if(entry.getKey().equals(key))
					return true;
				entry = entry.next;
			}
		}
		return false;
	}

	/**
	 * Vraca true ako postoji vrijednost <b>value</b> u tablici
	 * @param value
	 * @return true ako postoji vrijednost u tablici
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> entry;
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				entry = table[i];
				while(entry != null) {
					if(entry.getValue().equals(value))
						return true;
					entry = entry.next;
				}
			}
		}
		return false;
	}
	
	/**
	 * Brise TableEntry s kljucem <b>key</b> iz tablice ako on postoji
	 * @param key
	 * @return vrijednost izbrisanog TableEntry-a, null ako je <b>key</b> == null
	 */
	public V remove(Object key) {
		if(key == null)
			return null;
		int position = Math.abs(key.hashCode()) % table.length;
		
		if(table[position] == null)
			return null;
		else {
			TableEntry<K, V> current = table[position];
			TableEntry<K, V> previous = null;
			while(current != null) {
				if(current.getKey().equals(key)) {
					if(previous == null) 
						table[position] = current.next;
					else 
						previous.next = current.next;
					this.size--;
					modificationCount++;
					return current.getValue();
				}
				previous = current;
				current = current.next;
			}
		}
		return null;
	}

	/**
	 * 	Vraca true ako parova (ključ, vrijednost) u tablici
	 * @return true ako je tablic prazna
	 */
	public boolean isEmpty() {
		return this.size == 0 ? true : false;
	}
	
	/**
	 * Vraca tablicu u obliku liste
	 * @return listu elemenata tablice
	 */
	public TableEntry<K, V>[] toArray(){
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] returnArray = (TableEntry<K, V>[]) new TableEntry[this.size];
		int indexOfReturnArray = 0;
		
		TableEntry<K, V> entry;
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				entry = table[i];
				while(entry != null) {
					returnArray[indexOfReturnArray++] = entry;
					entry = entry.next;
				}
			}
		}
		
		return returnArray;
	}
	
	/**
	 * Vraca String reprezentaciju tablice
	 */
	@Override
	public String toString() {
		TableEntry<K, V>[] array = this.toArray();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < array.length; i++) {
			if(i == array.length - 1)
				sb.append(array[i].getKey() + "=" + array[i].getValue());
			else sb.append(array[i].getKey() + "=" + array[i].getValue() + ", ");
		}
		sb.append("]");
		
		return sb.toString();
	}

	/**
	 * Prazni tablicu
	 */
	public void clear() {
		for(int i = 0; i < this.table.length; i++) {
			table[i] = null;
		}
		this.size = 0;
		this.popunjenost = 0;
		modificationCount++;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Klasa predstavlja implementaciju iteratora nad SimpleHashtable
	 * @author Lovro Glogar
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>>{

		/**
		 * Spremljena vrijednost modificationCount
		 */
		private int savedModificationCount;
		
		/**
		 * Zadnji vraceni element tablice iteratorom
		 */
		private K currentKey = null;
		
		/**
		 * Prati ako je vec pomocu iteratora uklonjen trenutni element
		 */
		private boolean removed;
		
		/**
		 * Konstruktor
		 */
		public IteratorImpl() {
			savedModificationCount = modificationCount;
			removed = false;
		}
		
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			
			
			int currentPosition;
			TableEntry<K, V> entry;

			if(currentKey == null) { // nalazim se na pocetku (currentKey je null)
				for(int i = 0; i < table.length; i++) {
					if(table[i] != null) {
						return true;
					}
				}
				return false;
			}

			currentPosition = Math.abs(currentKey.hashCode()) % table.length;
			entry = table[currentPosition];

			while(entry != null) {
				if(entry.getKey().equals(currentKey)) {
					if(entry.next != null) 
						return true;
					else {
						for(int i = currentPosition + 1; i < table.length; i++) {
							if(table[i] != null) 
								return true;
						}
						return false;
					}
				}
				entry = entry.next;
			}			
			
			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			if(savedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			
			removed = false;
			int currentPosition;
			TableEntry<K, V> entry;

			if(currentKey == null) { // nalazim se na pocetku (currentKey je null)
				for(int i = 0; i < table.length; i++) {
					if(table[i] != null) {
						currentKey = table[i].getKey();
						return table[i];
					}
				}
				throw new NoSuchElementException();
			}

			currentPosition = Math.abs(currentKey.hashCode()) % table.length;
			entry = table[currentPosition];

			while(entry != null) {
				if(entry.getKey().equals(currentKey)) {
					if(entry.next != null) {
						currentKey = entry.next.getKey();
						return entry.next;
					} else {
						for(int i = currentPosition + 1; i < table.length; i++) {
							if(table[i] != null) {
								currentKey = table[i].getKey();
								return table[i];
							}
						}
						throw new NoSuchElementException();
					}
				}
				entry = entry.next;
			}			
			
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {
			if(removed || savedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			if(currentKey == null)
				throw new NullPointerException();
			
			TableEntry<K, V> entry, previous = null;
			
			for(int i = 0; i < table.length; i++) { // idem od pocetka jer moram pamtiti previous od currentKey
				entry = table[i];
				while(entry != null) {
					if(entry.getKey().equals(currentKey)) {
						if(previous == null) {
							currentKey = null;
						} else
							currentKey = previous.getKey();
						SimpleHashtable.this.remove(entry.getKey());
						savedModificationCount++;
						removed = true;
						return;
					}
					previous = entry;
					entry = entry.next;
				}
			}
		}
	}
}












