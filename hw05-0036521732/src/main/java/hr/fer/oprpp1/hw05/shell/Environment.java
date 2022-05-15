package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Sucelje modelira okolinu u kojoj se izvode shell naredbe
 * @author lovro
 *
 */
public interface Environment {
	
	/**
	 * Metoda cita sljedecu naredbu koju korisnik upise
	 * @return naredbu u obliku stringa
	 * @throws ShellIOException u slucaju pogreske tijekom citanja
	 */
	public String readLine() throws ShellIOException;
	
	/**
	 * Metoda pise liniju bez oznake novog reda
	 * @param text
	 * @throws ShellIOException u slucaju pogreske tijekom pisanja
	 */
	public void write(String text) throws ShellIOException;
	
	/**
	 * Metoda pise liniju sa oznakom novog reda
	 * @param text
	 * @throws ShellIOException u slucaju pogreske tijekom pisanja
	 */
	public void writeln(String text) throws ShellIOException;
	
	/**
	 * Metoda vraca ne promjenjivu mapu naredbi shell-a
	 * @return
	 */
	public SortedMap<String, ShellCommand> commands();
	
	/**
	 * Vraca znak za MULTILINE
	 * @return
	 */
	public Character getMultilineSymbol();
	
	/**
	 * Postavlja MULTILINE znak
	 * @param symbol
	 */
	public void setMultilineSymbol(Character symbol);
	
	/**
	 * Vraca znak za PROMPT
	 * @return
	 */
	public Character getPromptSymbol();
	
	/**
	 * Postavlja PROMPT znak
	 * @param symbol
	 */
	public void setPromptSymbol(Character symbol);
	
	/**
	 * Vraca znak za MORELINES
	 * @return
	 */
	public Character getMorelinesSymbol();
	
	/**
	 * Postavlja MORELINES znak
	 * @param symbol
	 */
	public void setMorelinesSymbol(Character symbol);
}
