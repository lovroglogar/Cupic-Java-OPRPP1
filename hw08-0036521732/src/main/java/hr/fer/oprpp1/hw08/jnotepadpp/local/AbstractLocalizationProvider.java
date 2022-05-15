package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if(listener == null)
			throw new NullPointerException("Listener is null");
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		if(listener == null)
			throw new NullPointerException("Listener is null");
		listeners.remove(listener);
	}
	
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
	
}
