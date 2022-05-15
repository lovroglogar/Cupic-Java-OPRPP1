package hr.fer.zemris.java.gui.layouts;

/**
 * Predstavlja poziciju komponente unutar {@link CalcLayout} layout-a
 * @author Lovro Glogar
 *
 */
public class RCPosition {

	/**
	 * Red
	 */
	private int row;
	
	/**
	 * Stupac
	 */
	private int column;

	/**
	 * Konstruktor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	/**
	 * Parsira tekst u instancu ove klase
	 * @param text
	 * @return instancu ove klase dobivenu iz teksta
	 * @throws {@code NullPointerException} ako je <b>text</b> == null
	 * @throws {@code IllegalArgumentException} ako je <b>text</b> nije oblika "1, 2"
	 */
	public static RCPosition parse(String text) {
		if(text == null)
			throw new NullPointerException();
		
		String[] s = text.split(",");
		if(s.length != 2)
			throw new IllegalArgumentException();
		
		try {
			return new RCPosition(Integer.parseInt(s[0].trim()), Integer.parseInt(s[1].trim()));
		} catch(NumberFormatException e) {
			System.err.println("Error parsing text");
			return null;
		}
	}
	
}
