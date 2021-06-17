package org.um.dke.titan.utils.testsquarehandling;

import javax.swing.*;

public class Window extends JFrame {
    private JPanel panel;
    public Window() {
        super("Square Testing");
        int chartWidth = 600;
        int chartHeight = 600;

        panel = new SquarePanel();

        setVisible(true);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
