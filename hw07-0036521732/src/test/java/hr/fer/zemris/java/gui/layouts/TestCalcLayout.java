package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class TestCalcLayout {
	
	
	@Test
	public void testIllegalConstraintOutOfBounds() {
		DemoFrame1 frame = new DemoFrame1();
		frame.setLayout(new CalcLayout());
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "-1, 1");
		});
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "1, -1");
		});
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "6, 1");
		});
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "5, 8");
		});
	}

	@Test
	public void testIllegalConstraintIllegalForFirstRow() {
		DemoFrame1 frame = new DemoFrame1();
		frame.setLayout(new CalcLayout());
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "1, 2");
		});
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "1, 5");
		});
	}
	
	@Test
	public void testIllegalConstraintComponentInTakenSpot() {
		DemoFrame1 frame = new DemoFrame1();
		frame.setLayout(new CalcLayout());
		assertThrows(CalcLayoutExcpetion.class, () -> {
			frame.getContentPane().add(new JLabel(), "1, 1");
			frame.getContentPane().add(new JLabel(), "1, 1");
		});
		
	}
	private static class DemoFrame1 extends JFrame {
		public DemoFrame1() {
			setSize(240, 140);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
		}
	}
	
}
