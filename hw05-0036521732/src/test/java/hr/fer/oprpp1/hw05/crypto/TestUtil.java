package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUtil {
	@Test
	public void testHexToByte() {
		assertArrayEquals(new byte[] { 1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testByteToHex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] { 1, -82, 34}));
	}
}
