package org.um.dke.titan.experiment;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.State;

import java.util.HashMap;

public class ErrorCalc {

    private static HashMap<Integer, Vector3D> data;
    private static StateInterface[][] timeLineArray;

    public static void main(String[] args) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray();

        System.out.println(averageError(SpaceObjectEnum.EARTH.getId()));
    }

    public static double averageError(int planetId) {
        double total = totalErrorSum(planetId);
        return total/ timeLineArray[0].length;
    }

    private static double totalErrorSum(int planetId) {
        double total = 0;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State planet = (State) timeLineArray[planetId][i];
            total += relativeVecError(data.get(i), (Vector3D) planet.getPosition() );
        }

        return total;
    }

    private static double relativeVecError(Vector3D p, Vector3D star) {
        double x = relativeError(p.getX(), star.getX());
        double y = relativeError(p.getY(), star.getY());
        double z = relativeError(p.getZ(), star.getZ());

        return (x+y+z)/3;
    }

    private static double relativeError(double p, double star) {
        return Math.abs(absoluteError(p, star)/p);
    }

    private static double absoluteError(double p, double star) {
        return Math.abs(p - star);
    }

}
