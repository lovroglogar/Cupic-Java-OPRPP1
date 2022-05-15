package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Klasa modelira kompleksan broj u standardnom zapisu
 * @author Lovro Glogar
 *
 */
public class Complex {
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Da bi se 0 mogla lijepo prikazati
	 */
	public static final double GRANICA = 1e-10;
	
	/**
	 * Realni dio
	 */
	private double re;
	
	/**
	 * Imaginarni dio
	 */
	private double im;
	
	/**
	 * Konstruktor
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Konstruktor
	 * @param re realni dio
	 * @param im imaginarni dio
	 */
	public Complex(double re, double im) {
		if((re < GRANICA && re > 0) || (re > -GRANICA && re < 0))
			this.re = 0;
		else
			this.re = re;
		if((im < GRANICA && im > 0) || (im > -GRANICA && im < 0))
			this.im = 0;
		else
			this.im = im;
	}
	
	/**
	 * Vraca modul od this
	 * @return
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Vraca this * c
	 * @param c
	 * @return
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}
	
	/**
	 * Vraca this / c
	 * @param c
	 * @return
	 */
	public Complex divide(Complex c) {
		double newRe = (this.re * c.re + this.im * c.im) / (c.re * c.re + c.im * c.im);
		double newIm = (this.im * c.re - this.re * c.im) / (c.re * c.re + c.im * c.im);
		return new Complex( newRe, newIm);
	}

	/**
	 * Vraca this + c
	 * @param c
	 * @return
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Vraca this - c
	 * @param c
	 * @return
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	/**
	 * Vraca -this
	 * @return
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * 	returns this^n, n is non-negative integer
	 * @param n
	 * @return
	 */
	public Complex power(int n) {
		double theta = Math.atan(this.im / this.re);
		if((this.re < 0 && this.im > 0) || (this.re < 0 && this.im < 0))
			theta = Math.PI + theta;
		else if (this.re > 0 && this.im < 0)
			theta = 2 * Math.PI + theta;
		double rn = Math.pow(re * re + im * im, ((double) n) / 2);
		return new Complex(rn * Math.cos(n * theta), rn * Math.sin(n * theta));
	}
	/**
	 * returns n-th root of this, n is positive integer
	 * @param n-th root
	 * @return
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new ArrayList<>();
		double theta = Math.atan(this.im / this.re);
		if((this.re < 0 && this.im > 0) || (this.re > 0 && this.im < 0))
			theta = Math.PI + theta;
		double r = this.module();
		double tempR = Math.pow(r, 1/((double) n));
		for(int i = 0; i < n; i++) 
			list.add(new Complex(tempR * Math.cos((theta + 2 * Math.PI * i) / n), tempR * Math.sin((theta + 2 * Math.PI * i) / n)));
		return list;
	}
	
	@Override
	public String toString() {
		if(this.im >= 0)
			return "(" + this.re + " + i" + this.im + ")";
		else
			return "(" + this.re + " - i" + -1 * this.im + ")";
	}	
	
	/**
	 * Vraca realni dio
	 * @return realni dio
	 */
	public double getRe() {
		return re;
	}
	
	/**
	 * Vraca imaginarni dio
	 * @return imaginarni dio
	 */
	public double getIm() {
		return im;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Complex))
			return false;
		Complex c2 = (Complex) obj;
		return this.re == c2.re && this.im == c2.im;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.re, this.im);
	}
}
