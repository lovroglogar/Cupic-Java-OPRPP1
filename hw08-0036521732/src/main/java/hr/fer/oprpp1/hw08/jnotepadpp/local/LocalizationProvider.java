package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private static LocalizationProvider instance = new LocalizationProvider();
	
	private String language;
	
	private ResourceBundle bundle;
	
	private LocalizationProvider() {
		this.setLanguage("en");
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return this.bundle.getString(key);
	}

	@Override
	public String getLanguage() {
		return this.language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", new Locale(language));
		this.fire();
	}
}
