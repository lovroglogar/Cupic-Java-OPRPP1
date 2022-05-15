package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Nasljeduje {@link JButton}.
 * Modelira gumb sa svojstvima koja su zajednika svakom gumbu u kalkulatoru
 * @author Lovro Glogar
 *
 */
public class MyButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyButton(String text) {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBackground(Color.lightGray);
		this.setText(text);
	}
	
}
