package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestConditionalExpression {

	@Test
	public void testDirectQuery() {
		String jmbag = (new QueryParser("jmbag   = \"0000000000\"")).getQueriedJMBAG();
		assertEquals("0000000000", jmbag);
	}
	
	@Test
	public void testDirectQueryException() {
		assertThrows(IllegalStateException.class, () -> (new QueryParser("jmbag   = \"0000000000\" and firstName = \"lovro\"")).getQueriedJMBAG());
	}
	
	@Test
	public void testAndParserExceptions() {
		assertThrows(ParserException.class, () -> new QueryParser("and jmbag   = \"0000000000\""));
	}
	
	@Test
	public void testAttributeParserExceptions() {
		assertThrows(ParserException.class, () -> new QueryParser("jmbg   = \"0000000000\""));
	}
	
	@Test
	public void testOperatorParserExceptions() {
		assertThrows(ParserException.class, () -> new QueryParser("jmbag   == \"0000000000\""));
	}
}








