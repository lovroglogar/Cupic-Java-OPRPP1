package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Parser
 * @author Lovro Glogar
 *
 */
public class SmartScriptParser {
	
	/**
	 * Lexer koji parser koristi
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * Dokument koji se dobije parsiranjem
	 */
	private DocumentNode documentNode;
	
	/**
	 * Konstruktor
	 * @param documentBody
	 */
	public SmartScriptParser(String documentBody) {
		lexer = new SmartScriptLexer(documentBody);
		parseDocument();
	}
	
	/**
	 * Parsira dokument koristeci lexer
	 */
	public void parseDocument() {
		try {
			SmartScriptToken token;
			ObjectStack stack = new ObjectStack();
			documentNode = new DocumentNode();
			Node newNode;
			Node topNode;
			StringBuilder sb = new StringBuilder();
			stack.push(documentNode);
			
			token = lexer.nextToken(); // prvi token

			if(token.getType() == SmartScriptTokenType.EOF) { // ako je prazan dokument
				documentNode.setDocumentBody("");
				return;
			}
			
			while(token.getType() != SmartScriptTokenType.EOF) {
				topNode = (Node) stack.peek();
				newNode = null;
				boolean isForLoopNode = false;
				
				switch(token.getType()) {
					case TEXT_NODE:
						newNode = (TextNode) token.getValue();
						sb.append(newNode.toString());
						break;
					case ECHO_NODE:
						newNode = (EchoNode) token.getValue();
						sb.append("{$= " + newNode.toString() + " $}");
						break;
					case FOR_LOOP_NODE: 
						newNode = (ForLoopNode) token.getValue();
						isForLoopNode = true;
						sb.append("{$FOR " + newNode.toString() + " $}");
						break;
					case FOR_LOOP_END:
						newNode = null;
						sb.append("{$END$}");
						break;
					case EOF:
						continue;
				}
				
				if(newNode == null) { // znaci da je dosao kraj FOR petlje
					stack.pop();
					if(stack.isEmpty())
						throw new SmartScriptParserException();
				} else {
					topNode.addChildNode(newNode);
					if(isForLoopNode) 
						stack.push(newNode);
				}
				//System.out.println(token.getType() + " -- " + token.getValue());
				token = lexer.nextToken();
			}
			
			stack.pop();
			if(!stack.isEmpty())
				throw new SmartScriptParserException();
			
			documentNode.setDocumentBody(sb.toString());
		}catch(Exception e) {
			throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Vraca <b>documentNode</b>
	 * @return <b>documentNode</b>
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
}
