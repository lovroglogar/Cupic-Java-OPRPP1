package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.JLabel;

import hr.fer.oprpp1.hw08.jnotepadpp.ClockListener;

/**
 * Labela koja je Clock listener
 * @author Lovro Glogar
 *
 */
public class JLabelClockListener extends JLabel implements ClockListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Trenutno vrijeme koje je dobiveno od Clocka
	 */
	String clock;

	@Override
	public void clockChanged(String time) {
		this.setText(time);
	}

}
