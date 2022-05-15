package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Implementacija sucelja IFilter koja prima listu izraza usporedbe i primjenjuje ih redom na primljenog studenta
 * @author Lovro Glogar
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * Lista izraza usporedbe
	 */
	List<ConditionalExpression> query;
	
	/**
	 * Konstruktor
	 * @param query
	 */
	public QueryFilter(List<ConditionalExpression> query) {
		this.query = query;
	}

	/**
	 * Redom ispituje svaki izraz usporedbe na studentu i vraca true ako je svaki zadovoljen
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expr: query) {
			boolean recordSatisfies = expr.getComparisonOperator().satisfied(
					 expr.getFieldValueGetter().get(record),
					 expr.getLiteral()
					);
			if(!recordSatisfies)
				return false;
		}
		return true;
	}

}
