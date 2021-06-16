package org.um.dke.titan.utils;

import javax.swing.*;
import java.awt.*;

public class ChartPanel4 extends JPanel{
	private ChartComponent4 c;

	public ChartPanel4(double[] xVals, double[] yVals, int chartWidth, int chartHeight, int xStepSize, int yStepSize) {
		super();
		setPreferredSize(new Dimension(chartWidth, chartHeight));
		c = new ChartComponent4(xVals, yVals, chartWidth, chartHeight, xStepSize, yStepSize);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		c.paintComponent(g);
	}
}
