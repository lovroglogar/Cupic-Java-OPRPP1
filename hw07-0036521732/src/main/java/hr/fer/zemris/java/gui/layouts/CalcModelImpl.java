package hr.fer.zemris.java.gui.layouts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementacija sucelja {@link CalcModel}
 * @author Lovro Glogar
 *
 */
public class CalcModelImpl implements CalcModel{
	
	/**
	 * Govori je li vrijednost kalkulatora izmjenjiva
	 */
	private boolean editable;
	
	/**
	 * True ako je negativan, false ako je pozitivan
	 */
	private boolean sign;
	
	/**
	 * Vrijednost kao String
	 */
	private String stringValue;
	
	/**
	 * Vrijednost kao double
	 */
	private double doubleValue;
	
	/**
	 * Zamrznuta vrijednost
	 */
	private String freezeValue;
	
	/**
	 * Aktivni operand
	 */
	private Double activeOperand;
	
	/**
	 * Zakazana operacija
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * Slusaci
	 */
	private List<CalcValueListener> listeners;
	
	/**
	 * Konstruktor
	 */
	public CalcModelImpl() {
		this.editable = true;
		this.sign = false;
		this.stringValue = "";
		this.doubleValue = 0;
		this.freezeValue = null;
		this.activeOperand = null;
		this.pendingOperation = null;
		this.listeners = new ArrayList<>();
	}
	

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null)
			throw new NullPointerException("Listener is null");
		this.listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		this.listeners.remove(l);
	}
	
	@Override
	public String toString() {
		if(this.freezeValue != null)
			return this.freezeValue;
		else {
			if(this.doubleValue == 0) 
				return String.format("%s0", this.sign ? "-" : "");
			else {
				if(this.stringValue.startsWith("0") && this.stringValue.charAt(1) != '.')
					this.stringValue = this.stringValue.substring(1);
				if(!this.sign)
					return this.stringValue;
				else return "-" + this.stringValue;
			}
		}
	}

	@Override
	public double getValue() {
		return this.doubleValue;
	}

	@Override
	public void setValue(double value) {
		this.doubleValue = value;
		this.freezeValue = null;
		if(value == Double.POSITIVE_INFINITY)
			this.stringValue = "Infinity";
		else if(value == Double.NEGATIVE_INFINITY)
			this.stringValue = "-Infinity";
		else if (value == Double.NaN)
			this.stringValue = "NaN";
		else if (value == 0)
			this.stringValue = "";
		else
			this.stringValue = String.valueOf(value);
		this.editable = false;
		this.doubleValue = value;
		notifyListeners();
	}


	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		this.stringValue = "";
		this.doubleValue = 0;
		this.editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		this.clear();
		this.clearActiveOperand();
		this.pendingOperation = null;
		this.freezeValue = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!this.isEditable())
			throw new CalculatorInputException("Can't swap because editable isn't true");
		this.freezeValue = null;
		this.doubleValue = -this.doubleValue;
		this.sign = !this.sign;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!this.isEditable())
			throw new CalculatorInputException("Can't swap because editable isn't true");
		this.freezeValue = null;
		
		if(this.stringValue.contains("."))
			throw new CalculatorInputException("Decimal point already exists");
		
		if(this.stringValue.isEmpty())
			throw new CalculatorInputException("Can't insert decimal point on empty number");
		
		tryToSetDoubleValue(stringValue);
		this.stringValue = this.stringValue + ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!this.editable)
			throw new CalculatorInputException("Not editable");
		this.freezeValue = null;
		
		if(this.stringValue.equals("0") && digit == 0)
			return;
		
		String temp = this.stringValue + String.valueOf(digit);
		
		tryToSetDoubleValue(temp);
		this.stringValue = temp;
		notifyListeners();
	}
	
	/**
	 * Pomocna funkcija pokusava postaviti <b>doubleValue</b> na vrijednost parametra <b>stringValue</b>
	 * @param stringValue
	 */
	private void tryToSetDoubleValue(String stringValue) {
		double value;
		try {
			value = Double.parseDouble(stringValue);
			if(value > Double.MAX_VALUE)
				throw new CalculatorInputException();
		} catch(Exception e) {
			throw new CalculatorInputException("Problem parsing " + stringValue + " into double");
		}
		this.doubleValue = value;
		if(this.sign)
			this.doubleValue *= -1;
	}

	@Override
	public boolean isActiveOperandSet() {
		return this.activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(this.activeOperand == null)
			throw new IllegalStateException("Active operand isn't set");
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;	
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		this.editable = true;
	}
	
	/**
	 * Zamrzava vrijednost
	 * @param value
	 */
	public void freezeValue(String value) {
		this.freezeValue = value;
		this.doubleValue = 0;
		this.stringValue = "";
		notifyListeners();
	}
	
	/**
	 * {@code true} ako ima zamrznutu vrijednost
	 * @return
	 */
	public boolean hasFrozenValue() {
		return this.freezeValue != null;
	}
	
	/**
	 * Metoda javalja svim slusateljima da je vrijednost promijenjena
	 */
	private void notifyListeners() {
		this.listeners.forEach(l -> l.valueChanged(this));	
	}

}
