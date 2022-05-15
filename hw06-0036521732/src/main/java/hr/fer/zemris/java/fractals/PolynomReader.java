package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class PolynomReader {
	
	public static ComplexRootedPolynomial getPolynomialFromUser() {
		Scanner scanner = new Scanner(System.in);
		
		String line;
		String[] splitLine;
		
		List<Complex> rootsAsList = new ArrayList<>();
		Complex newRoot;
		
		int i = 1;
		System.out.print("Root " + i + "> ");
		while(!(line = scanner.nextLine()).equals("done")) {
			splitLine = line.split(" ");
			int length = splitLine.length;
			
			if(length == 1) {
				if(splitLine[0].contains("i")) {
					double im = parseImaginaryPart(splitLine[0]);
					
					if(splitLine[0].startsWith("-"))
						im = -im;
					newRoot = new Complex(0, im);
				}
				else {
					double re = parseRealPart(splitLine[0]);
					newRoot = new Complex(re, 0);
				}
			} else {
				if(length != 3) {
					System.out.println("Illegal root format");
					System.out.print("Root " + i + "> ");
					continue;
				} else {
					double re = parseRealPart(splitLine[0]);
					double im = parseImaginaryPart(splitLine[2]);
					
					if(splitLine[1].equals("-"))
						im = -im;
					newRoot = new Complex(re, im);
					
				}
			}
			rootsAsList.add(newRoot);
			i++;
			System.out.print("Root " + i + "> ");
		}
		scanner.close();
		
		Complex[] roots = new Complex[rootsAsList.size()];
		return new ComplexRootedPolynomial(Complex.ONE, rootsAsList.toArray(roots));
	}
	
	private static double parseRealPart(String line) {
		double re;
		if(line.startsWith("-"))
			re = -Double.parseDouble(line.substring(1, line.length()));
		else
			re = Double.parseDouble(line);
		return re;
	}
	
	private static double parseImaginaryPart(String line) {
		double im;
		if(line.endsWith("i")) // 1 * i
			im = 1;
		 else 
			im = Double.parseDouble(line.substring(line.indexOf("i") + 1, line.length()));
		return im;
	}
}
