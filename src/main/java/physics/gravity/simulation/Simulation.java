package physics.gravity.simulation;

import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.ODEFunction;
import physics.gravity.ode.ODESolver;
import physics.gravity.ode.State;
import repositories.SolarSystemRepository;
import repositories.interfaces.ISettingRepository;

public class Simulation {

    protected static double daySec = 60*24*60;
    protected static double t;
    protected static double dt = 0.1*daySec;
    public static StateInterface[][] timeLineArray;

	public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    private static void simulate(Vector3dInterface unit, int velocity) {
        SolarSystemRepository system = FactoryProvider.getSolarSystemFactory();
        ISettingRepository setting = FactoryProvider.getSettingRepository();

        double totalTime = setting.getYearCount() * setting.getDayCount() * daySec;
        ODESolver odes = new ODESolver();
        ODEFunction odef = new ODEFunction();

        system.init();
        // TODO: fix .findPlanet("Earth")

        Vector3dInterface initialVelocity = unit.mul(velocity);
        Vector3dInterface earthVelocity = system.getPlanets().get(3).getVelocity();
        system.init(initialVelocity.add(earthVelocity));

        timeLineArray = odes.getData(odef, totalTime, dt);
    }

    public static StateInterface[][] simulate() {
        SolarSystemRepository system = FactoryProvider.getSolarSystemFactory();
        ISettingRepository setting = FactoryProvider.getSettingRepository();

        double totalTime = setting.getYearCount() * setting.getDayCount() * daySec;
        ODESolver odes = new ODESolver();
        ODEFunction odef = new ODEFunction();

        system.init();
        return odes.getData(odef, totalTime, dt);
    }

    public static double getMinDistance() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State probe = (State) timeLineArray[9][i];
            State titan = (State) timeLineArray[8][i];

            double dist = probe.getPosition().dist(titan.getPosition());

            if (min > dist)
                min = dist;
        }
       return min;
    }
}

