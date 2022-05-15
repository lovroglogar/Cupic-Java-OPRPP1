package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

public class XYValue {
	
	private int x;
	
	private int y;
	
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof XYValue))
			return false;
		XYValue v2 = (XYValue) obj;
		return this.x == v2.x && this.y == v2.y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
