package org.um.dke.titan.utils;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ChartPanel extends JPanel{
	private ChartComponent c;
	
	public ChartPanel(double[] xVals, double[] yVals, int chartWidth, int chartHeight, int xStepSize, int yStepSize) {
		super();
		setPreferredSize(new Dimension(chartWidth, chartHeight));
		c = new ChartComponent(xVals, yVals, chartWidth, chartHeight, xStepSize, yStepSize);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		c.paintComponent(g);
	}
}
