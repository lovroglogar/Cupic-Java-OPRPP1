package hr.fer.oprpp1.hw04.db;

/**
 * Sadrzi implementacije sucelja IFieldValueGetter FIRST_NAME, LAST_NAME i JMBAG
 * @author Lovro Glogar
 *
 */
public class FieldValueGetters {

	/**
	 * Dohvaca first name od predanog studenta
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	
	/**
	 * Dohvaca last name od predanog studenta
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	
	/**
	 * Dohvaca jmbag od predanog studenta
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
	
}
