package hr.fer.zemris.java.gui.layouts;

import java.util.function.DoubleBinaryOperator;

/**
 * Klasa nasljeduje {@link MyButton}. Opisuje gumb koji pokrece binarnu operaciju zbrajanja, oduzimanja, dijeljenja,
 * mnozenja ili potenciranja
 * @author Lovro Glogar
 *
 */
public class BinaryOperationButton extends MyButton implements BinaryOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Funkcija koja se primjenjuje
	 */
	private DoubleBinaryOperator operation;

	/**
	 * Konstruktor
	 * @param operation operacija koju gumb prijmenjuje
	 * @param text tekst na gumbu
	 */
	public BinaryOperationButton(BinaryOperations operation ,String text) {
		super(text);
		switch(operation) {
			case ADD: 
				this.operation = (d1, d2) -> d1 + d2; break;
			case DIV: 
				this.operation = (d1, d2) -> {
					if(d2 == 0)
						throw new IllegalArgumentException("Division by 0 not allowed");
					return d1 / d2;
				};
				break;
			case MUL: 
				this.operation = (d1, d2) -> d1 * d2; break;
			case SUB: 
				this.operation = (d1, d2) -> d1 - d2; break; 
		}
	}
	
	/**
	 * Dohvaca funkciju ili njen inverz ovisno o <b>inverseFlag</b>
	 * @param inverseFlag zastavica oznacava primjenjuje li se inverz funkcije
	 * @return operaciju
	 */
	@Override
	public DoubleBinaryOperator getOperation() {
		return operation;
	}
	
	
}
