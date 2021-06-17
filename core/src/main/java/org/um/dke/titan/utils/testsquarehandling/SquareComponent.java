package org.um.dke.titan.utils.testsquarehandling;

import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.SquareHandling;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class SquareComponent extends JComponent {
    private final Vector3dInterface center;
    private final Vector3dInterface[] corners;
    private final Vector3dInterface dist;
    private double x,y;

    public SquareComponent(Vector3dInterface center, Vector3dInterface[] corners, double x, double y, Vector3dInterface dist) {
        setPreferredSize(new Dimension(500, 500));
        this.center = center;
        this.corners = corners;
        Collections.sort(Arrays.asList(corners), SquareHandling.xComp);
        Vector3dInterface v = corners[2];
        corners[2] = corners[3];
        corners[3] = v;
        this.y = y;
        this.x = x;
        this.dist = dist;
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
        g2.setColor(Color.blue);
        g2.drawLine(0, (int)y, 10000, (int)y);
        g2.setColor(Color.magenta);
        g2.drawLine((int)(center.getX()), (int)(center.getY()), (int)(center.getX() + dist.getX()), (int)(center.getY() + dist.getY()));
        g2.setColor(Color.green);
        g2.drawOval((int)x - 2, (int)y-2, 2, 2);
    }
}
