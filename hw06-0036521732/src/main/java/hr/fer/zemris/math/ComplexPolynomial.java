package hr.fer.zemris.math;

/**
 * Klasa modelira polinom s kompleksim koeficijentima
 * @author Lovro Glogar
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Faktori polinoma
	 */
	private Complex[] factors;
	
	/**
	 * Konstruktor
	 * @param factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	/**
	 * returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * computes a new polynomial this*p
	 * @param p
	 * @return
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		
		for(int i = 0; i < newFactors.length; i++)
			newFactors[i] = Complex.ZERO;
		for(int i = 0; i < this.order() + 1; i++) {
			for(int j = 0; j < p.order() + 1; j++) 
				newFactors[i + j] = newFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
		}
		return new ComplexPolynomial(newFactors);
		
	}
	
	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return
	 */ 
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.factors.length - 1];
		for(int i = 1; i < this.factors.length; i++) {
			newFactors[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * computes polynomial value at given point z
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex temp = factors[0];
		for(int i = 1; i < factors.length; i++)
			temp = temp.add(factors[i].multiply(z.power(i)));
		return temp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = this.factors.length - 1; i > 0; i--)
			sb.append(factors[i].toString() + "*z^" + i + " + ");
		sb.append(factors[0].toString());
		return sb.toString();
	}

}
