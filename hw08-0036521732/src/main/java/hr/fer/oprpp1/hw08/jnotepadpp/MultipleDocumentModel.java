package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JComponent;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Dohvaca vizualnu komponentu modela
	 * @return
	 */
	JComponent getVisualComponent();

	/**
	 * Stvara novi dokument
	 * @return
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Dohvaca trenutni dokument
	 * @return
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Ucitava dokument
	 * @param path
	 * @return
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Sprema dokument
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Zatvara dokument
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Dodaje slusac
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Mice slusaca
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Vraca broj trenutno otvorenih dokumenata
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * Dohvaca dokument na indexu
	 * @param index
	 * @return
	 */
	SingleDocumentModel getDocument(int index);

	/**
	 * Nalazi dokument prema predanom putu
	 * @param path
	 * @return
	 */
	SingleDocumentModel findForPath(Path path); // null, if no such model exists

	/**
	 * Dohvaca index pridruzen dokumentu ili -1 ako nema dokumenta
	 * @param doc
	 * @return
	 */
	int getIndexOfDocument(SingleDocumentModel doc); // -1 if not present
}
