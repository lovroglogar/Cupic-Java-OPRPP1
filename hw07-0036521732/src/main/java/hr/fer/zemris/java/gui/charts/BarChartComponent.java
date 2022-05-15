package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;

/**
 * Komponenta modelira stupcasti dijagram. Nasljeduje {@link JComponent}.
 * @author Lovro Glogar
 *
 */
public class BarChartComponent extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Udaljenost od lijevog ruba ili dna do opisa osi
	 */
	private static final int DISTANCE_TO_DESCRIPTION = 25;
	
	/**
	 * Udaljenost od opisa osi do vrijednosti osi
	 */
	private static final int DISTANCE_DESCRIPTION_VALUES = 20;
	
	/**
	 * Udaljenost od vrijednosti x osi do x osi
	 */
	private static final int DISTANCE_VALUES_X_AXIS = 30;
	
	/**
	 * Udaljenost od vrijednosti y osi do y osi
	 */
	private final int DISTANCE_VALUES_Y_AXIS;
	
	/**
	 * Udaljenost od kraja x osi do desnog ruba
	 */
	private static final int DISTANCE_X_END = 50;
	
	/**
	 * Udaljenost od kraja y osi do gornjeg ruba
	 */
	private static final int DISTANCE_Y_END = 50;
	
	/**
	 * Definira svojstvo stijelice na kraju x i y osi
	 */
	private static final int ARROW_CONSTANT = 10;
	
	/**
	 * Dijagram
	 */
	private BarChart chart;
	
	/**
	 * Konstruktor
	 * @param chart
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		int mostDigits = String.valueOf(chart.getMaxY()).length();
		if(mostDigits > 5)
			this.DISTANCE_VALUES_Y_AXIS = 25 + (int) Math.pow(1.54, mostDigits);
		else
			this.DISTANCE_VALUES_Y_AXIS = 25 + (int) Math.pow(1.88, mostDigits);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets insets = getInsets();
		Dimension dimension = getSize();
		List<XYValue> values = chart.getValues();
				
		Graphics2D g2d = (Graphics2D) g;
		
		int numberOfYLabels = 0;
		int yMin = chart.getMinY();
		int yMax = chart.getMaxY();
		int gap = chart.getGap();
		
		if((yMax - yMin) % gap != 0) {
			while((yMax - yMin) % gap != 0)
				yMax++;
		} 
		
		numberOfYLabels = (yMax - yMin) / gap + 1;
		
		// Y os
		int yAxisLength = dimension.height - insets.bottom - DISTANCE_TO_DESCRIPTION - DISTANCE_DESCRIPTION_VALUES
							- DISTANCE_VALUES_X_AXIS - DISTANCE_Y_END - insets.top;
		int yAxisX = insets.left + DISTANCE_TO_DESCRIPTION + DISTANCE_DESCRIPTION_VALUES + DISTANCE_VALUES_Y_AXIS;
		int yAxisYEnd = insets.top + DISTANCE_Y_END ;
		int yAxisYStart = yAxisYEnd + yAxisLength;
		
		// labele na y osi
		int gapBetweenLabelsYAxis = yAxisLength / numberOfYLabels;
		int i;
		g2d.setFont(getFont().deriveFont(15f));
		for(i = 0; i < numberOfYLabels; i++) {
			g2d.drawString(String.valueOf(yMin + i * gap), 
							insets.left + DISTANCE_TO_DESCRIPTION + DISTANCE_DESCRIPTION_VALUES, 
							yAxisYStart - i * gapBetweenLabelsYAxis + 5);
		}
		
		// cratenje y osi
		g2d.drawLine(yAxisX, yAxisYStart + 20, yAxisX, yAxisYEnd);
		g2d.drawLine(yAxisX, yAxisYEnd, yAxisX - ARROW_CONSTANT / 2, yAxisYEnd + ARROW_CONSTANT);
		g2d.drawLine(yAxisX, yAxisYEnd, yAxisX + ARROW_CONSTANT / 2, yAxisYEnd + ARROW_CONSTANT);
		
		
		//X os
		int xAxisLength = dimension.width - (insets.left + DISTANCE_TO_DESCRIPTION + DISTANCE_DESCRIPTION_VALUES + 
							DISTANCE_VALUES_Y_AXIS + DISTANCE_X_END + insets.right);
		int xAxisXEnd = dimension.width - (insets.right + DISTANCE_X_END) + 25;
		int xAxisXStart = yAxisX;
		int xAxisY = yAxisYStart;
		
		// labele na x osi
		int numberOfXLabels = chart.getValues().size();
		int gapBetweenLabelsXAxis = xAxisLength / numberOfXLabels;
		int xLabelsXStart = xAxisXStart + gapBetweenLabelsXAxis / 2;
		for(i = 0; i < numberOfXLabels; i++) {
		g2d.drawString(String.valueOf(values.get(i).getX()), 
						xLabelsXStart + i * gapBetweenLabelsXAxis,
						dimension.height - (insets.bottom + DISTANCE_TO_DESCRIPTION + DISTANCE_DESCRIPTION_VALUES));
		
		}
		
		// cratenje x osi
		g2d.drawLine(xAxisXStart - 10, xAxisY, xAxisXEnd, xAxisY);
		g2d.drawLine(xAxisXEnd, xAxisY, xAxisXEnd - ARROW_CONSTANT , xAxisY + ARROW_CONSTANT / 2);
		g2d.drawLine(xAxisXEnd, xAxisY, xAxisXEnd - ARROW_CONSTANT, xAxisY - ARROW_CONSTANT / 2);
		
		AffineTransform old = g2d.getTransform();
		AffineTransform at = new AffineTransform(old);
		at.rotate( -Math.PI / 2);
		g2d.setTransform(at);
		
		// Opis y osi
		g2d.setFont(getFont().deriveFont(25f));
		g2d.drawString(chart.getyDescription(), -(int) (yAxisYEnd + yAxisLength * 0.6), insets.left + DISTANCE_TO_DESCRIPTION);
		
		g2d.setTransform(old);
		// Opis X osi
		g2d.setFont(getFont().deriveFont(25f));
		g2d.drawString(chart.getxDescription(), yAxisX + xAxisLength / 2 - 130, dimension.height - (insets.bottom + DISTANCE_TO_DESCRIPTION));
		
		
		Color linesColor = new Color(255, 200, 0);
		
		// Pomocne linije y = n
		for(i = 1; i < numberOfYLabels; i++) {
			g2d.setColor(Color.GRAY);
			g2d.drawLine( xAxisXStart - 5,
					yAxisYStart - i * gapBetweenLabelsYAxis,
					xAxisXStart,
					yAxisYStart - i * gapBetweenLabelsYAxis);
			g2d.setColor(linesColor);
			g2d.drawLine( xAxisXStart,
					yAxisYStart - i * gapBetweenLabelsYAxis,
					xAxisXEnd - 25,
					yAxisYStart - i * gapBetweenLabelsYAxis);
		}
		
		// Pomocne linije x = n
		for(i = 1; i <= numberOfXLabels; i++) {
			g2d.setColor(Color.GRAY);
			
			g2d.drawLine( xAxisXStart + i * gapBetweenLabelsXAxis,
					yAxisYStart + 7,
					xAxisXStart + i * gapBetweenLabelsXAxis,
					yAxisYStart);
			g2d.setColor(linesColor);
			g2d.drawLine( xAxisXStart + i * gapBetweenLabelsXAxis,
					yAxisYStart,
					xAxisXStart + i * gapBetweenLabelsXAxis,
					yAxisYEnd + 35);
		}
		
		// Crtanje stupaca
		Color rectColor = new Color(255, 115, 0);
		g2d.setColor(rectColor);
		for(i = 0; i < values.size(); i++) {
			XYValue value = values.get(i);
			int height = (value.getY() - yMin) / gap * gapBetweenLabelsYAxis;
			if(value.getY() % gap != 0 && value.getY() < yMax) {
				int temp = value.getY() % gap;
				height = height + (int) ((double) temp / gap * gapBetweenLabelsYAxis);
			}
			g2d.fillRect(yAxisX + i * gapBetweenLabelsXAxis + 3,
							yAxisYStart - height,
							gapBetweenLabelsXAxis - 5,
							height);
		}
		
	}

	public BarChart getChart() {
		return chart;
	}
	
}
