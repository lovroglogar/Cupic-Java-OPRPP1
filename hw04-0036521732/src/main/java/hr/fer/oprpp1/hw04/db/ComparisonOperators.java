package hr.fer.oprpp1.hw04.db;

/**
 * Sadrzi implementacije sucelja IComparisonOperator LESS, LESS_OR_EQUALS, 
 * GRETER, GREATER_OR_EQUALS, EQUALS, NOT_EQUALS i LIKE
 * @author Lovro Glogar
 *
 */
public class ComparisonOperators {

	/**
	 * Predstavlja usporedbu "manje od" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} manje od {@code o2}
	 */
	public static final IComparisonOperator LESS = (o1, o2) -> o1.compareTo(o2) < 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "manje ili jednako" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} manje ili jednako {@code o2}
	 */
	public static final IComparisonOperator LESS_OR_EQULS = (o1, o2) -> o1.compareTo(o2) <= 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "vece od" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} vece od {@code o2}
	 */
	public static final IComparisonOperator GREATER = (o1, o2) -> o1.compareTo(o2) > 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "vece ili jednako" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} vece ili jednako {@code o2}
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (o1, o2) -> o1.compareTo(o2) >= 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "jednako" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} jednako {@code o2}
	 */
	public static final IComparisonOperator EQUALS = (o1, o2) -> o1.compareTo(o2) == 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "nije jednako" izmedu {@code o1} i {@code o2}.
	 * Vraca true ako je {@code o1} nije jednako {@code o2}
	 */
	public static final IComparisonOperator NOT_EQUALS = (o1, o2) -> o1.compareTo(o2) != 0 ? true : false;
	
	/**
	 * Predstavlja usporedbu "LIKE" izmedu {@code o1} i {@code o2}.
	 */
	public static final IComparisonOperator LIKE = (o1, o2) -> {
		if(!o2.contains("*"))
			return o1.equals(o2);
		
		if(o2.startsWith("*"))
			return o1.endsWith(o2.substring(1));
		else if (o2.endsWith("*"))
			return o1.startsWith(o2.substring(0, o2.length() - 1));
		else {
			String[] split = o2.split("\\*");
			if(split.length != 2)
				throw new IllegalArgumentException("Expression '" + o2 + "' must not contain more than 1 character '*'");
			else
				return o1.length() >= (split[0].length() + split[1].length()) && o1.startsWith(split[0]) && o1.endsWith(split[1]);
		}
	};
	
	
	
}
