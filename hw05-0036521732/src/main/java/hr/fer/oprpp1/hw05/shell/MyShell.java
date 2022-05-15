package hr.fer.oprpp1.hw05.shell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeCommand;

/**
 * Klasa sluzi kao okolina izvodenja shell-a i pokrece shell
 * @author lovro
 *
 */
public class MyShell implements Environment{
	
	/**
	 * Zadane vrijednosti prompt, morelines i multiline znakova
	 */
	private static final Character DEFAULT_PROMPT_SYMBOL = '>';
	private static final Character DEFAULT_MORELINES_SYMBOL = '\\';
	private static final Character DEFAULT_MULTILINE_SYMBOL = '|';
	
	/**
	 * Zadana imena naredba shell-a 
	 */
	private static final String DEFAULT_EXIT_COMMAND = "exit";
	private static final String DEFAULT_SYMBOL_COMMAND = "symbol";
	private static final String DEFAULT_CHARSETS_COMMAND = "charsets";
	private static final String DEFAULT_CAT_COMMAND = "cat";
	private static final String DEFAULT_LS_COMMAND = "ls";
	private static final String DEFAULT_TREE_COMMAND = "tree";
	private static final String DEFAULT_COPY_COMMAND = "copy";
	private static final String DEFAULT_MKDIR_COMMAND = "mkdir";
	private static final String DEFAULT_HEXDUMP_COMMAND = "hexdump";
	private static final String DEFAULT_HELP_COMMAND = "help";

	/**
	 * Prompt, morelines i multiline simboli
	 */
	private Character promptSymbol;
	private Character morelinesSymbol;
	private Character multilineSymbol;
	
	/**
	 * Sluzi za citanje naredbi koje korisnik upise
	 */
	private Scanner scanner;
	/**
	 * Sluzi za pisanje na konzolu
	 */
	private BufferedWriter writer;
	
	/**
	 * Nepromjenjiva mapa naredbi shell-a
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Konstruktor
	 */
	public MyShell() {
		this.promptSymbol = DEFAULT_PROMPT_SYMBOL;
		this.morelinesSymbol = DEFAULT_MORELINES_SYMBOL;
		this.multilineSymbol = DEFAULT_MULTILINE_SYMBOL;
		this.scanner = new Scanner(System.in);
		this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
		this.commands = null;
	}

	/**
	 * Metoda pokrece shell
	 */
	private void start() {
		
		writeln("Welcome to MyShell v 1.0");
		
		this.commands = commands();
		
		ShellCommand command;
		ShellStatus status;
		String arguments;
		
		StringBuilder sb;
		String line;
		String[] splitCommand;
		
		do {
			sb = new StringBuilder();
		
			write(this.promptSymbol + " ");
			while((line = this.readLine()).endsWith(this.morelinesSymbol.toString())) {
				line.replace("\n", "");
				line.replace("\r", "");
				line = line.substring(0, line.length() - 1);
				sb.append(line);
				write(this.multilineSymbol + " ");
			}
			
			line.replace("\n", "");
			line.replace("\r", "");
			sb.append(line);

			try {
				splitCommand = sb.toString().split(" ", 2);
				command = this.commands.get(splitCommand[0]);
				arguments = null;
				
				if(command == null)
					throw new NullPointerException(" Unrecognized command");
				if(splitCommand.length > 1) {
					arguments = splitCommand[1];
					arguments = arguments.trim();
				}
				
				status = command.executeCommand(this, arguments);
			} catch (ShellIOException e) {
				this.writeln(e.getMessage());
				status = ShellStatus.TERMINATE;
			} catch(Exception e) {
				this.writeln(e.getMessage());
				status = ShellStatus.CONTINUE;
			}
		}while(status == ShellStatus.CONTINUE); 
		
		scanner.close();
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return scanner.nextLine();
		} catch(Exception e) {
			throw new ShellIOException("Error while reading line");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.write(text);
			writer.flush();
		} catch(Exception e) {
			throw new ShellIOException("Error while writing line");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			writer.write(text + "\n");
			writer.flush();
		} catch(Exception e) {
			throw new ShellIOException("Error while writing line");
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		if(this.commands != null)
			return commands;
		
		SortedMap<String, ShellCommand> tempMap = new TreeMap<>();
		
		tempMap.put(DEFAULT_EXIT_COMMAND, new ExitCommand(DEFAULT_EXIT_COMMAND));
		tempMap.put(DEFAULT_SYMBOL_COMMAND, new SymbolCommand(DEFAULT_SYMBOL_COMMAND));
		tempMap.put(DEFAULT_CHARSETS_COMMAND, new CharsetsCommand(DEFAULT_CHARSETS_COMMAND));
		tempMap.put(DEFAULT_CAT_COMMAND, new CatCommand(DEFAULT_CAT_COMMAND));
		tempMap.put(DEFAULT_LS_COMMAND, new LsCommand(DEFAULT_LS_COMMAND));
		tempMap.put(DEFAULT_TREE_COMMAND, new TreeCommand(DEFAULT_TREE_COMMAND));
		tempMap.put(DEFAULT_COPY_COMMAND, new CopyCommand(DEFAULT_COPY_COMMAND));
		tempMap.put(DEFAULT_MKDIR_COMMAND, new MkdirCommand(DEFAULT_MKDIR_COMMAND));
		tempMap.put(DEFAULT_HEXDUMP_COMMAND, new HexdumpCommand(DEFAULT_HEXDUMP_COMMAND));
		tempMap.put(DEFAULT_HELP_COMMAND, new HelpCommand(DEFAULT_HELP_COMMAND));
		
		return Collections.unmodifiableSortedMap(tempMap);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}
	
	/**
	 * Main metoda, stvara shell i pokrece ga
	 * @param args
	 */
	public static void main(String[] args) {
		new MyShell().start();
	}
}
