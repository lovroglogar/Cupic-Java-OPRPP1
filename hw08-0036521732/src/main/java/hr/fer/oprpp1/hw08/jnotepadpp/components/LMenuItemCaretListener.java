package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.oprpp1.hw08.jnotepadpp.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * MenuItem koji je Caret listener i Multiple document listener
 * @author lovro
 *
 */
public class LMenuItemCaretListener extends JMenuItem implements CaretListener, MultipleDocumentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kljuca za lokalizaciju
	 */
	private String key;
	
	/**
	 * Slusac za lokalizaciju
	 */
	private ILocalizationListener listener;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider provider;
	
	/**
	 * Konstruktor
	 * @param key
	 * @param provider
	 * @param action
	 */
	public LMenuItemCaretListener(String key, ILocalizationProvider provider, Action action) {
		super(action);
		this.setEnabled(false);
		this.key = key;
		this.provider = provider;
		this.setText(provider.getString(key));
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(LMenuItemCaretListener.this.key));
			}
		};
		this.provider.addLocalizationListener(listener);
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if(e.getDot() != e.getMark()) 
			this.setEnabled(true);
		else this.setEnabled(false);
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(previousModel != null)
			previousModel.getTextComponent().removeCaretListener(this);
		
		if(currentModel != null)
			currentModel.getTextComponent().addCaretListener(this);
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		
	}
	
}
