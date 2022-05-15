package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Klasa nasljeduje {@link JFrame}.
 * Prikazuje dijagram zadan datotekom
 * @author Lovro Glogar
 *
 */
public class BarChartDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Dijagram koji se crta
	 */
	private BarChart barChart;
	
	/**
	 * Put do datoteke opisa dijagrama
	 */
	private String pathName;
	
	/**
	 * Konstruktor
	 * @param barChart
	 * @param pathName
	 */
	public BarChartDemo(BarChart barChart, String pathName) {
		this.barChart = barChart;
		this.pathName = pathName;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setLocation(20, 20);
		setSize(900, 700);
		initGUI();
	}
	
	/**
	 * Inicijalizira GUI
	 */
	private void initGUI() {
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		// Labela sa putom do datoteke
		JLabel label = new JLabel(pathName);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(label, BorderLayout.BEFORE_FIRST_LINE);
		
		// Komponena stupcastog dijagrama
		BarChartComponent barChartComponent = new BarChartComponent(barChart);
		container.add(barChartComponent, BorderLayout.CENTER);
	}



	public static void main(String[] args) {
		try {
			if(args.length != 1)
				throw new IllegalArgumentException("Expected one argument: path to file");
			String pathName = args[0];
			
			Path p = Paths.get(pathName);
			
			List<XYValue> values = new ArrayList<>();
			String xDescription, yDescription;
			int yMin, yMax, gap;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(p), "UTF-8"));
			String line;
			
			xDescription = reader.readLine();
			yDescription = reader.readLine();
			
			line = reader.readLine();
			int index = 0;
			while(index < line.length()) {
				StringBuilder sb = new StringBuilder();
				while(index < line.length() && !Character.isWhitespace(line.charAt(index))) {
					sb.append(line.charAt(index));
					index++;
				}
				String[] temp = sb.toString().split(",");
				if(temp.length != 2)
					throw new Exception(sb.toString() + " is not good");
				int x = Integer.parseInt(temp[0]);
				int y = Integer.parseInt(temp[1]);
				values.add(new XYValue(x, y));
				while(index < line.length() && Character.isWhitespace(line.charAt(index)))
					index++;
			}
			
			yMin = Integer.parseInt(reader.readLine());
			yMax = Integer.parseInt(reader.readLine());
			gap = Integer.parseInt(reader.readLine());
			
			reader.close();
			
			BarChart barChart = new BarChart(values, xDescription, yDescription, yMin, yMax, gap);
			
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(barChart, pathName).setVisible(true);
			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
