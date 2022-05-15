package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Predstavlja stanja u kojima se <code>SmartScriptLexer</code> moze nalaziti
 * @author Lovro Glogar
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Lexer je trenutno van taga
	 */
	OUTSIDE_OF_TAGS,
	
	/**
	 * Lexer je trenutno unutar taga
	 */
	INSIDE_OF_TAGS
}
