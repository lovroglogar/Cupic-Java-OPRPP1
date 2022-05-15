package hr.fer.zemris.java.gui.layouts;

import java.util.function.DoubleBinaryOperator;

public class ExpButton extends MyButton implements BinaryOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tekst gumba ako je operacija x^n
	 */
	private static final String text = "x^n";

	/**
	 * Tekst gumba ako je aktivna inverzna funkcija
	 */
	private static final String textInverse = "x^(1/n)";
	
	/**
	 * Operacija x^n
	 */
	private static final DoubleBinaryOperator operation = (d1, d2) -> Math.pow(d1, d2); 
	
	/**
	 * Operacija x^(1/n)
	 */
	private static final DoubleBinaryOperator inverse = (d1, d2) -> Math.pow(d1, 1 / d2);
	
	/**
	 * Aktivna operacija
	 */
	private DoubleBinaryOperator active;
	/**
	 * Konstruktor
	 * @param text
	 * @param textInverse
	 */
	public ExpButton() {
		super(text);
	}
	
	@Override
	public DoubleBinaryOperator getOperation() {
		return this.active;
	}
	
	public void updateButton(boolean inverseFlag) {
		if(inverseFlag) {
			this.setText(textInverse);
			this.active = inverse;
		} else {
			this.setText(text);
			this.active = operation;
		}
	}

}
