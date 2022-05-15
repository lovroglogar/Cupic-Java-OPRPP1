package hr.fer.oprpp1.custom.collections;
/**
 * Klasa <code>Collection</code> reprezentira generalnu kolekciju objekata te implementira metode
 * <code>isEmpty, size, add, contains, remove, toArray, forEach, addAll, clear</code>
 * @author Lovro Glogar
  *
 */
public class Collection {

	/**
	 * Vraca vrijednost <code>True</code> ako je kolekcija prazna.
	 * @return <code>True</code>
	 */
	public boolean isEmpty() {		
		return this.size() == 0 ? true : false;
	}
	
	/**
	 * Vraca broj elemenata u kolekciji.
	 * @return broj elemenata u kolekciji
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje na kraj kolekcije objekt <b>value</b>.
	 * @param value element koji se dodaje na kraj kolekcije
	 */
	public void add(Object value) {}
	
	/**
	 * Vraca <code>True</code> ako kolekcija sadrzi barem jedan
	 * element koji je jednak objektu <b>value</b>.
	 * @param value element koji se trazi u kolekciji
	 * @return <code>True</code> ako element <b>value</b> pripada kolekciji
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Uklanja element <b>value</b> iz kolekcije i vraca
	 * <code>True</code> ako je uspjesno uklonjen.
	 * @param value element koji je potrebno ukloniti iz kolekcije
	 * @return <b>True</b> ako je element uspjesno uklonjen
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Pretvara kolekciju u listu objekata i vraca dobivenu listu pritom cuvajuci staru kolekciju, 
	 * tj, stvara kopije objekata kolekcije,
	 * u slucaju prazne kolekcije baca iznimku <code>UnsupportedOperationException</code>.
	 * @return listu objekata kolekcije <code>Object[]</code>
	 * @throws UnsupportedOperationException u slucaju prazne kolekcije
	 */
	public Object[] toArray() {	
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Nad svakim objektom kolekcije poziva metodu <code>process</code>
	 * od instance klase <b>Processor</b>.
	 * @param processor implementira metodu <code>process</code> koja se poziva nad svakim elementom kolekcije
	 * @see hr.fer.oprpp1.custom.collections.Processor
	 */
	public void forEach(Processor processor) {}
	
	/**
	 * Na kraj kolekcije dodaje svaki element od poslane kolekcije <b>other</b>.
	 * @param other kolekcija ciji se elementi dodaju na kraj pozivajuce kolekcije
	 */
	public void addAll(Collection other) {
		
		class AddAllProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		AddAllProcessor processor = new AddAllProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije
	 */
	public void clear() {}
}














