package org.um.dke.titan.utils.testsquarehandling;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.SquareHandling;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {
    private SquareComponent s;
    public SquarePanel() {
        super();
        setPreferredSize(new Dimension(500, 500));
        Vector3dInterface center = new Vector3D(200, 200, 0);
        Vector3dInterface[] corners = SquareHandling.calculateCorners(center, Math.PI/4.0);
        s = new SquareComponent(center, corners);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        s.paintComponent(g);
    }
}
