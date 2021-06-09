package org.um.dke.titan.physicsold.ode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.SpaceObject;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physicsold.ode.utils.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(GdxTestRunner.class)
public class SpaceObjectTest {

    @Test
    public void getDiameterTest() {
        double radius = 1e3;
        SpaceObject so = new SpaceObject(SpaceObjectEnum.EARTH.getName(), (float)1e7, (float)radius, new Vector3D(1, 2, 3), (float)1e2);
        double diameter = radius * 2;

        assertEquals(diameter, so.getDiameter(), 1e7);
        assertEquals(radius, so.getRadius(), 1e7);
    }

    @Test
    public void SetPositionTest() {
        double radius = 1e3;
        Vector3dInterface original = new Vector3D(1, 2, 3);
        SpaceObject so = new SpaceObject(SpaceObjectEnum.EARTH.getName(), (float)1e7, (float)radius, original, (float)1e2);
        Vector3dInterface newPos = new Vector3D(4, 5, 6);
        so.setPosition(newPos);

        assertNotEquals(so.getPosition(), original);
        assertEquals(so.getPosition(), newPos);
    }

    @Test
    public void setAndGetRadius() {
        float radius = (float) 1e3;
        SpaceObject so = new SpaceObject(SpaceObjectEnum.EARTH.getName(), (float)1e7, radius, new Vector3D(1, 2, 3), (float)1e2);
        double newRad = 1e4;

        so.setRadius((float) newRad);

        assertNotEquals(so.getRadius(), radius);
        assertEquals(so.getRadius(), newRad, 1e7);
    }

    @Test
    public void gettersTest() {
        double radius = 1e3;
        String name = SpaceObjectEnum.EARTH.getName();
        double mass = 1e7;
        Vector3dInterface position = new Vector3D(1, 2, 3);
        double zoom =1e2;
        SpaceObject so = new SpaceObject(name, (float)mass, (float)radius, position, (float)zoom);

        assertEquals(name, so.getName());
        assertEquals(mass, so.getMass(), 1e7);
        assertEquals(zoom, so.getZoomLevel(), 1e7);
        assertEquals(position, so.getPosition());
        assertEquals(radius, so.getRadius(), 1e7);
        assertEquals(position.getX(), so.getX(), 1e7);
        assertEquals(position.getY(), so.getY(), 1e7);
        assertEquals(position.getZ(), so.getZ(), 1e7);
    }
}