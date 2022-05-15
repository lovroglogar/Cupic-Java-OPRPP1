package hr.fer.oprpp1.custom.collections;
/**
 * Sucelje implementiraju klase koje definiraju ugnjezdenu klasu koja iterira po elementima klase.
 * @author Lovro Glogar
 *
 */
public interface ElementsGetter<T> {
	
	/**
	 * Provjerava postoji li sljedeci element.
	 * @return <code>True</code> ako element postoji
	 */
	public boolean hasNextElement();
	
	/**
	 * Dohvaca sljedeci element.
	 * @return <code>Object</code>
	 */
	public T getNextElement();
	
	/**
	 * Radi p.process na preostalim elementima kolekcije
	 * @param p
	 */
	public default void processRemaining(Processor<T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
