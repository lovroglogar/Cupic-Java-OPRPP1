package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira ls naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class LsCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba prima jedan argument koji mora biti put do nekog direktorija.",
			"Naredba ispisuje sadrzaj tog direktorija"));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public LsCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("Comamnd " + commandName + " expects exactly 1 argument (directory path).");
		
		String[] argumentsSplit = Util.splitArguments(arguments);
		
		if(argumentsSplit.length > 1) 
			throw new IllegalArgumentException("Command " + commandName + " expects exactly 1 argument");
		
		File directory = new File(argumentsSplit[0]);
		
		if(!directory.isDirectory())
			throw new IllegalArgumentException("Command " + commandName + " expects directory path as argument");
		
		File[] children = directory.listFiles();
		
		try {
			
			for(File f: children) {
				env.write(String.format("%s%s%s%s %10d %s %s\n", 
											f.isDirectory() ? "d" : "-",
											f.canRead() ? "r" : "-",
											f.canWrite() ? "w" : "-",
											f.canExecute() ? "x" : "-",
											f.length(),
											getDateAndTime(f),
											f.getName()));
			}
		}catch (IOException e) {
			env.writeln("Date and time error");
		}
		
		return ShellStatus.CONTINUE;
	}

	private String getDateAndTime(File f) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = f.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
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

