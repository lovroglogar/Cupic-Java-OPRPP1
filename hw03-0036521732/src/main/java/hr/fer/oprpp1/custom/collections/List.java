package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author Lovro Glogar
 *
 */
public interface List<T> extends Collection<T> {

	/**
	 * Vraca objekt koji se nalazi na poziciji <b>index</b>.
	 * @param index pozicija trazenog objekta, legalne vrijednosti [0, velicinaKolekcije - 1]
	 * @return objekt na poziciji <b>index</b>
	 */
	public T get(int index);
	
	/**
	 * Umece objekt <b>value</b> na poziciju <b>position</b>.
	 * @param value objekt koji se umece
	 * @param position pozicija na koju se umece, legalne pozicije su [0, velicinaKolekcije]
	 */
	public void insert(T value, int position);
	
	/**
	 * Vraca poziciju/indeks predanog objekta <b>value</b>.
	 * @param value trazeni objekt
	 * @return indeks predanog objekta; -1 u slucaju da objekt ne pripada kolekciji
	 */
	public int indexOf(Object value);
	
	/**
	 * Uklanja element na poziciji <b>index</b> iz kolekcije.
	 * @param index pozicija elementa koji se treba ukolini, legalne vrijednosti su [0, velicinaKolekcije - 1]
	 */
	public void remove(int index);
}
