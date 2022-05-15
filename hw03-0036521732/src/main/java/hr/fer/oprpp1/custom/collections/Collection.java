package hr.fer.oprpp1.custom.collections;
/**
 * Sucelje <code>Collection</code> implementiraju klase koje opisuju kolekciju objekata te implementiraju metode
 * <code>isEmpty, size, add, contains, remove, toArray, forEach, addAll, clear</code>
 * @author Lovro Glogar
 *
 */
public interface Collection<T> {

	/**
	 * Vraca vrijednost <code>True</code> ako je kolekcija prazna.
	 * @return <code>True</code>
	 */
	public default boolean isEmpty() {		
		return this.size() == 0 ? true : false;
	}
	
	/**
	 * Vraca broj elemenata u kolekciji.
	 * @return broj elemenata u kolekciji
	 */
	public int size();
	
	/**
	 * Dodaje na kraj kolekcije objekt <b>value</b>.
	 * @param value element koji se dodaje na kraj kolekcije
	 */
	public void add(T value);
	
	/**
	 * Vraca <code>True</code> ako kolekcija sadrzi barem jedan
	 * element koji je jednak objektu <b>value</b>.
	 * @param value element koji se trazi u kolekciji
	 * @return <code>True</code> ako element <b>value</b> pripada kolekciji
	 */
	public boolean contains(Object value);
	
	/**
	 * Uklanja element <b>value</b> iz kolekcije i vraca
	 * <code>True</code> ako je uspjesno uklonjen.
	 * @param value element koji je potrebno ukloniti iz kolekcije
	 * @return <b>True</b> ako je element uspjesno uklonjen
	 */
	public boolean remove(Object value);
	
	/**
	 * Pretvara kolekciju u listu objekata i vraca dobivenu listu pritom cuvajuci staru kolekciju, 
	 * tj, stvara kopije objekata kolekcije,
	 * u slucaju prazne kolekcije baca iznimku <code>UnsupportedOperationException</code>.
	 * @return listu objekata kolekcije <code>Object[]</code>
	 */
	public Object[] toArray();
	
	/**
	 * Nad svakim objektom kolekcije poziva metodu <code>process</code>
	 * od instance klase <b>Processor</b>.
	 * @param processor implementira metodu <code>process</code> koja se poziva nad svakim elementom kolekcije
	 * @see hr.fer.oprpp1.custom.collections.Processor
	 */
	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> iterator = this.createElementsGetter();
		while(iterator.hasNextElement())
			processor.process(iterator.getNextElement());
	}
	
	/**
	 * Na kraj kolekcije dodaje svaki element od poslane kolekcije <b>other</b>.
	 * @param other kolekcija ciji se elementi dodaju na kraj pozivajuce kolekcije
	 */
	public default void addAll(Collection<? extends T> other) {
		
		class AddAllProcessor implements Processor<T> {
			@Override
			public void process(T value) {
				add(value);
			}
		}
		
		AddAllProcessor processor = new AddAllProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije
	 */
	public void clear();
	
	/**
	 * Stvara i vraca instancu klase koja implementira sucelje <code>ElementsGetter</code>.
	 * 
	 * @return ElementsGetter
	 */
	public ElementsGetter<T> createElementsGetter();
	
	/**
	 * Defaultna metoda u trenutnu kolekciju dodaje elemente kolekcije <b>col</b>
	 * ako zadovoljavaju test definiran s <b>tester</b>.
	 * @param col kolekcija ciji se elementi dodaju
	 * @param tester definira metodu test(Object) kojom se testiraju elementi kolekcije <b>col</b>
	 */
	public default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> iterator = col.createElementsGetter();
		while(iterator.hasNextElement()) {
			T element = iterator.getNextElement();
			if(tester.test(element))
				this.add(element);
		}
	}

}














