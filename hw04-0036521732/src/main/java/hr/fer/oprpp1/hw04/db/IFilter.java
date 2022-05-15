package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sucelje koje predstavlja filter na studenta
 * @author Lovro Glogar
 *
 */
public interface IFilter {
	public boolean accepts(StudentRecord record);
}
