package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Klasa omogucuje kriptiranje/dekriptiranje datoteka
 * @author lovro
 *
 */
public class Crypto {
	
	public static void main(String[] args) {
		
		try(Scanner scanner = new Scanner(System.in)) {
			String command = args[0];
			InputStream is;
			OutputStream os;
			String line;
			boolean encrypt;
	
			if(command.equals("checksha")) {
				String file = args[1];
				Path p = Paths.get(file);
				is = new BufferedInputStream(Files.newInputStream(p));
				byte[] b;
				
				System.out.println("Please provide expected sha-256 digest for " + file);
				line = scanner.nextLine();
				
				MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
				
				b = new byte[1024];
				int lenRead;
				
				while((lenRead = is.read(b)) > 0)
					messageDigest.update(b, 0, lenRead);
				
				String expectedDigest = Util.bytetohex(messageDigest.digest());
				
				if(line.equals(expectedDigest))
					System.out.printf("Digesting completed. Digest of %s matches expected digest.\n" , file);
				else
					System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest\r\n"
							+ "was: %s\n" , file, expectedDigest);
				
				is.close();
			} else if((encrypt = command.equals("encrypt")) || command.equals("decrypt")) {
				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				String key = scanner.nextLine();
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
				String iv = scanner.nextLine();
				
				SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(key), "AES");
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(iv));
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);		
				
				String file1 = args[1];
				String file2 = args[2];
				
				Path p1 = Paths.get(file1);
				Path p2 = Paths.get(file2);
				is = new BufferedInputStream(Files.newInputStream(p1));
				os = new BufferedOutputStream(Files.newOutputStream(p2));
				
				byte[] b1 = new byte[1024];
				int temp;
				int len = 0;
				
				while((temp = is.read(b1)) != -1) {
					os.write(cipher.update(b1));
					len = temp;
				}
				
				os.write(cipher.doFinal(b1, 0, len));
				
				is.close();
				os.close();
			} else
				throw new IllegalArgumentException();
			
		} catch(IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
}
