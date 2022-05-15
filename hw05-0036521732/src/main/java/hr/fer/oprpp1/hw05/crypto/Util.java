package hr.fer.oprpp1.hw05.crypto;


public class Util {
	
	/**
	 * Metoda pretvara dani heksadekadski zapis u byte zapis
	 * @param keyText heksadekadski zapis
	 * @return byte[]
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText == null)
			throw new NullPointerException();
		if(keyText.length() % 2 != 0)
			throw new IllegalArgumentException();
		
		byte[] byteArray = new byte[keyText.length() / 2];		
		char[] hexPair = new char[2];
		
		String[] binaryPair = new String[2];

		int i = 1;
		int k = 0;
		
		while(i < keyText.length()) {
			hexPair[0] = keyText.charAt(i - 1);
			hexPair[1] = keyText.charAt(i);
			
			binaryPair[0] = hexToBinaryString(hexPair[0]);
			binaryPair[1] = hexToBinaryString(hexPair[1]);
			
			String binary = binaryPair[0] + binaryPair[1];
			
			int number;
			if(binary.charAt(0) == '1') { // 2 komplement ako treba biti negativan
				number = -1 * binaryToDecimal(twoComplement(binary));
			}else
				number = binaryToDecimal(binary);
			
			byteArray[k] = (byte) number;
			k++;
			i+=2;
		}
		
		return byteArray;
	}
	
	/**
	 * Pomocna metoda pretvara heksadekadski broj od 0 do F u binarni zapis
	 * @param hex heksadekadski broj od 0 do F
	 * @return binari zapis
	 */
	private static String hexToBinaryString(char hex) {
		
		switch(Character.toLowerCase(hex)) {
			case '0': return "0000";
			case '1': return "0001";
			case '2': return "0010";
			case '3': return "0011";
			case '4': return "0100";
			case '5': return "0101";
			case '6': return "0110";
			case '7': return "0111";
			case '8': return "1000";
			case '9': return "1001";
			case 'a': return "1010";
			case 'b': return "1011";
			case 'c': return "1100";
			case 'd': return "1101";
			case 'e': return "1110";
			case 'f': return "1111";
			default: throw new IllegalArgumentException();
 		}
	}
	
	/**
	 * Pomocna metoda pretvara binarni broj u decimalni
	 * @param binary binarni broj
	 * @return decimalni broj
	 */
	private static Integer binaryToDecimal(String binary) {
		int numberOf0 = 0;
		for(int i = 0; i < binary.length(); i++) {
			if(binary.charAt(i) == '0')
				numberOf0++;
		}
		int result = (int) (Math.pow(2, Integer.parseInt(binary.substring(0, 1)) * 7)
							+  Math.pow(2, Integer.parseInt(binary.substring(1, 2)) * 6)
							+  Math.pow(2, Integer.parseInt(binary.substring(2, 3)) * 5)
							+  Math.pow(2, Integer.parseInt(binary.substring(3, 4)) * 4)
							+  Math.pow(2, Integer.parseInt(binary.substring(4, 5)) * 3)
							+  Math.pow(2, Integer.parseInt(binary.substring(5, 6)) * 2)
							+  Math.pow(2, Integer.parseInt(binary.substring(6, 7)) * 1)
							+  Math.pow(2, Integer.parseInt(binary.substring(7, 8)) * 0)) - numberOf0;
		
		return result;
	}

	/**
	 * Metoda pretvara byte zapis u heksadekadski zapis
	 * @param byteArray 
	 * @return heksadekadski zapis
	 */
	public static String bytetohex(byte[] byteArray) {
		if(byteArray == null)
			throw new NullPointerException();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < byteArray.length; i++) {
			String binary = null;
			
			int number = (int) byteArray[i];

			if(number < 0) {
				int abs = -1 * number;
				binary = twoComplement(decimalToBinary(abs));
			} else 
				binary = decimalToBinary(number);
			
			int n = binary.length();
			char hex1 = binaryStringToHexChar(binary.substring(0, n/2));
			char hex2 = binaryStringToHexChar(binary.substring(n/2, n));
			
			sb.append(hex1);
			sb.append(hex2);
		}
		
		return sb.toString();
	}
	
	private static String decimalToBinary(int i) {
		StringBuilder sb = new StringBuilder();
		
		while(i > 0) {
			sb.append(i%2);
			i /= 2;
		}
		
		while(sb.length() < 8)
			sb.append("0");
		sb.reverse();	
		return sb.toString();
	}
	/**
	 * Pomocna metoda prevara binarni broj od 0 do 15 u heksadekadski zapis
	 * @param binary
	 * @return heksadekadski zapis
	 */
	private static char binaryStringToHexChar(String binary) {
		switch(binary) {
			case "0000": return '0';
			case "0001": return '1';
			case "0010": return '2';
			case "0011": return '3';
			case "0100": return '4';
			case "0101": return '5';
			case "0110": return '6';
			case "0111": return '7';
			case "1000": return '8';
			case "1001": return '9';
			case "1010": return 'a';
			case "1011": return 'b';
			case "1100": return 'c';
			case "1101": return 'd';
			case "1110": return 'e';
			case "1111": return 'f';
			default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Pomocna metoda racuna 2 komplement binarnog broja
	 * @param binary
	 * @return 2 komplement predanog binarnog broja
	 */
	private static String twoComplement(String binary) {
		StringBuffer binaryBuffer = new StringBuffer(binary);
		int j;
		for(j = binaryBuffer.length() - 1; j >= 0; j--) {
			if(binaryBuffer.charAt(j) == '1')
				break;
		}
		
		if(j >= 0) {
			for(j = j - 1; j >= 0; j--) {
				if(binaryBuffer.charAt(j) == '1')
					binaryBuffer.replace(j, j+1, "0");
				else
					binaryBuffer.replace(j, j+1, "1");
			}
		}
		
		return binaryBuffer.toString();
	}
}
