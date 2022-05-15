package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Klasa nasljeduje {@link JLabel}.
 * Klasa implementira {@link CalcValueListener}
 * @author Lovro Glogar
 *
 */
public class MyLabel extends JLabel{

	/**
	 * Listener
	 * @author Lovro Glogar
	 *
	 */
	private class CalcValueListenerImpl implements CalcValueListener{
		@Override
		public void valueChanged(CalcModel model) {
			MyLabel.this.setText(model.toString());
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Referenca na slusaca
	 */
	private CalcValueListenerImpl listener;
	
	/**
	 * Konstruktor
	 * @param text
	 */
	public MyLabel(String text) {
		super(text);
		this.setOpaque(true);
		this.setBackground(Color.yellow);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		this.setFont(this.getFont().deriveFont(30f));
		this.listener = new CalcValueListenerImpl();
	}

	public CalcValueListenerImpl getListener() {
		return listener;
	}
}
