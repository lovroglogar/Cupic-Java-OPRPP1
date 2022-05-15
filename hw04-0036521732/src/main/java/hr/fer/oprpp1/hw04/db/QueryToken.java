package hr.fer.oprpp1.hw04.db;


public class QueryToken {
	/**
	 * Tip tokena
	 */
	private QueryTokenType type;
	
	/**
	 * Vrijednost tokena
	 */
	private Object value;

	/**
	 * Konstruktor
	 * @param type
	 * @param value
	 */
	public QueryToken(QueryTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost tokena
	 * @return Object
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Vraca tip tokena
	 * @return
	 */
	public QueryTokenType getType() {
		return this.type;
	}
}
