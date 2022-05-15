package hr.fer.zemris.java.gui.layouts;

import java.util.function.DoubleFunction;

/**
 * Pomoca klasa
 * @author Lovro Glogar
 *
 */
public class InverseOperationsUtil {

	/**
	 * Staticka metoda za zadanu unarnu operaciju vraca njenu funkciju
	 * @param operation
	 * @return funkcija
	 */
	public static DoubleFunction<Double> getFunction(UnaryInversableOperation operation){
		switch(operation) {
			case RECIPROCAL_VALUE: return d -> 1 / d;
			case SIN: return d -> Math.sin(d);
			case COS: return d -> Math.cos(d);
			case CTG: return d -> 1 / Math.tan(d);
			case LN: return d -> Math.log(d);
			case LOG: return d -> Math.log10(d);
			case TAN: return d -> Math.tan(d);
		}
		return null;
	}
	
	/**
	 * Staticka metoda za zadanu unarnu operaciju vraca njenu inverznu funkciju
	 * @param operation
	 * @return
	 */
	public static DoubleFunction<Double> getInverse(UnaryInversableOperation operation){
		switch(operation) {
			case RECIPROCAL_VALUE: return d -> 1 / d;
			case SIN: return d -> Math.asin(d);
			case COS: return d -> Math.acos(d);
			case CTG: return d -> 1 / Math.atan(d);
			case LN: return d -> Math.pow(Math.E, d);
			case LOG: return d -> Math.pow(10, d);
			case TAN: return d -> Math.atan(d);
		}
		return null;
	}
	
}
