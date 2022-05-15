package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira exit naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class ExitCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba gasi shell"));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public ExitCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null)
			throw new IllegalArgumentException(commandName + " command shouldn't take any arguments");
		return ShellStatus.TERMINATE;
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
