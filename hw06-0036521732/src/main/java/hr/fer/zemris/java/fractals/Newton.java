package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Klasa crta fraktal dobiven primjenom Newton-Raphson iteracije na polinom koji korinik zada koje računa iterativno
 * @author Lovro Glogar
 *
 */
public class Newton {
	
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = PolynomReader.getPolynomialFromUser();
		FractalViewer.show(new MojProducer(crp));		
	}
	
	/**
	 * Klasa implementira IFractalProducer sučelje
	 * @author Lovro Glogar
	 *
	 */
	private static class MojProducer implements IFractalProducer{
		
		/**
		 * Polinom koji je korisnik zadao
		 */
		private ComplexRootedPolynomial crp;
		
		/**
		 * Konstruktor
		 * @param crp
		 */
		public MojProducer(ComplexRootedPolynomial crp) {
			super();
			this.crp = crp;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 100; // broj iteracija koliko pustamo max
			int offset = 0;
			short[] data = new short[width * height];
			
			ComplexPolynomial polynomial = this.crp.toComplexPolynom(); // zadani polinom u raspisanom obliku
			ComplexPolynomial derived = polynomial.derive(); // derivacija zadanog polinoma
			
			final double rootDistanceThreshold = 0.002;
			final double convergenceThreshold = 1e-3;
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim); // pocetni kompleksni broj u trenutnoj iteraciji
					
					int iters = 0;
					Complex znold;
					do {
						znold = zn;
						zn = zn.sub(polynomial.apply(zn).divide(derived.apply(zn))); // zn - (f(zn)/f'(zn))
						iters++;
					} while(iters < m && znold.sub(zn).module() > convergenceThreshold);
					int closestIndex = this.crp.indexOfClosestRootFor(zn, rootDistanceThreshold);
					data[offset] = (short) (closestIndex + 1);
					offset++;
				}
			}
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
	
	
}
