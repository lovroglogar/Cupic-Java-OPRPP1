package hr.fer.oprpp1.custom.collections;

/**
 * Klasa modelira jednostavnan riječnik koristeci {@code ArrayIndexCollection} 
 * @author Lovro Glogar
 *
 * @param <K> tip kljuca
 * @param <V> tip vrijednosti
 */
public class Dictionary<K, V> {
	
	/**
	 * Staticku klasu {@code Pair<K, V>} koja sluzi kao pomocna struktura za mapiranje kljuca i vrijednosti
	 * @author Lovro Glogar
	 *
	 * @param <K>
	 * @param <V>
	 */
	private static class Pair<K, V>{
		/**
		 * Predstavlja kljuc
		 */
		private K key;
		
		/**
		 * Predstavlja vrijednost
		 */
		private V value;
		
		/**
		 * Konstruktor
		 * @param key
		 * @param value
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * Lista u koju se spremaju vrijednosti {@code Pair<K, V>}
	 */
	private ArrayIndexedCollection<Pair<K, V>> dictionary;
	
	/**
	 * Konstruktor
	 */
	public Dictionary(){
		this.dictionary = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Vraca {@code true} ako je riječnik prazan
	 * @return {@code true} ako riječnik nema niti jedan par (ključ, vrijednost)
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}
	
	/**
	 * Vraca broj parova (ključ, vrijednost) u riječniku
	 * @return {@code int} broj parova (ključ, vrijednost) u riječniku
	 */
	public int size() {
		return dictionary.size();
	}
	
	/**
	 * Prazni riječnik
	 */
	public void clear() {
		dictionary.clear();
	}
	
	/**
	 * Stvara par (key, value); ako vec postoji u riječniku kljuc <b>key</b> onda overridea staru vrijednost
	 * i ubacuje novu vrijednost <b>value</b>
	 * @param key
	 * @param value
	 * @return {@code V} staru vrijdnost ako je zamjenjena novom, inace {@code null}
	 * @throws NullPointerException u slucaju da je predana vrijednost od <b>key</b> {@code null}
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException();
		
		Pair<K, V> pair;
		ElementsGetter<Pair<K, V>> getter = dictionary.createElementsGetter();
		
		while(getter.hasNextElement()) { // Overrideanje vrijednosti ako vec postoji kljuc
			pair = getter.getNextElement();
			if(pair.key.equals(key)) {
				V temp = pair.value;
				pair.value = value;
				return temp;
			}
		}
		
		// Stvaranje novog ako nema kljuca
		pair = new Pair<>(key, value);
		dictionary.add(pair);
		
		return null;
	}
	
	/**
	 * Vraca vrijednost pridruzenu kljucu <b>key</b>
	 * @param key
	 * @return {@code V} vrijednost pridruzenu kljucu; ako ne postoji vraca {@code null}
	 * @throws NullPointerException u slucaju da je predana vrijednost od <b>key</b> {@code null}
	 */
	public V get(Object key) {
		if(key == null)
			throw new NullPointerException();
		
		Pair<K, V> pair;
		ElementsGetter<Pair<K, V>> getter = dictionary.createElementsGetter();
		
		while(getter.hasNextElement()) { 
			pair = getter.getNextElement();
			if(pair.key.equals(key)) {
				return pair.value;
			}
		}
		return null;
	}
	
	/**
	 * Uklanja zapis sa zadanim ključem i vraća vrijednost koja je bila upisana u riječniku
	 * za taj ključ (ako je postojala); inače vraća null.
	 * @param key
	 * @return 
	 * @throws NullPointerException u slucaju da je predana vrijednost od <b>key</b> {@code null}
	 */
	public V remove(K key) {
		if(key == null)
			throw new NullPointerException();
		
		Pair<K, V> pair;
		ElementsGetter<Pair<K, V>> getter = dictionary.createElementsGetter();
		int currentIndex  = 0;
		
		while(getter.hasNextElement()) { 
			pair = getter.getNextElement();
			if(pair.key.equals(key)) {
				dictionary.remove(currentIndex);
				return pair.value;
			}
			currentIndex++;
		}
		return null;
	}
}















