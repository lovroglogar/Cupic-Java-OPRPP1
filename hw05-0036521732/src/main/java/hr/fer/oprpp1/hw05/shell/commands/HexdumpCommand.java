package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class HexdumpCommand implements ShellCommand{
	
	private static final List<String> DEFAULT_COMMAND_DESCRIPTION = new ArrayList<>(Arrays.asList(
			"Naredba prima 1 argument.",
			"Naredba ispisuje heksadekadski ispis datoteke."));
	
	private String commandName;
	private List<String> commandDescription;
	
	public HexdumpCommand(String name) {
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
		
		Path p = Paths.get(argumentsSplit[0]);
		
		try(InputStream is = new BufferedInputStream(Files.newInputStream(p))) {
			
			byte[] buffer = new byte[16];
			int length;
			int row = 1;
			String rowHex;
			StringBuilder sb;
			int i;
			
			while((length = is.read(buffer)) > 0) {
				sb = new StringBuilder();
				rowHex = Integer.toHexString(row * 16);
				row++;
				sb.append(rowHex);
				sb.reverse();
				for(i = rowHex.length(); i < 8; i++)
					sb.append("0");
				sb.reverse();
				env.write(sb.toString() + ": ");
				
				for(i = 0; i < 16; i++) {
					if(i < length) {
						sb = new StringBuilder();
						sb.append(Integer.toHexString(buffer[i]));
						if(sb.length() == 1) {
							sb.reverse();
							sb.append("0");
							sb.reverse();
						}
						env.write(sb.toString());
					}
					else
						env.write("  ");
					if(i + 1 == 8)
						env.write("|");
					else
						env.write(" ");
				}
				env.write("| ");
				
				for(i = 0; i < length; i++) {
					if(buffer[i] < 32 || buffer[i] > 127)
						env.write(".");
					else {
						char c = (char) buffer[i];
						Character c1 = Character.valueOf(c);
						env.write(c1.toString());
					}	
				}
				env.write("\n");
			}
			
		} catch (IOException e) {
			env.writeln("Reading file error.");
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
