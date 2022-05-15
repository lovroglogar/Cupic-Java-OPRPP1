package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
/**
 * Klasa modelira charsets naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class CharsetsCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba ne prima argumente i ispisuje listu dostupnih kodnih stranica",
			"Ispisuje svaku kodnu stranicu u zaseban red"));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public CharsetsCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null)
			throw new IllegalArgumentException(commandName + " command shouldn't take any arguments");
		
		Charset.availableCharsets().forEach( (k,v) -> {
			env.writeln(k);
		});
		
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
