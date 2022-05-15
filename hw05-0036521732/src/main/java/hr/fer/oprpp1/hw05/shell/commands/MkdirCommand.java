package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira mkdir naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class MkdirCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba prima jedan argument, put direktorija.",
			"Naredba stvara novi direktorij."));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public MkdirCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("Command " + commandName + " expects 1 argument.");
		
		String[] argumentsSplit = Util.splitArguments(arguments);
		
		if(argumentsSplit.length != 1)
			throw new IllegalArgumentException("Command " + commandName + " expects 1 argument.");
		
		File directory = new File(argumentsSplit[0]);
		
		if(!directory.exists()) 
			directory.mkdirs();
		else throw new IllegalArgumentException("Given directory \"" + directory.getName() + "\" already exists");
		
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
