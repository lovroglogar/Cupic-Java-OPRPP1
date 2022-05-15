package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Predstavlja jedan dokument otvoren
 * @author Lovro Glogar
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{
	
	/**
	 * Put do dokumenta, null ako se radi o novom dokumentu
	 */
	private Path filePath;
	
	/**
	 * Prati je li dokument mijenjan i promjene nisu spremljene
	 */
	private boolean modified;
	
	/**
	 * Slusaci na dogadaje ove klase
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Text area koji ovaj dokument koristi
	 */
	private JTextArea textArea;
	
	/**
	 * Slusac koji prati promjene u JTextComponent-u ovog objekta;
	 */
	private DocumentListener documentListener = new DocumentListener() {

		@Override
		public void insertUpdate(DocumentEvent e) {
			modified();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			modified();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			modified();
		}
		
		private void modified() {
			if(isModified()) return;
			setModified(true);
		}
	};
	
	/**
	 * Konstruktor
	 * @param filePath
	 * @param content
	 */
	public DefaultSingleDocumentModel(Path filePath, String content) {
		this.filePath = filePath;
		this.textArea = new JTextArea();
		textArea.getInputMap().put(KeyStroke.getKeyStroke("control X"), "none");
		textArea.getInputMap().put(KeyStroke.getKeyStroke("control C"), "none");
		textArea.getInputMap().put(KeyStroke.getKeyStroke("control V"), "none");
		this.textArea.setText(content);
		this.listeners = new ArrayList<>();
		this.textArea.getDocument().addDocumentListener(documentListener);
	}

	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = path;	
		this.listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		fireModified();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(!this.listeners.contains(l)) 
			this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}
	
	/**
	 * Obavjesti slusace o modified promjeni
	 */
	private void fireModified() {
		this.listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}
}
