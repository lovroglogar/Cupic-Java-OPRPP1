package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

public interface SingleDocumentModel {
	/**
	 * Dohvaca text komponentu dokumenta
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Dohvaca put dokumenta ili null ako se radi o novom dokumentu
	 * @return
	 */
	Path getFilePath();

	/**
	 * Postavlja put datoteke
	 * @param path
	 */
	void setFilePath(Path path);

	/**
	 * Vraca true ako je dokument modified
	 * @return
	 */
	boolean isModified();

	/**
	 * Postavlja modified
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Dodaje slusaca
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Mice slusaca
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
