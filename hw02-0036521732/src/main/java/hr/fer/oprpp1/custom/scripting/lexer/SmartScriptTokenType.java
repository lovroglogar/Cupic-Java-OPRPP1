package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Svi moguci tipovi SmartScriptTokena
 * @author LovroGlogar
 *
 */
public enum SmartScriptTokenType {
	/**
	 * Predstavlja text node
	 */
	TEXT_NODE,
	
	/**
	 * Predstavlja for loop node, tj. pocetak for petlje
	 */
	FOR_LOOP_NODE,
	
	/**
	 * Predstavlja kraj for petlje
	 */
	FOR_LOOP_END,
	
	/**
	 * Predstavlja echo node
	 */
	ECHO_NODE,
	
	/**
	 * Predstavlja kraj datoteke
	 */
	EOF
}
