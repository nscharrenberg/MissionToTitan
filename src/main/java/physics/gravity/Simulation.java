package physics.gravity;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import utils.WriteToFile;

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

        State start = (State) timeLineArray[1][0];
        State titan = (State) timeLineArray[4][0];
        System.out.println(titan.getPosition().sub(start.getPosition()).norm());



        for (int i = 21000; i < 36500; i+=2) {
                State goal = (State) timeLineArray[4][i];


                Vector3dInterface velocity = unitVecToGoal(start.getPosition(), goal.getPosition()).mul(58000);
                FactoryProvider.getSolarSystemFactory().init(velocity.add(start.getVelocity()));
                if (i == 21296 ) {
                    System.out.println(velocity.add(start.getVelocity()));
                }
                timeLineArray = odes.getData(odef, totalTime, dt);
                printMin();
                System.out.println(".    i: " + i);

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
                State probe = (State) timeLineArray[5][i];
                State titan = (State) timeLineArray[4][i];
                if (probe.getPosition().sub(titan.getPosition()).norm() < (69911e3 + 20)) {
                    System.out.println("COLLISION");
                }
                double dist = probe.getPosition().sub(titan.getPosition()).norm() - 2575.5e3;
                if (min > dist) {
                    min = dist;
                    if (dist < 3.6e9)
                    {
                        WriteToFile.writeToFile(dist, probe.getVelocity().toString());
                    }
                }
            }
        }
        System.out.print(min);
    }


}
