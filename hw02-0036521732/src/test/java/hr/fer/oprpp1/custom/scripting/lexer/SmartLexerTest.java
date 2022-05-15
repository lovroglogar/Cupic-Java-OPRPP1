package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;


public class SmartLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testTextNodeWithLegalEscapeCharacter() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test\\ and\\{$=$}");
		SmartScriptToken token = lexer.nextToken();
		
		assertEquals(SmartScriptTokenType.TEXT_NODE, token.getType());
		assertEquals("Test\\ and\\{$=$}", token.getValue().toString());
	}
	
	@Test
	public void testTextNodeWithIllegalEscapeCharacter() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test \\d and\\{$=$}");
		
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	public void testForLoopNode() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ For   i   @funkcija  -1.2  $}");
		SmartScriptToken token = lexer.nextToken();
		
		assertEquals(SmartScriptTokenType.FOR_LOOP_NODE, token.getType());
		assertEquals("i @funkcija -1.2", token.getValue().toString());
	}
	
	@Test
	public void testEchoNode() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= For   i   @funkcija  -1.2  \"String\"$}");
		SmartScriptToken token = lexer.nextToken();
		
		assertEquals(SmartScriptTokenType.ECHO_NODE, token.getType());
		assertEquals("For i @funkcija -1.2 \"String\" ", token.getValue().toString());
	}
	
	@Test
	public void testTagWithLegalEscapeCharacter() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ For   i \n  @funkcija  -1.2  \"String \\ \"$}");
		SmartScriptToken token = lexer.nextToken();
		
		assertEquals(SmartScriptTokenType.FOR_LOOP_NODE, token.getType());
		assertEquals("i @funkcija -1.2 \"String \\ \"", token.getValue().toString());
	}
	
	@Test
	public void testTagWithIllegalEscapeCharacter() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ For   i   @funkcija \\n -1.2  \"String \\ \"$}");
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}
}
