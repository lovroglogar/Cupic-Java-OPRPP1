package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CopyCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba ocekuje 2 argumenta.",
			"Prvi argument je ime izvorne datoteke, dok je drugi ime datoteke odredista.",
			"Naredba kopira sadrzaj izvorne datoteke u odredisnu datoteku.",
			"Naredba trazi od korisnika potvrdu ako odredisna vec postoji"));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public CopyCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("Command " + commandName + " expects 2 arguments");
		
		String[] argumentsSplit = Util.splitArguments(arguments);
		
		if(argumentsSplit.length != 2)
			throw new IllegalArgumentException("Command " + commandName + " expects 2 arguments");
		
		File source = new File(argumentsSplit[0]);
		File destination = new File(argumentsSplit[1]);
		
		if(destination.exists()) {
			env.write("Destination file already exists. Do you wish to overwrite it? [y, n] ");
			String response = env.readLine();
			while(!response.equals("y") && !response.equals("n")) {
				env.writeln("Write 'y' if yes and 'n' if no");
				response = env.readLine();
			}
			if(response.equals("n"))
				return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = new BufferedInputStream(Files.newInputStream(source.toPath()));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination.toPath()));){
			byte[] buffer = new byte[1024];
			int lengthRead;
			while((lengthRead = is.read(buffer)) > 0) {
				os.write(buffer, 0, lengthRead);
			}
		} catch (IOException e) {
			env.writeln("Reading and writing error");
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
