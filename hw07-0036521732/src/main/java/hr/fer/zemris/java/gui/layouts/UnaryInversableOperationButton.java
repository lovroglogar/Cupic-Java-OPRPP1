package hr.fer.zemris.java.gui.layouts;

import java.util.function.DoubleFunction;

/**
 * Klasa nasljeduje {@link MyButton}.
 * Predstavlja gumb koji primjenjuje unarnu inverzibilnu funkciju.
 * @author Lovro Glogar
 *
 */
public class UnaryInversableOperationButton extends MyButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tekst na gumbu
	 */
	String text;
	
	/**
	 * Tekst na gumbu za inverznu funkciju
	 */
	String textInverse;
	
	/**
	 * Operacija
	 */
	UnaryInversableOperation operation;
	
	/**
	 * Funkcija
	 */
	DoubleFunction<Double> function;
	
	/**
	 * Inverz funkcije
	 */
	DoubleFunction<Double> inverse;
	
	
	/**
	 * Trenutno aktivna funkcija
	 */
	DoubleFunction<Double> active;
	
	/**
	 * Konstruktor
	 * @param operation
	 * @param text
	 */
	public UnaryInversableOperationButton(UnaryInversableOperation operation, String text, String textInverse) {
		super(text);
		this.text = text;
		this.textInverse = textInverse;
		this.operation = operation;
		function = InverseOperationsUtil.getFunction(operation);
		inverse = InverseOperationsUtil.getInverse(operation);
		active = function;
	}
	
	/**
	 * Primjenjuje aktivnu funkciju na <b>value</b>
	 * @param value
	 * @return rezultat aktivne funkcije
	 */
	public double applyOperation(double value) {
		return this.active.apply(value);
	}
	
	/**
	 * Metoda osvjezava gumb ovisno o zastavici <b>inverseFlag</b>, ako je <code>true</code>
	 * gumbu aktivna funkcija postaje inverzna
	 * @param inverseFlag
	 */
	public void updateButton(boolean inverseFlag) {
		if(inverseFlag) { 
			this.setText(textInverse);
			this.active = inverse;
		}
		else {
			this.setText(text);
			this.active = function;
		}
	}

}
