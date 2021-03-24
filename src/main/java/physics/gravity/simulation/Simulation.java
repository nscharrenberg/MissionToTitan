package physics.gravity.simulation;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.ODEFunction;
import physics.gravity.ode.ODESolver;
import physics.gravity.ode.State;
import utils.WriteToFile;

public class Simulation {

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    public static StateInterface[][] timeLineArray;

	public static double run(Vector3dInterface unit, int velocity) {
        FactoryProvider.getSolarSystemFactory().init();
        double totalTime = FactoryProvider.getSettingRepository().getYearCount() * FactoryProvider.getSettingRepository().getDayCount() * daySec;

        ODESolver odes;
        ODEFunction odef;

        odes = new ODESolver();
        odef = new ODEFunction();
        timeLineArray = odes.getData(odef, totalTime, dt);

        State start = (State) timeLineArray[3][0];
        Vector3dInterface initialVelocity = unit.mul(velocity);
        FactoryProvider.getSolarSystemFactory().init(initialVelocity.add(start.getVelocity()));

        timeLineArray = odes.getData(odef, totalTime, dt);
        return getMin();
    }


    private static Vector3dInterface unitVecToGoal(Vector3dInterface start, Vector3dInterface goal) {
        Vector3dInterface aim = goal.sub(start); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }

    public static double getMin() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State probe = (State) timeLineArray[9][i];
            State titan = (State) timeLineArray[8][i];

            double dist = probe.getPosition().dist(titan.getPosition());
            if (min > dist) {
                min = dist;
            }
        }
       return min;
    }


}

