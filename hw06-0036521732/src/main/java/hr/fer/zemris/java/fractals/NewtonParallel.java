package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.awt.*;

/**
 * Klasa crta fraktal dobiven primjenom Newton-Raphson iteracije na polinom koji 
 * korinik zada. Posao računanja iteracija se dijeli nad dretvama
 * @author Lovro Glogar
 *
 */
public class NewtonParallel {
	
	public static void main(String[] args) {
		
		int workers = 0;
		int tracks = 0;
		int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
		
		if(args.length == 0) { // nije zadan niti jedan argument
			workers = numberOfAvailableProcessors;
			tracks = 4 * numberOfAvailableProcessors;
		} else if(args.length == 1) { // zadan je argument oblika --workers=12
			String argument = args[0].substring(2, args[0].length());
			if(argument.startsWith("workers")) {
				workers = Integer.parseInt(argument.substring(argument.indexOf("=") + 1));
				tracks = 4 * numberOfAvailableProcessors;
			}
			else if(argument.startsWith("tracks")) {
				workers = numberOfAvailableProcessors;
				tracks = Integer.parseInt(argument.substring(argument.indexOf("=") + 1));
			}
			else throw new IllegalArgumentException("Unrecognized arguments 1");
		} else if(args.length == 2) { // zadana su 2 argumenta --workers i --tracks ili jedan argument oblika -w N
			if(args[0].startsWith("--")) {
				int t1, t2;
				if(args[0].startsWith("--workers=") && args[1].startsWith("--tracks=")) {
					t1 = 0;
					t2 = 1;
				}
				else if(args[1].startsWith("--workers=") && args[0].startsWith("--tracks=")){
					t1 = 1;
					t2 = 0;
				} else throw new IllegalArgumentException("Unrecognized argument 1.5");
				workers = Integer.parseInt(args[t1].substring(args[t1].indexOf("=") + 1));
				tracks = Integer.parseInt(args[t2].substring(args[t2].indexOf("=") + 1));
			} else {
				String arg0 = args[0].substring(1);
				if(arg0.equals("w")) {
					workers = Integer.parseInt(args[1]);
					tracks = 4 * numberOfAvailableProcessors;
				} else if(arg0.equals("t")) {
					workers = numberOfAvailableProcessors;
					tracks = Integer.parseInt(args[1]);
				} else 
					throw new IllegalArgumentException("Unrecognized arguments 2");
			}
		} else if(args.length == 3) { // zadanje jedan argument oblika -w N i jedan argument oblika --workers=N
			String arg0 = null;
			int i;
			for(i = 0; i < args.length; i++) {
				if(args[i].startsWith("--")) {
					arg0 = args[i];
					break;
				}
			}
			if(arg0 == null || i == 1) throw new IllegalArgumentException("Unrecognized arguments 3");
			if(arg0.startsWith("--workers=")) {
				workers = Integer.parseInt(arg0.substring(arg0.indexOf("=") + 1));
			} else if(arg0.startsWith("--tracks="))
				tracks = Integer.parseInt(arg0.substring(arg0.indexOf("=") + 1));
			else throw new IllegalArgumentException("Unrecognized arguments 4");
			
			String arg1;
			String arg1Value;
			
			if(i == 0) {
				arg1 = args[1];
				arg1Value = args[2];
			} else {
				arg1 = args[0];
				arg1Value = args[1];
			}
			
			if(arg1.equals("-w")) {
				if(arg0.startsWith("--workers"))
					throw new IllegalArgumentException("Cant be two same arguments 5");
				workers = Integer.parseInt(arg1Value);
			} else if(arg1.equals("-t")) {
				if(arg0.startsWith("--tracks"))
					throw new IllegalArgumentException("Cant be two same arguments 6");
				tracks = Integer.parseInt(arg1Value);
			} else throw new IllegalArgumentException("Unrecognized arguments 7");
		
			
		} else if(args.length == 4) { // zadana su 2 argumenta oblika -w N
			String arg0 = args[0].substring(1);
			String arg1 = args[2].substring(1);
			if((arg0.equals("w") && arg1.equals("t")) || (arg0.equals("t") && arg1.equals("w"))) {
				if(arg0.equals("w")) {
					workers = Integer.parseInt(args[1]);
					tracks = Integer.parseInt(args[3]);
				} else {
					workers = Integer.parseInt(args[3]);
					tracks = Integer.parseInt(args[1]);
				}
			} else
				throw new IllegalArgumentException("Unrecognize arguments 8");
		}
		
		ComplexRootedPolynomial crp = PolynomReader.getPolynomialFromUser();
		FractalViewer.show(new MojProducer(crp, workers, tracks));
	}
	
	/**
	 * Klasa predstvalja posao koji pojedina dretva radi
	 * @author Lovro Glogar
	 *
	 */
	private static class PosaoIzracuna implements Runnable{

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private ComplexRootedPolynomial crp;
		
		/**
		 * Konstruktor
		 */
		private PosaoIzracuna() {
		}
		
		/**
		 * Konstruktor
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param cancel
		 * @param crp
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial crp) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.crp = crp;
		}
		

		@Override
		public void run() {
			// Dretva racuna iteracije za redove od yMin do yMax
			ComplexPolynomial polynomial = crp.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			int offset = yMin * width;
			
			for(int y = yMin; y <= yMax; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					int iters = 0;
					Complex znold;
					do {
						znold = zn;
						zn = zn.sub(polynomial.apply(zn).divide(derived.apply(zn)));
						iters++;
					} while(iters < m && znold.sub(zn).module() > 1e-3);
					int closestIndex = this.crp.indexOfClosestRootFor(zn, 0.002);
					data[offset] = (short) (closestIndex + 1);
					offset++;
				}
			}
		}
		
	}
	
	/**
	 * Moj producer
	 * @author Lovro Glogar
	 *
	 */
	private static class MojProducer implements IFractalProducer{

		/**
		 * Polinom koji je korisnik zadao
		 */
		private ComplexRootedPolynomial crp;
		
		/**
		 * Zadani broj dretvi
		 */
		private int workers;
		
		/**
		 * Podjela na staze
		 */
		private int tracks;
		
		/**
		 * Konstruktor
		 * @param crp
		 * @param workers
		 * @param tracks
		 */
		public MojProducer(ComplexRootedPolynomial crp, int workers, int tracks) {
			this.crp = crp;
			this.workers = workers;
			this.tracks = tracks;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 100;
			short[] data = new short[width * height];
			if(tracks > height)
				tracks = height;
			int brojYPoTraci = height / tracks;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < tracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==tracks-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, crp);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(crp.toComplexPolynom().order() + 1), requestNo);
		}
		
	}
	
}
