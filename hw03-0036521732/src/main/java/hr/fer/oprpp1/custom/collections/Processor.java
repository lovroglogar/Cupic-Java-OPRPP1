package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje <code>Processor</code> implementira neka klasa koja zatim 
 * implementira funkciju <code>void process</code> koja obavlja neku operaciju
 * na predanom objektu
 * @author Lovro Glogar
 *
 */
public interface Processor<T> {

	/**
	 * Metoda <code>process</code> obavlja operaciju na predanom objektu <code>value</code>
	 * @param value Objekt nad kojim se obavlja operacija
	 */
	public void process(T value);
}
