package physics.gravity;

import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

import java.util.ArrayList;

public class Simulation {

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    private static StateInterface[][] timeLineArray;


    public static void main(String[] args) {
        FactoryProvider.getSolarSystemFactory().init();
        double totalTime = FactoryProvider.getSettingRepository().getYearCount() * FactoryProvider.getSettingRepository().getDayCount() * daySec;

        ODESolver odes;
        ODEFunction odef;


        odes = new ODESolver(FactoryProvider.getSolarSystemFactory());
        odef = new ODEFunction(FactoryProvider.getSolarSystemFactory());
        timeLineArray = odes.getData(odef, totalTime, dt);

        State start = (State) timeLineArray[3][0];

        for (int i = 20000; i < 25000; i+=10) {
            State goal = (State) timeLineArray[8][i];
            Vector3dInterface unit = unitVecToGoal(start.getPosition(), goal.getPosition());
            FactoryProvider.getSolarSystemFactory().init(start.getVelocity().add(unit.mul(60000)));
            timeLineArray = odes.getData(odef, totalTime, dt);
            printMin();
        }

    }

    private static Vector3dInterface unitVecToGoal(Vector3dInterface start, Vector3dInterface goal) {
        Vector3dInterface aim = goal.sub(start); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }

    private static void printMin() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            if (i * dt / daySec < 300) {
                State probe = (State) timeLineArray[9][i];
                State titan = (State) timeLineArray[8][i];
                if (probe.getPosition().sub(titan.getPosition()).norm() < (69911e3 + 20)) {
                    System.out.println("COLLISION");
                }
                double dist = probe.getPosition().sub(titan.getPosition()).norm() - 2575.5e3;
                if (min > dist) {
                    min = dist;
                }
            }
        }
        System.out.println(min);
    }


}
