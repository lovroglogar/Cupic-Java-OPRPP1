package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Klasa modelira tree naredbu shell-a
 * @author Lovro Glogar
 *
 */
public class TreeCommand implements ShellCommand{
	
	/**
	 * Opis naredbe
	 */
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba prima jedan argument (put direktorija)",
			"Naredba ispisuje stablo zadanog direktorija."));
	
	private String commandName;
	private List<String> commandDescription;
	
	/**
	 * Konstruktor
	 * @param name
	 */
	public TreeCommand(String name) {
		this.commandName = name;
		this.commandDescription = Collections.unmodifiableList(DEFAULT_COMMAND_DESCRIPTION);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null)
			throw new NullPointerException("Command " + commandName + " expects 1 argument");
		
		String[] argumentsSplit = Util.splitArguments(arguments);
		
		if(argumentsSplit.length > 1)
			throw new IllegalArgumentException("Command " + commandName + " expects 1 argument");
		
		Path p = Paths.get(argumentsSplit[0]);
		
		try {
			Files.walkFileTree(p, new MyVisitor(env));
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private static class MyVisitor implements FileVisitor<Path>{
		
		private Environment env;
		private int depth;
		
		public MyVisitor(Environment env) {
			this.env = env;
			this.depth = 0;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			this.env.write(String.format("%s%s\n", " ".repeat(depth * 2), dir.getFileName()));
			this.depth++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.write(String.format("%s%s\n", " ".repeat(depth * 2), file.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			this.depth--;
			return FileVisitResult.CONTINUE;
		}
		
	}

}
