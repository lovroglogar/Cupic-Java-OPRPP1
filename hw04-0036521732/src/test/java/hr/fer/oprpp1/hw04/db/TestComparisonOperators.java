package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestComparisonOperators {

	@Test
	public void testLessOperator() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "A"));
	}
	
	@Test
	public void testLessOrEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQULS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testGreaterOperator() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testNotEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testLikeOperator() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "J*na"));
		assertEquals(true, oper.satisfied("Jasna", "*na"));
		assertEquals(true, oper.satisfied("Jasna", "Jas*"));
		assertEquals(false, oper.satisfied("Jasna", "*Jas"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
}









