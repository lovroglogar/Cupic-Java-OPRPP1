package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Klasa koja modelira stupcasti dijagram
 * @author Lovro Glogar
 *
 */
public class BarChart {

	/**
	 * Lista vrijednosti
	 */
	private List<XYValue> values;
	
	/**
	 * Opis x osi
	 */
	private String xDescription;
	
	/**
	 * Opis y osi
	 */
	private String yDescription;
	
	/**
	 * Najmanja vrijednost na y osi
	 */
	private int minY;
	
	/**
	 * Najveca prikazana vrijednost na y osi
	 */
	private int maxY;
	
	/**
	 * Korak na y osi
	 */
	private int gap;
	
	/**
	 * Konstruktor
	 * @param values
	 * @param xDescrption
	 * @param yDescription
	 * @param minY
	 * @param maxY
	 * @param gap
	 */
	public BarChart(List<XYValue> values, String xDescrption, String yDescription, 
					int minY, int maxY, int gap) {
		if(minY < 0)
			throw new IllegalArgumentException("Y min can't be negative");
		if(maxY <= minY)
			throw new IllegalArgumentException("Y max must be greater than Y min");
		values.forEach(v -> {
			if(v.getY() < minY) throw new IllegalArgumentException("Element " + v + " has y value lower than Y min");
		});
		this.values = values;
		this.xDescription = xDescrption;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.gap = gap;
	}

	/**
	 * Vraca vrijednosti
	 * @return 
	 */
	public List<XYValue> getValues() {
		return values;
	}

	public String getxDescription() {
		return xDescription;
	}

	public String getyDescription() {
		return yDescription;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getGap() {
		return gap;
	}
	
}
