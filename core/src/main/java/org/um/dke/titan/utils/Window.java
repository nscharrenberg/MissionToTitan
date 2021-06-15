package org.um.dke.titan.utils;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	private ChartPanel panel;
	public Window(double width, double height, double[] xVals, double[] yVals) {
		super("Chart");
		//double[][] vals = Function.f(new double[]{1000,30000}, new double[]{2,5}, -10, 10, 0.5);
		int chartWidth = 600;
		int chartHeight = 600;
		panel = new ChartPanel(xVals, yVals, chartWidth, chartHeight, 1, 100);
		add(panel);
		setVisible(true);
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
}
