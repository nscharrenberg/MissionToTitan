package org.um.dke.titan.utils.lander.chart;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ChartComponent extends JComponent{
	private double[] xVals, yVals;
	private double chartWidth, chartHeight;
	private double xStepSize, yStepSize;
	private double noXSteps, noYSteps;
	private double valXStepSize, valYStepSize;
	private final Color BACKGROUND, GRID, CURVE, AXIS;

	public ChartComponent(double[] xVals, double[] yVals, double chartWidth, double chartHeight, double xStepSize, double yStepSize) {
		super();
		setPreferredSize(new Dimension((int)chartWidth, (int)chartHeight));
		this.xVals = xVals;
		this.yVals = yVals;
		this.chartWidth = chartWidth;
		this.chartHeight = chartHeight;
		valXStepSize = xStepSize;
		valYStepSize = yStepSize;
		double maxX = max(xVals);
		double maxY = max(yVals);
		noXSteps = (double) (maxX / xStepSize) + 2;
		noYSteps = (double) (maxY / yStepSize) + 3;
		this.xStepSize = chartWidth / (noXSteps + 1);
		this.yStepSize = chartHeight / (noYSteps + 1);
		BACKGROUND = Color.white;
		GRID = Color.LIGHT_GRAY;
		CURVE = Color.red;
		AXIS = Color.black;
	}
	
	private double max(double[] vals) {
		double max = vals[0];
		for(double n : vals) {
			if(n > max) {
				max = n;
			}
		}
		return max;
	}
	private double min(double[] vals) {
		double min = vals[0];
		for(double n : vals) {
			if(n < min) {
				min = n;
			}
		}
		return min;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(BACKGROUND);
		g2.fillRect(0, 0, (int)chartWidth, (int)chartHeight);
		g2.setColor(GRID);
		int i;
		for(i = 1; i < noXSteps - 1; i++) {
			g2.drawLine((int)(xStepSize * (i + 1)), (int)(0 + yStepSize), (int)(xStepSize * (i + 1)), (int)(chartHeight - yStepSize));
		}
		for(i = 1; i < noYSteps - 1; i++) {
			g2.drawLine((int)(0 + xStepSize), (int)(chartHeight - yStepSize - yStepSize * i), (int)(chartWidth - xStepSize), (int)(chartHeight - yStepSize -yStepSize * i));
		}
		g2.setColor(AXIS);
        g2.setStroke(new BasicStroke(2));
		//y-axis
		g2.drawLine((int)(xStepSize), (int)(yStepSize), (int)(xStepSize), (int)(chartHeight - yStepSize));
		//x-axis
		g2.drawLine((int)(xStepSize), (int)(chartHeight - yStepSize), (int)(chartWidth - xStepSize), (int)(chartHeight - yStepSize));
		//x marks
		for(i = 0; i < noXSteps - 1; i++) {
			g2.drawLine((int)(xStepSize * (i + 1)), (int)(chartHeight - yStepSize - 8), (int)(xStepSize * (i + 1)), (int)(chartHeight - yStepSize + 7));
			g2.drawString(i*(int)(valXStepSize)+"", (int)(xStepSize * (i + 1) - 3), (int)(chartHeight - yStepSize + 18));
		}
		i = (int) (noXSteps - 1);
		g2.drawLine((int)(xStepSize * (i + 1)) - 7, (int)(chartHeight - yStepSize - 8), (int)(xStepSize * (i + 1)), (int)(chartHeight - yStepSize));
		g2.drawLine((int)(xStepSize * (i + 1)), (int)(chartHeight - yStepSize), (int)(xStepSize * (i + 1)) - 7, (int)(chartHeight - yStepSize + 7));
		//y marks
		for(i = 0; i < noYSteps - 1; i++) {
			g2.drawLine((int)(xStepSize - 8), (int)(chartHeight - yStepSize - yStepSize * i), (int)(xStepSize + 7), (int)(chartHeight - yStepSize -yStepSize * i));
			g2.drawString(i*(int)(valYStepSize)+"", (int)(xStepSize - 18), (int)(chartHeight - yStepSize - yStepSize * i));
		}
		i = (int) (noYSteps - 1);
		g2.drawLine((int)(xStepSize - 8), (int)(chartHeight - yStepSize - yStepSize * i) + 7, (int)(xStepSize), (int)(chartHeight - yStepSize -yStepSize * i));
		g2.drawLine((int)(xStepSize), (int)(chartHeight - yStepSize - yStepSize * i), (int)(xStepSize + 7), (int)(chartHeight - yStepSize -yStepSize * i) + 7);
		g.setColor(CURVE);
		double prevXVal = xVals[0]/valXStepSize, prevYVal = yVals[0]/valYStepSize;
		for(i = 1; i < xVals.length; i++) {
			double xVal = xVals[i]/valXStepSize, yVal = yVals[i]/valYStepSize;
			g2.drawLine((int)(prevXVal * xStepSize + xStepSize),  (int)(chartHeight - yStepSize*2 - prevYVal * yStepSize + yStepSize), (int)(xVal * xStepSize + xStepSize),  (int)(chartHeight - 2*yStepSize - yVal * yStepSize + yStepSize));
			//end
			prevXVal = xVal;
			prevYVal = yVal;
		}
	}
}
