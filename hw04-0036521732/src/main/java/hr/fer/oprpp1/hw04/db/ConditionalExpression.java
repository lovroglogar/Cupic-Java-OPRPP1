package hr.fer.oprpp1.hw04.db;

/**
 * Predstavlja cijeli izraz usporedbe
 * @author Lovro Glogar
 *
 */
public class ConditionalExpression {

	/**
	 * Referenca na ValueGetter 
	 */
	private IFieldValueGetter fieldValueGetter;
	
	/**
	 * Referenca na string literal
	 */
	private String literal;
	
	/**
	 * Referenca na operator usporedbe
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Konstruktor
	 * @param fieldValueGetter
	 * @param literal
	 * @param comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldValueGetter = fieldValueGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	public String getLiteral() {
		return literal;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
