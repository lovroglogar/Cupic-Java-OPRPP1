package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.Function;

/**
 * Klasa nasljeduje {@code LayoutManager2} i definira layout za kalkulator
 * @author Lovro Glogar
 *
 */
public class CalcLayout implements LayoutManager2{
	
	/**
	 * Broj redaka
	 */
	private static final int ROWS = 5;
	
	/**
	 * Broj stupaca
	 */
	private static final int COLUMNS = 7;
		
	/**
	 * Komponente layout-a
	 */
	private Component[] components;
	
	/**
	 * Razmak izmedu komponenata layout-a
	 */
	private int interspace;
	
	/**
	 * Konstruktor sa <b>interspace</b> = 0
	 */
	public CalcLayout() {
		this(0);
	};
	
	/**
	 * Konstruktor
	 * @param interspace
	 */
	public CalcLayout(int interspace) {
		this.interspace = interspace;
		this.components = new Component[ ROWS * COLUMNS - 4];
	}
	

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if(comp == null)
			throw new NullPointerException();
		
		for(int i = 0; i < components.length; i++) {
			if(components[i].equals(comp)) {
				components[i] = null;
				return;
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return helper(parent, (t) -> t.getPreferredSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return helper(parent, t -> t.getMinimumSize());
	}
	
	/**
	 * Funkcija vraca ovisno o funkciji <b>f</b> minimalne ili preferirane dimenzije layout-a
	 * @param parent
	 * @param f
	 * @return minimalne ili prefereirane dimenzije layout-a ovisno o funkciji
	 */
	private Dimension helper(Container parent, Function<Component, Dimension> f) {
		Insets insets = parent.getInsets();
		
		// trazim prvu not null komponentu
		int i;
		for( i = 0; i < components.length; i++) {
			if(components[i] != null) 
				break;
		}
		//--------------------------------------------------------------------
		if(i == components.length) 
			return new Dimension(0, 0);
		
		int largestHeight = 0, largestWidth = 0;
		
		// Trazim najveci width i height medu komponentama
		for(; i < components.length; i++) {
			Component temp = components[i];
			if(temp != null) {
				Dimension dimTemp = f.apply(temp);
				if(dimTemp != null) {
					if (dimTemp.height >  largestHeight)
						largestHeight = dimTemp.height;
					if(dimTemp.width > largestWidth)
						largestWidth = dimTemp.width;
				}
			}
		}
		
		Dimension d = new Dimension(insets.left + insets.right + largestWidth * COLUMNS + (COLUMNS - 1) * this.interspace,
				insets.top + insets.bottom + largestHeight * ROWS + (ROWS - 1) * this.interspace);
		return d;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		
		int parentWidth = parent.getSize().width;
		int parentHeight = parent.getSize().height;
				
		int availableParentWidth = parentWidth - insets.left - insets.right - (COLUMNS - 1) * this.interspace;
		int availableParentHeight = parentHeight - insets.top - insets.bottom - (ROWS - 1) * this.interspace;
		
		int tempWidth = Math.round((float) availableParentWidth / COLUMNS);
		int tempHeight = Math.round((float) availableParentHeight / ROWS);
		
		int numTempWidth = tempWidth * COLUMNS - availableParentWidth;
		int numTempHeight = tempHeight * ROWS - availableParentHeight;
		
		
		
		int[] widths;
		int a;
		if(numTempWidth < 0) {
			numTempWidth = -numTempWidth;
			a = 1;
		} else a = -1;
		
		switch(numTempWidth) {
			case 1: widths = new int[] {tempWidth, tempWidth, tempWidth, tempWidth + a, tempWidth, tempWidth, tempWidth}; break;
			case 2: widths = new int[] {tempWidth, tempWidth, tempWidth + a, tempWidth, tempWidth + a, tempWidth, tempWidth}; break;
			case 3: widths = new int[] {tempWidth, tempWidth + a, tempWidth, tempWidth + a, tempWidth, tempWidth + a, tempWidth}; break;
			case 4: widths = new int[] {tempWidth + a, tempWidth, tempWidth + a, tempWidth, tempWidth + a, tempWidth, tempWidth + a}; break;
			case 5: widths = new int[] {tempWidth + a, tempWidth + a, tempWidth, tempWidth + a, tempWidth, tempWidth + a, tempWidth + a}; break;
			case 6: widths = new int[] {tempWidth + a, tempWidth + a, tempWidth + a, tempWidth, tempWidth + a, tempWidth + a, tempWidth + a}; break;
			default: widths = new int[] {tempWidth, tempWidth, tempWidth, tempWidth, tempWidth, tempWidth, tempWidth}; break;
		}
		
		if(numTempHeight < 0) {
			numTempHeight = -numTempHeight;
			a = 1;
		} else a = -1;
		
		int[] heights;
		switch(numTempHeight) {
			case 1: heights = new int[] {tempHeight, tempHeight, tempHeight + a, tempHeight, tempHeight}; break;
			case 2: heights = new int[] {tempHeight, tempHeight + a, tempHeight, tempHeight + a, tempHeight}; break;
			case 3: heights = new int[] {tempHeight + a, tempHeight, tempHeight + a, tempHeight, tempHeight + a}; break;
			case 4: heights = new int[] {tempHeight + a, tempHeight + a, tempHeight, tempHeight + a, tempHeight + a}; break;
			default: heights = new int[] {tempHeight, tempHeight, tempHeight, tempHeight, tempHeight}; break;
		}
		
		// 
		int i;
		int x, y, width = 0, height;
		x = insets.left;
		y = insets.top;
		for(i = 0; i < 5; i++) {
			width += widths[i];
		}
		width += this.interspace * 4;
		height = heights[0];
		if(this.components[0] != null)
			this.components[0].setBounds(x, y, width, height);
		
		Component comp;
		for(i = 1; i < COLUMNS - 4; i++) { // Prvi redak
			x = x + width + this.interspace;
			width = widths[i + 4];
			comp = this.components[i];
			if(comp != null)
				comp.setBounds(x, y, width, height);
		}
		
		for(i = 2; i <= ROWS; i++) {
			y = y + heights[i - 2] + this.interspace;
			x = insets.left;
			for(int j = 1; j <= COLUMNS; j++) {
				if(j != 1) x = x + width + this.interspace;
				width = widths[j - 1];
				int index = (i - 1) * COLUMNS + (j - 1) - 4;
				comp = components[index];
				if(comp != null)
					comp.setBounds(x, y, width, height);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null)
			throw new NullPointerException("Component or constraint can't be null");
		
		RCPosition position;
		if(constraints instanceof RCPosition)
			position = (RCPosition) constraints;
		else if(constraints instanceof String)
			position = RCPosition.parse((String) constraints);
		else throw new IllegalArgumentException();
		
		int row = position.getRow();
		int column = position.getColumn();
		
		if(row < 1 || column < 1 || row > ROWS || column > COLUMNS || 
				(row == 1 && column > 1 && column < 6))
			throw new CalcLayoutExcpetion("Row or column out of bounds");
		
		int index;
		if(row == 1) {
			if(column == 1)
				index = 0;
			else if(column == 6)
				index = 1;
			else index = 2;
		} else 
			index = (row - 1) * COLUMNS + (column - 1) - 4;

		if(this.components[index] != null)
			throw new CalcLayoutExcpetion("Component spot not available");

		this.components[index] = comp;
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return helper(target, t -> t.getMaximumSize());
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		
	}

}
