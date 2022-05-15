package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Sucelje modelira naredbu shell-a
 * @author lovro
 *
 */
public interface ShellCommand {
	
	/**
	 * Metoda izvodi naredbu
	 * @param env okolina u kojoj se izvodi naredba
	 * @param arguments argumenti predani naredbi
	 * @return Shell status CONTINUE ili TERMINATE
	 */
	public ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Dohvaca ime naredbe
	 * @return ime naredbe
	 */
	public String getCommandName();
	
	/**
	 * Dohvaca opis naredbe
	 * @return
	 */
	public List<String> getCommandDescription();

}
