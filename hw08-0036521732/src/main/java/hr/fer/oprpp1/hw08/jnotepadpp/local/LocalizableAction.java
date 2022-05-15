package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private ILocalizationListener listener;
	
	private ILocalizationProvider provider;

	public LocalizableAction(String key, ILocalizationProvider localizationProvider) {
		this.key = key;
		this.provider = localizationProvider;
		this.putValue(Action.NAME, this.provider.getString(this.key));
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, provider.getString(LocalizableAction.this.key));
			}
		};
		this.provider.addLocalizationListener(listener);
	}

	public String getKey() {
		return key;
	}

	public ILocalizationProvider getProvider() {
		return provider;
	}
	
	
}
