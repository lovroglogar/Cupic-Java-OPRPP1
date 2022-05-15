package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira cat naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class CatCommand  implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba obavezno prima jedan argument, a može primati i dva.",
			"Prvi argument je put neke datoteke.",
			"Drugi argument je kodna stranica koja se koristi za interpretiranje datoteke. Ako nije predan koristi se defaultna kodna stranica.",
			"Naredba otvara poslanu datoteku i ispisuje ju."));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public CatCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("Command " + commandName + " expects 1 or 2 arguments");
		
		String[] argumentsSplit = Util.splitArguments(arguments);
		
		if(argumentsSplit.length > 2) 
			throw new IllegalArgumentException("Command " + commandName + " expects 1 or 2 arguments");
		
		Path p = Paths.get(argumentsSplit[0]);
		String charset = null;
		if(argumentsSplit.length == 2) 
			charset = argumentsSplit[1];
		else
			charset = Charset.defaultCharset().toString();

		try(BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(p), charset))){
			String line;
			while((line = br.readLine()) != null)
				env.writeln(line);
		} catch (Exception e) {
			e.printStackTrace();
			env.writeln("Error while reading file");
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
