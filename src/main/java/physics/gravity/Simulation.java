package physics.gravity;

import domain.Vector3D;
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
        double min = Double.MAX_VALUE;
        StateInterface minState;
        minState = start;
        int xIndex = 0;
        int yIndex = 0;
        int zIndex = 0;
        int kIndex = 0;
        Vector3dInterface vBest = new Vector3D(0,0,0);
        for (int x = 35000; x < 50000; x+=100) {
            for(int y = -70000; y > -85000; y-=100) {
                for (int z = -100; z < 100; z += 10) {

                    for (int k = 45000; k < 60000; k += 100) {
                        double newZ = z/10;
                        Vector3dInterface v = new Vector3D(x, y, newZ);
                        v = v.mul(k/v.norm());
                        FactoryProvider.getSolarSystemFactory().init(v);
                        timeLineArray = odes.getData(odef, totalTime, dt);
                        System.out.println("x: " + x + " y: " + y + " z: " + newZ + " k: " + k );
                        System.out.println(printMin());
                        if (min > printMin()) {
                            min = printMin();
                            xIndex = x;
                            yIndex = y;
                            zIndex = z;
                            kIndex = k;
                            vBest = v;

                        }
                    }
                }
            }
        }

        System.out.println(xIndex);
        System.out.println(yIndex);
        System.out.println(zIndex);
        System.out.println(kIndex);
        System.out.println(vBest);





    }

    private static Vector3dInterface unitVecToGoal(Vector3dInterface start, Vector3dInterface goal) {
        Vector3dInterface aim = goal.sub(start); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }

    private static double printMin() {
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
        return min;
        //System.out.println(min);
    }


}
