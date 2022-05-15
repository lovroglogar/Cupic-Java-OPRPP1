package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sucelje odgovorno za dohvat polja koje se zahtjeva iz StudentRecord
 * @author Lovro Glogar
 *
 */
public interface IFieldValueGetter {

	public String get(StudentRecord record);
	
}
