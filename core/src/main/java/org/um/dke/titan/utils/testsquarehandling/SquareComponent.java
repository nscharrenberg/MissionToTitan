package org.um.dke.titan.utils.testsquarehandling;

import org.um.dke.titan.interfaces.Vector3dInterface;

import javax.swing.*;
import java.awt.*;

public class SquareComponent extends JComponent {
    private final Vector3dInterface center;
    private final Vector3dInterface[] corners;

    public SquareComponent(Vector3dInterface center, Vector3dInterface[] corners) {
        setPreferredSize(new Dimension(500, 500));
        this.center = center;
        this.corners = corners;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        int centerOff = 2;
        g2.drawOval((int)center.getX() - centerOff, (int)center.getY() - centerOff, centerOff, centerOff);
        g2.setColor(Color.BLACK);
        int j = 1;
        for(int i = 0; i < 4; i++) {
            double x1 = corners[i].getX(), y1 = corners[i].getY();
            double x2 = corners[j].getX(), y2 = corners[j].getY();
            g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
            System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
            if(j == 3) {
                j = 0;
            } else {
                j++;
            }
        }
    }
}
