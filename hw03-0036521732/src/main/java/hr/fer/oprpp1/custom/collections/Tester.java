package hr.fer.oprpp1.custom.collections;

/**
 * Suceljem <b>Tester</b> modeliraju se objekti koji primaju i testiraju neki objekt
 * @author Lovro Glogar
 *
 */
public interface Tester<T> {
	
	/**
	 * Metoda prima i testira objekt.
	 * @param obj objekt nad kojim se provodi testiranje
	 * @return <code>boolean</code>
	 */
	public boolean test(T obj);
}
