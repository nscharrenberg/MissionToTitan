package org.um.dke.titan.experimental;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.State;

import java.util.HashMap;

public class ErrorCalc {

    private HashMap<Integer, Vector3dInterface> data;
    private StateInterface[][] timeLineArray;

    public ErrorCalc(HashMap<Integer, Vector3dInterface> data, StateInterface[][] timeLineArray) {
        this.data = data;
        this.timeLineArray = timeLineArray;
    }

    public double averageError(HashMap<Integer, Vector3D> data, StateInterface[][] timeLineArray) {
        int planetSize = timeLineArray.length - 1;

        double total = 0;

        for (int i = 0; i < planetSize - 1; i++) {
            total += averageError(i);
        }

        return total/(planetSize - 1);
    }

    private double averageError(int planetId) {
        double total = totalErrorSum(planetId);
        return total/ timeLineArray[0].length;
    }

    private double totalErrorSum(int planetId) {
        double total = 0;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State planet = (State) timeLineArray[planetId][i];
            total += relativeVecError((Vector3D) data.get(i), (Vector3D) planet.getPosition() );
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
