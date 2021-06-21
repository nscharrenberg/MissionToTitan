package org.um.dke.titan.utils;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class Window extends JFrame{
	private JPanel panelX, panelY, panelTheta, panel, panel2, panel3;
	private JLabel left, right;
	public Window(double width, double height, double[] tVals, double[] xVals, double[] yVals, double[] theta) {
		super("Chart");
		int chartWidth = 600;
		int chartHeight = 600;
		left = new JLabel("                                                               y-velocity over time");
		right = new JLabel("y-position over time                                                               ");
		panelX = new ChartPanel4(tVals, xVals, chartWidth, chartHeight, 2000, 10);
		panelY = new ChartPanel(tVals, yVals, chartWidth, chartHeight, 700, 5000);
		panelTheta = new ChartPanel(tVals, theta, chartWidth, chartHeight, 700, 1);
		panel = new JPanel(new GridLayout(1,3));
		panel2 = new JPanel(new BorderLayout());
		panel3 = new JPanel(new BorderLayout());
		panel2.add(left, BorderLayout.WEST);
		panel2.add(right, BorderLayout.EAST);
		panel.add(panelTheta);
		panel.add(panelX);
		panel.add(panelY);
		panel3.add(panel, BorderLayout.CENTER);
		panel3.add(panel2, BorderLayout.SOUTH);
		add(panel3);
		setVisible(true);
		setContentPane(panel3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
}
