package hr.fer.zemris.java.gui.layouts;

import java.util.function.Consumer;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Klasa nasljeduje {@link MyButton}.
 * Predstavlja gumb koji obavlja funkciju bez povratne vrijednosti i parametra
 * @author Lovro Glogar
 *
 */
public class OneLineButton extends MyButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Operacija
	 */
	private Consumer<CalcModelImpl> operation;

	/**
	 * Konstruktor
	 * @param operation
	 * @param text
	 */
	public OneLineButton(OneLineOperations operation, String text) {
		super(text);
		switch(operation) {
			case CLR: this.operation = (model) -> model.clear(); break;
			case RESET: this.operation = (model) -> model.clearAll(); break;
			case SIGN: this.operation = (model) -> model.swapSign(); break;
			case DECIMAL: this.operation = (model) -> model.insertDecimalPoint(); break;
		}
	}
	
	/**
	 * Pokusa primjeniti operaciju nad modelom kalkulatora
	 * @param model
	 */
	public void applyOperation(CalcModelImpl model) {
		try {
			this.operation.accept(model);
		} catch(CalculatorInputException e) {
			System.err.println(e.getMessage());
		}
	}

}
