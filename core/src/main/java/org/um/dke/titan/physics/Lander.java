package org.um.dke.titan.physics;

import com.badlogic.gdx.math.Vector2;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Lander {

    private String probeName = SpaceObjectEnum.SHIP.getName();
    private Vector3dInterface force;
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();

    private double x;
    private double y;
    private double side;


    public Rectangle2D.Double setSquare(){
        Vector3D p = (Vector3D) system.getRocketByName(probeName).getPosition();
        double originX = p.getX() - side/2;
        double originY = p.getY() + side/2;
        return new Rectangle2D.Double(originX, originY, side, side);
    }






}