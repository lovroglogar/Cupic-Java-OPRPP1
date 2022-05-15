package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		
		//String filepath = args[0];		
		//String docBody = new String( Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);		
		String docBody = "ovo je \\{$ = g$} dsf  {$FoR varijabla -1.234 @funkcija \"String\" $} d\\ {$END$} {$= \"String \\\" \" $}";	
		SmartScriptParser parser = null;
		System.out.println(docBody);
		try {
		 parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
		 System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
		
		parser= new SmartScriptParser(originalDocumentBody);
		originalDocumentBody = parser.getDocumentNode().toString();
		System.out.println(originalDocumentBody);
	}
}
