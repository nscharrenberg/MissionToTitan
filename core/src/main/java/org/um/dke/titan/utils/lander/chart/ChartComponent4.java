package org.um.dke.titan.utils.lander.chart;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ChartComponent4 extends JComponent{
	private double[] xVals, yVals;
	private double chartWidth, chartHeight;
	private double xStepSize, yStepSize;
	private double noXSteps, noYSteps;
	private double valXStepSize, valYStepSize;
	private final Color BACKGROUND, GRID, CURVE, AXIS;
	private int xOffset, yOffset;
	private boolean gridOn;

	public ChartComponent4(double[] xVals, double[] yVals, double chartWidth, double chartHeight, double xStepSize, double yStepSize) {
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
		this.xOffset = (int) (chartWidth/2);
		this.yOffset = (int) (chartHeight/2);
		this.xStepSize = xOffset / (noXSteps + 1);
		this.yStepSize = yOffset / (noYSteps + 1);
		BACKGROUND = Color.white;
		GRID = Color.LIGHT_GRAY;
		CURVE = Color.red;
		AXIS = Color.black;
		gridOn = true;
	}
	
	private double max (double[] vals) {
		double minv = abs(minV(vals));
		double maxv = maxV(vals);
		return maxv < minv ? minv : maxv;
	}
	private double abs(double d) {
		return d < 0 ? -1*d : d;
	}
	private  double maxV(double[] vals) {
		double max = vals[0];
		for(double n : vals) {
			if(n > max) {
				max = n;
			}
		}
		return max;
	}
	private  double minV(double[] vals) {
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
		if(gridOn) {
			paintGrid(g2);
		}
		int i;
        g2.setStroke(new BasicStroke(2));
        paintAxis(g2);
		g.setColor(CURVE);
		double prevXVal = xVals[0]/valXStepSize, prevYVal = yVals[0]/valYStepSize;
		for(i = 1; i < xVals.length; i++) {
			double xVal = xVals[i]/valXStepSize, yVal = yVals[i]/valYStepSize;
			g2.drawLine((int)(prevXVal * xStepSize + xStepSize) + xOffset,  (int)(chartHeight - yStepSize*2 - prevYVal * yStepSize + yStepSize) - yOffset, (int)(xVal * xStepSize + xStepSize) + xOffset,  (int)(chartHeight - 2*yStepSize - yVal * yStepSize + yStepSize) - yOffset);
			//end
			prevXVal = xVal;
			prevYVal = yVal;
		}
	}
	
	public void paintGrid(Graphics2D g2) {
		int i;
		g2.setColor(GRID);
		//y
		for(i = (int)(-1*(noXSteps - 1)); i < noXSteps - 1; i++) {
			g2.drawLine((int)(xStepSize * (i + 1)) + xOffset, (int)(yStepSize), (int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - 2*yStepSize));
		}
		//x
		for(i = (int)(-1*(noYSteps - 1)); i < noYSteps - 1; i++) {
			g2.drawLine((int)(2*xStepSize), (int)(chartHeight - yStepSize - yStepSize * i) - yOffset, (int)(chartWidth - xStepSize), (int)(chartHeight - yStepSize -yStepSize * i) - yOffset);
		}
	}
	
	public void paintAxis(Graphics2D g2) {
		int i;
		g2.setColor(AXIS);
		//y-axis
		g2.drawLine((int)(xStepSize) + xOffset, (int)(yStepSize), (int)(xStepSize) + xOffset, (int)(chartHeight - 2*yStepSize));
		//x-axis
		g2.drawLine((int)(2*xStepSize), (int)(chartHeight - yStepSize) - yOffset, (int)(chartWidth - xStepSize), (int)(chartHeight - yStepSize) - yOffset);
		//x marks
		for(i = (int) (-1*(noXSteps - 1)); i < noXSteps - 1; i++) {
			g2.drawLine((int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize - 8) - yOffset, (int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize + 7) - yOffset);
			g2.drawString(i*(int)(valXStepSize)+"", (int)(xStepSize * (i + 1) - 3) + xOffset, (int)(chartHeight - yStepSize + 18)- yOffset);
		}
		//arrow right
		i = (int) (noXSteps - 1);
		g2.drawLine((int)(xStepSize * (i + 1) - 7) + xOffset, (int)(chartHeight - yStepSize - 8) - yOffset, (int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize) - yOffset);
		g2.drawLine((int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize) - yOffset, (int)(xStepSize * (i + 1)) - 7 + xOffset, (int)(chartHeight - yStepSize + 7)- yOffset);
		//arrow left
		i = (int) (-1*(noXSteps));
		g2.drawLine((int)(xStepSize * (i + 1) + 7) + xOffset, (int)(chartHeight - yStepSize - 8) - yOffset, (int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize) - yOffset);
		g2.drawLine((int)(xStepSize * (i + 1)) + xOffset, (int)(chartHeight - yStepSize) - yOffset, (int)(xStepSize * (i + 1)) + 7 + xOffset, (int)(chartHeight - yStepSize + 7)- yOffset);
		
		//y marks
		for(i = (int) (-1*(noYSteps - 1)); i < noYSteps - 1; i++) {
			g2.drawLine((int)(xStepSize - 8) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) - yOffset, (int)(xStepSize + 7) + xOffset, (int)(chartHeight - yStepSize -yStepSize * i) - yOffset);
			g2.drawString(i*(int)(valYStepSize)+"", (int)(xStepSize - 18) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) - yOffset);
		}
		//arrow top
		i = (int) (noYSteps - 1);
		g2.drawLine((int)(xStepSize - 8) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) + 7 - yOffset, (int)(xStepSize) + xOffset, (int)(chartHeight - yStepSize -yStepSize * i) - yOffset);
		g2.drawLine((int)(xStepSize) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) - yOffset, (int)(xStepSize + 7) + xOffset, (int)(chartHeight - yStepSize -yStepSize * i) + 7 - yOffset);
		//arrow bottom			
		i = (int) (-1*(noYSteps));
		g2.drawLine((int)(xStepSize - 8) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) - 7 - yOffset, (int)(xStepSize) + xOffset, (int)(chartHeight - yStepSize -yStepSize * i) - yOffset);
		g2.drawLine((int)(xStepSize) + xOffset, (int)(chartHeight - yStepSize - yStepSize * i) - yOffset, (int)(xStepSize + 7) + xOffset, (int)(chartHeight - yStepSize -yStepSize * i) - 7 - yOffset);
		
	}
}
