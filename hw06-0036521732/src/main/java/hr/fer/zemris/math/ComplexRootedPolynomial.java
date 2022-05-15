package hr.fer.zemris.math;

/**
 * Klasa modelira polinom u faktoriziranom obliku
 * @author Lovro Glogar
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Konstanta z0
	 */
	private Complex constant;
	
	/**
	 * Korijeni polinoma
	 */
	private Complex[] roots;
	
	/**
	 * Konstruktor
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * computes polynomial value at given point z
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex temp = this.constant;
		for(Complex c : roots)
			temp = temp.multiply(z.sub(c));
		return temp;
	}
	
	/**
	 * converts this representation to ComplexPolynomial type
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		int n = roots.length;
		Complex[] factors = new Complex[n + 1];
		
		int i;
		for(i = 0; i < n; i++)
			factors[i] = Complex.ZERO;
		factors[n] = Complex.ONE;
		
		for(i = 0; i < n; i++) {
			for(int j = n - i - 1; j < n; j++) 
				factors[j] = factors[j].add(new Complex(-1, 0).multiply(roots[i]).multiply(factors[j + 1]));
		}
		
		for(i = 0; i < n + 1; i++) 
			factors[i] = factors[i].multiply(constant);
		
		return new ComplexPolynomial(factors);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		for(Complex c: roots) {
			sb.append("*(z - " + c.toString() + ")");
		}
		return sb.toString();
	}
	
	/**
	 * finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc 
	 * @param z
	 * @param treshold
	 * @return
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int closestIndex = -1;
		double closest = Double.MAX_VALUE;
		for(int i = 0; i < this.roots.length; i++) {
			double current = roots[i].sub(z).module();
			if(current < treshold && current < closest) {
				closest = current;
				closestIndex = i;
			}
		}
		
		return closestIndex;
	}
}
