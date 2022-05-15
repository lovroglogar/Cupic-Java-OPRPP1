package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestComplex {
	
	@Test
	public void testToString() {
		assertEquals("2.0 + i4.0", new Complex(2, 4).toString());
	}

	@Test
	public void testModule() {
		assertEquals(5.0, new Complex(3, 4).module());
	}
	@Test
	public void testMultiply() {
		assertEquals("11.0 + i2.0", new Complex(1, 2).multiply(new Complex(3, -4)).toString());
	}
	@Test
	public void testRoot() {
		assertEquals("11.0 + i2.0", new Complex(-8, 8 * Math.sqrt(3)).root(4).toString());
	}
	
}
