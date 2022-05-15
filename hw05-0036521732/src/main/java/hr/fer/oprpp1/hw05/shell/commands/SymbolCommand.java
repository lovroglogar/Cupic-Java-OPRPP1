package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira symbol naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class SymbolCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba moze ispisati PROMPT, MULTILINE ILI MORELINES simbole.",
			"Naredba takoder moze promjeniti PROMPT, MULTILINE ILI MORELINES simbole.",
			"Prima najmanje 1, a najvise 2 argumenta.",
			"Prvi argument je uvijek ime simbola.",
			"Drugi argument se ne mora zadati, a ako se zada onda se vrijednost simbola zadanog prvim argumentom mijenja na znak zadan drugim"));
	
	private static final String DEFAULT_PROMPT_ARGUMENT = "PROMPT";
	private static final String DEFAULT_MORELINES_ARGUMENT = "MORELINES";
	private static final String DEFAULT_MULTILINE_ARGUMENT = "MULTILINE";
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public SymbolCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("symbol command expects at least 1 argument");
		
		String[] argumentsSplit = arguments.split(" ");
		String symbol = argumentsSplit[0];
		Character oldValue = null;
		
		if(argumentsSplit.length > 2)
			throw new IllegalArgumentException("Command " + commandName + " expects 1 or 2 arguments.");
		
		if(argumentsSplit.length == 2 && argumentsSplit[1].length() != 1)
			throw new IllegalArgumentException("Second argument should be only 1 character");
	
		switch (symbol) {
			case DEFAULT_PROMPT_ARGUMENT: {
				if(argumentsSplit.length == 1)
					env.writeln(String.format("Symbol for %s is '%c'", DEFAULT_PROMPT_ARGUMENT, env.getPromptSymbol()));
				else {
					oldValue = env.getPromptSymbol();
					env.setPromptSymbol(argumentsSplit[1].charAt(0));
					env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", DEFAULT_PROMPT_ARGUMENT, oldValue, env.getPromptSymbol()));
				}
					
				break;
			}
			case DEFAULT_MORELINES_ARGUMENT: {
				if(argumentsSplit.length == 1)
					env.writeln(String.format("Symbol for %s is '%c'", DEFAULT_MORELINES_ARGUMENT, env.getMorelinesSymbol()));
				else {
					oldValue = env.getMorelinesSymbol();
					env.setMorelinesSymbol(argumentsSplit[1].charAt(0));
					env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", DEFAULT_MORELINES_ARGUMENT, oldValue, env.getMorelinesSymbol()));
				}
					
				break;
			}
			case DEFAULT_MULTILINE_ARGUMENT: {
				if(argumentsSplit.length == 1)
					env.writeln(String.format("Symbol for %s is '%c'", DEFAULT_MULTILINE_ARGUMENT, env.getMultilineSymbol()));
				else {
					oldValue = env.getMultilineSymbol();
					env.setMultilineSymbol(argumentsSplit[1].charAt(0));
					env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", DEFAULT_MULTILINE_ARGUMENT, oldValue, env.getMultilineSymbol()));
				}
					
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + symbol);
		}
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return this.commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return this.commandDescription;
	}
}
