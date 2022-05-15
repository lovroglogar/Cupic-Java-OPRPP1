package hr.fer.oprpp1.custom.scripting.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartParserTest {
	
	@Test
	public void testJedanTextNode() {
		SmartScriptParser parser = new SmartScriptParser("Text node \\ \\{$ ovo je isto tekst $}");
		assertEquals("Text node \\ \\{$ ovo je isto tekst $}", parser.getDocumentNode().toString());
	}
	
	@Test
	public void testForLoopIspravan() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOr i @funkcija \"String\" $} sdf {$End$}");
		assertEquals("{$FOR i @funkcija \"String\" $} sdf {$END$}", parser.getDocumentNode().toString());
	}
	
	@Test
	public void testForLoopBezEndTaga() {
		assertThrows(SmartScriptParserException.class, () -> { new SmartScriptParser("{$ FOr i @funkcija \"String\" $} sdf");});
	}
	
	@Test
	public void testForLoopPreviseArgumenata() {
		assertThrows(SmartScriptParserException.class, () -> { new SmartScriptParser("{$ FOr i @funkcija \"String\" -1.2 1.3 $} sdf {$End$}");});
	}
	
	@Test
	public void testForLoopPrviArgumentNijeVarijabla() {
		assertThrows(SmartScriptParserException.class, () -> { new SmartScriptParser("{$ FOr @funkcija \"String\" -1.2 1.3 $} sdf {$End$}");});
	}
	
	@Test
	public void testForLoopNeispravanEscapeZnak() {
		assertThrows(SmartScriptParserException.class, () -> { new SmartScriptParser("{$ FOr i @funkcija \\n \"String\" -1.2 1.3 $} sdf {$End$}");});
	}
	
	@Test
	public void testForLoopIspravanEscapeZnakUStringu() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOr i @funkcija  \"String \\\"u stringu\\\" sd\"$} sdf {$End$}");
		assertEquals("{$FOR i @funkcija \"String \\\"u stringu\\\" sd\" $} sdf {$END$}", parser.getDocumentNode().toString());
	}
	
	@Test
	public void testEchoTagIspravan() {
		SmartScriptParser parser = new SmartScriptParser("{$ = varijabla @funkcija -1.2 1.2 123 \"String\"$}");
		assertEquals("{$= varijabla @funkcija -1.2 1.2 123 \"String\"  $}", parser.getDocumentNode().toString());
	}
	
}
