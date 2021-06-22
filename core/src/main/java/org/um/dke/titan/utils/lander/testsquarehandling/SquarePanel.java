package org.um.dke.titan.utils.lander.testsquarehandling;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.lander.math.SquareHandling;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {
    private SquareComponent s;
    public SquarePanel() {
        super();
        setPreferredSize(new Dimension(500, 500));

        //square
        double angle = Math.PI/6.0;
        boolean left = false;
        Vector3dInterface center = new Vector3D(200, 200, 0);
        Vector3dInterface[] corners = SquareHandling.calculateCorners(center, angle);
        double[] interval = SquareHandling.exposedSide(center, corners, angle, left);
        double y = SquareHandling.generateRandom(interval[0], interval[1]);
        double x = SquareHandling.calculateAccX(center, corners, left, y);
        Vector3dInterface dist = SquareHandling.calculateDist(center, x, y);
        s = new SquareComponent(center, corners, x, y, dist);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        s.paintComponent(g);
    }


}
