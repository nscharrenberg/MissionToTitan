package org.um.dke.titan.error;


import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Map;

/**
 *  in this class we calculate the error of the planets by comparing our results from the
 *  ODE's to the result from NASA's Horizon program
 */

public class ErrorCalc {

    private HashMap<Integer, Vector3dInterface> map;
    private StateInterface[] timeLineArray;

    public ErrorCalc(HashMap<Integer, Vector3dInterface> map, StateInterface[] timeLineArray) {
        this.map = map;
        this.timeLineArray = timeLineArray;
    }

    public double averageError(String name) {
        double total = totalErrorSum(name);
        return total/ map.size();
    }

    private double totalErrorSum(String name) {
        double total = 0;

        for (Map.Entry<Integer, Vector3dInterface> entry : map.entrySet()) {
            int key = entry.getKey()/((int)Main.dt);
            Vector3D value = (Vector3D) entry.getValue().mul(1000);

            org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState planets = (org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState) timeLineArray[key];

            total += relativeVecError(value, (Vector3D) planets.getPlanet(name).getPosition());
        }

        return total;
    }

    private double relativeVecError(Vector3D p, Vector3D star) {
        double x = relativeError(p.getX(), star.getX());
        double y = relativeError(p.getY(), star.getY());
        double z = relativeError(p.getZ(), star.getZ());

        return (x+y+z)/3;
    }

    private double relativeError(double p, double star) {
        return Math.abs(absoluteError(p, star)/p);
    }

    private double absoluteError(double p, double star) {
        return Math.abs(p - star);
    }

}
