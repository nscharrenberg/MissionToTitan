package org.um.dke.titan.utils;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class Window extends JFrame{
	private JPanel panelX, panelY, panel, panel2, panel3;
	private JLabel left, right;
	public Window(double width, double height, double[] tVals, double[] xVals, double[] yVals) {
		super("Chart");
		int chartWidth = 600;
		int chartHeight = 600;
		left = new JLabel("                                                               x-position over time");
		right = new JLabel("y-position over time                                                               ");
		panelX = new ChartPanel(tVals, xVals, chartWidth, chartHeight, 2000, 1000000);
		panelY = new ChartPanel(tVals, yVals, chartWidth, chartHeight, 2000, 5000);
		panel = new JPanel(new GridLayout(1,2));
		panel2 = new JPanel(new BorderLayout());
		panel3 = new JPanel(new BorderLayout());
		panel2.add(left, BorderLayout.WEST);
		panel2.add(right, BorderLayout.EAST);
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
