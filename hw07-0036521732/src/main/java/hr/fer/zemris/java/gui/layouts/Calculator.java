package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
// unarne operacije 
/**
 * Klasa predstavlja kalkulator
 * @author Lovro Glogar
 *
 */
public class Calculator {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CalculatorFrame().setVisible(true);
		});
	}
	
	private static class CalculatorFrame extends JFrame{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Model s kojim radi kalkulator
		 */
		private CalcModelImpl model;
		
		/**
		 * Stog
		 */
		private Stack<Double> stack;
		
		/**
		 * Konstruktor
		 */
		public CalculatorFrame() {
			model = new CalcModelImpl();
			setSize(240, 140);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			stack = new Stack<>();
			initGUI();
			pack();
		}

		/**
		 * Inicijalizira GUI komponente
		 */
		private void initGUI() {
			
			// Display
			MyLabel display = new MyLabel(model.toString());
			model.addCalcValueListener(display.getListener());
			
			// Gumbi za znamenke
			ActionListener digitButtonListener = a -> {
				try {
					DigitButton digitButton = (DigitButton) a.getSource();
					model.insertDigit(digitButton.getDigit());
				} catch(CalculatorInputException e) {
					System.err.println(e.getMessage());
				}
			};
			List<DigitButton> digitButtons = new ArrayList<>();
			for(int i = 0; i < 10; i++) {
				DigitButton temp = new DigitButton(i);
				temp.addActionListener(digitButtonListener);
				digitButtons.add(temp);
			}
			
			// Unarne inverzne operacije
			List<UnaryInversableOperationButton> unaryButtons = new ArrayList<>();
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.RECIPROCAL_VALUE, "1/x", "1/x"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.SIN, "sin", "arcsin"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.LOG, "log", "10^x"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.COS, "cos", "arccos"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.LN, "ln", "e^x"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.TAN, "tan", "arctan"));
			unaryButtons.add(new UnaryInversableOperationButton(UnaryInversableOperation.CTG, "ctg", "arcctg"));
			
			ActionListener unaryInversableButtonListener = b -> {
				try {
					if(model.hasFrozenValue())
						throw new CalculatorInputException("Frozen value exists");
					UnaryInversableOperationButton button = (UnaryInversableOperationButton) b.getSource();
					double newValue;
					newValue = button.applyOperation(model.getValue());
					model.setValue(newValue);
				} catch(CalculatorInputException e) {
					System.err.println(e.getMessage());
				}
			};
			unaryButtons.forEach(b -> b.addActionListener(unaryInversableButtonListener));
			
			
			// Binarne operacije
			List<BinaryOperationButton> binaryButtons = new ArrayList<>();
			binaryButtons.add(new BinaryOperationButton(BinaryOperations.ADD, "+"));
			binaryButtons.add(new BinaryOperationButton(BinaryOperations.SUB, "-"));
			binaryButtons.add(new BinaryOperationButton(BinaryOperations.MUL, "*"));
			binaryButtons.add(new BinaryOperationButton(BinaryOperations.DIV, "/"));
			
			ExpButton expButton = new ExpButton();
			
			ActionListener binaryOperationButtonListener = b -> {
				try {
					if(model.hasFrozenValue())
						throw new CalculatorInputException("Frozen value exists");
					BinaryOperation button;
					if(b.getSource() instanceof BinaryOperationButton)
						button = (BinaryOperationButton) b.getSource();
					else
						button = (ExpButton) b.getSource();
					double value = model.getValue();
					if(model.getPendingBinaryOperation() == null) {
						model.freezeValue(String.valueOf(value));
						model.setActiveOperand(value);
					} else {
						double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), value);
						model.freezeValue(String.valueOf(newValue));
						model.setActiveOperand(newValue);
					}
					model.setPendingBinaryOperation(button.getOperation());
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			};
			binaryButtons.forEach(b -> b.addActionListener(binaryOperationButtonListener));
			expButton.addActionListener(binaryOperationButtonListener);
			
			// Gumb jednako
			MyButton equalsButton = new MyButton("=");
			
			equalsButton.addActionListener((a) -> {
				double value = model.getValue();
				if(model.getPendingBinaryOperation() == null)
					model.setValue(value);
				else {
					double operand = model.getActiveOperand();
					double newValue = model.getPendingBinaryOperation().applyAsDouble(operand, value);
					model.setValue(newValue);
					model.clearActiveOperand();
					model.setPendingBinaryOperation(null);
				}
			});
			
			
			ActionListener oneLineButtonListener = a -> {
				OneLineButton button = (OneLineButton) a.getSource();
				button.applyOperation(model);
			};
			//Gumb decimalne tocke
			OneLineButton decimalButton = new OneLineButton(OneLineOperations.DECIMAL, ".");
			decimalButton.addActionListener(oneLineButtonListener);
			
			// Gumb +/-
			OneLineButton signButton = new OneLineButton(OneLineOperations.SIGN, "+/-");
			signButton.addActionListener(oneLineButtonListener);
			
			// Gumb clr
			OneLineButton clrButton = new OneLineButton(OneLineOperations.CLR, "clr");
			clrButton.addActionListener(oneLineButtonListener);
			
			//Gumb reset
			OneLineButton resetButton = new OneLineButton(OneLineOperations.RESET, "reset");
			resetButton.addActionListener(oneLineButtonListener);
			
			//Push
			MyButton pushButton = new MyButton("push");
			pushButton.addActionListener(a -> {
				this.stack.push(model.getValue());
			});
			
			//Pop
			MyButton popButton = new MyButton("pop");
			popButton.addActionListener(a -> {
				if(this.stack.isEmpty())
					System.err.println("Stack is empty");
				else model.setValue(stack.pop());
			});
			
			// Inverse check box
			JCheckBox invCheckBox = new JCheckBox("Inv");
			
			invCheckBox.addActionListener(cb -> {
				JCheckBox box = (JCheckBox) cb.getSource();
				unaryButtons.forEach(b -> b.updateButton(box.isSelected()));
				expButton.updateButton(box.isSelected());
			});
			
			//-----------------------------------Unos komponenti-----------------------------------
			Container container = this.getContentPane();
			container.setLayout(new CalcLayout(5));
			container.add(display, "1, 1");
			
			container.add(digitButtons.get(0), "5, 3");
			container.add(digitButtons.get(1), "4, 3");
			container.add(digitButtons.get(2), "4, 4");
			container.add(digitButtons.get(3), "4, 5");
			container.add(digitButtons.get(4), "3, 3");
			container.add(digitButtons.get(5), "3, 4");
			container.add(digitButtons.get(6), "3, 5");
			container.add(digitButtons.get(7), "2, 3");
			container.add(digitButtons.get(8), "2, 4");
			container.add(digitButtons.get(9), "2, 5");
			
			container.add(invCheckBox, "5, 7");
			
			container.add(unaryButtons.get(0), "2, 1");
			container.add(unaryButtons.get(1), "2, 2");
			container.add(unaryButtons.get(2), "3, 1");
			container.add(unaryButtons.get(3), "3, 2");
			container.add(unaryButtons.get(4), "4, 1");
			container.add(unaryButtons.get(5), "4, 2");
			container.add(unaryButtons.get(6), "5, 2");
			
			container.add(binaryButtons.get(0), "5, 6");
			container.add(binaryButtons.get(1), "4, 6");
			container.add(binaryButtons.get(2), "3, 6");
			container.add(binaryButtons.get(3), "2, 6");
			container.add(expButton, "5, 1");
			
			container.add(equalsButton, "1, 6");
			container.add(decimalButton, "5, 5");
			container.add(signButton, "5, 4");
			container.add(clrButton, "1, 7");
			container.add(resetButton, "2, 7");
			container.add(pushButton, "3, 7");
			container.add(popButton, "4, 7");
		}
		
	}
	
}
