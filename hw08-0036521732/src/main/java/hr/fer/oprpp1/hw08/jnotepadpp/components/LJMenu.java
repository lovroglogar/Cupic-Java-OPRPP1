package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.JMenu;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Lokalizirani menu
 * @author Lovro Glogar
 *
 */
public class LJMenu extends JMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ILocalizationListener listener;
	
	private ILocalizationProvider provider;
	
	public LJMenu(String key, ILocalizationProvider provider) {
		super();
		this.provider = provider;
		this.setText(provider.getString(key));
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		};
		this.provider.addLocalizationListener(listener);
	}

}
