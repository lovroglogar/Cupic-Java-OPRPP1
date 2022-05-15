package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private String currentLanguage;

	private boolean connected;

	private ILocalizationProvider parent;

	private ILocalizationListener listener;

	public LocalizationProviderBridge(LocalizationProvider localizationProvider) {
		this.parent = localizationProvider;
		this.currentLanguage = parent.getLanguage();
		this.connected = false;
	}

	@Override
	public String getString(String key) {
		return this.parent.getString(key);
	}

	@Override
	public String getLanguage() {
		return this.currentLanguage;
	}

	public void connect() {
		if (!this.connected) {
			if (!this.parent.getLanguage().equals(currentLanguage)) {
				this.currentLanguage = this.parent.getLanguage();
				this.fire();
			}
		}
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				currentLanguage = parent.getLanguage();
				fire();
			}
		};
		this.parent.addLocalizationListener(listener);
		this.connected = true;
	}

	public void disconnect() {
		if (this.connected) {
			this.parent.removeLocalizationListener(listener);
			this.connected = false;
		}
	}

}
