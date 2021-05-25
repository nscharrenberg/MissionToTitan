package org.um.dke.titan.experimental;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.State;

import java.util.HashMap;
import java.util.Map;

public class ErrorCalc {

    private HashMap<Integer, Vector3dInterface> map;
    private StateInterface[][] timeLineArray;

    public ErrorCalc(HashMap<Integer, Vector3dInterface> map, StateInterface[][] timeLineArray) {
        this.map = map;
        this.timeLineArray = timeLineArray;
    }

    public double averageError(int planetId) {
        double total = totalErrorSum(planetId);
        return total/ map.size();
    }

    private double totalErrorSum(int planetId) {
        double total = 0;

        for (Map.Entry<Integer, Vector3dInterface> entry : map.entrySet()) {
            int key = entry.getKey()/(60);
            Vector3D value = (Vector3D) entry.getValue().mul(1000);

            State planet = (State) timeLineArray[planetId][key];
            total += relativeVecError(value, (Vector3D) planet.getPosition());
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
