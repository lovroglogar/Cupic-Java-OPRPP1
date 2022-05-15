package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira help naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class HelpCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba prima 0 ili 1 argument.",
			"Naredba ispisuje sve naredbe i njene opise u slucaju 0 argumenata.",
			"Naredba ispisuje opis naredbe navedene kao argument."));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public HelpCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		SortedMap<String, ShellCommand> commands = env.commands();
		if(arguments == null) {
			commands.forEach((k, v) -> {
				env.writeln("Naredba " + v.getCommandName());
				v.getCommandDescription().forEach(d -> env.writeln("--> " + d) );
			});
		} else {
			String[] argumentsSplit = arguments.split(" ");
			if(argumentsSplit.length > 1)
				throw new IllegalArgumentException("Command " + commandName + " expects 0 or 1 argument.");
			ShellCommand command = commands.get(argumentsSplit[0]);
			if(command == null)
				throw new IllegalArgumentException("Command " + argumentsSplit[0] + " doesn't exist.");
			else {
				env.writeln("Naredba " + command.getCommandName());
				command.getCommandDescription().forEach(d -> env.writeln("--> " + d));
			}
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
