package org.um.dke.titan.physics.ode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.solvers.ODERungeSolver;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;
import org.um.dke.titan.repositories.SolarSystemRepository;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(GdxTestRunner.class)
public class ODERungeSolverTest {

    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();
    private List<MovingObject> planets = new ArrayList<MovingObject>();
    private int size;
    private StateInterface[][] timelineArray;
    private int currentPlanetIndex;

//TODO: i really need to test just the public methods aka solve, step, getData


    @Test
    public void testRungeSolvermethodsolveNullFunction(){

        ODERungeSolver r = new ODERungeSolver();
        ODEFunctionInterface f = null;
        StateInterface y0 = new State(new Vector3D(1, 1,1 ), new Vector3D(1, 1, 1), new MovingObject("test", 100, 10, new Vector3D(1, 1, 1), 1, new Vector3D(1,1,1)));
        double[] ts = new double[100];
        assertThrows(NullPointerException.class, () -> r.solve(f, y0, ts));
    }

    @Test
    public void testRungeSolvermethodsolveNullState(){

        ODERungeSolver r = new ODERungeSolver();
        ODEFunctionInterface f = new ODEFunction();
        StateInterface y0 = null;
        double[] ts = new double[100];
        assertThrows(NullPointerException.class, () -> r.solve(f, y0, ts));
    }
    @Test
    public void testRungeSolvermethodsolveNullTimeStepArray(){

        ODERungeSolver r = new ODERungeSolver();
        ODEFunctionInterface f = new ODEFunction();
        StateInterface y0 = new State(new Vector3D(1, 1,1 ), new Vector3D(1, 1, 1), new MovingObject("test", 100, 10,  new Vector3D(1, 1, 1), 1, new Vector3D(1,1,1)));
        double[] ts = null;
        assertThrows(NullPointerException.class, () -> r.solve(f, y0, ts));
    }

    @Test
    public void testRungeSolvermethodsolveTSLengthZeroTest() {
        ODERungeSolver r = new ODERungeSolver();
        ODEFunctionInterface f = new ODEFunction();
        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
        double[] ts = new double[0];
        assertThrows(IllegalArgumentException.class, () -> r.solve(f, y0, ts));
    }


//    public void testRungeSolvermethodsolveLegalArguments() {
//        double[] ts =  {0.1, 0.2, 0.3, 0.4};
//        addInitialStates();
//        for (int i = 1; i < size; i++) {
//            int j = 0;
//            for(MovingObject planet : planets)
//            {
//                double time = 1;
//
//
//                timelineArray[j][i] = step(f, time, timelineArray[j][i - 1], ts[i]-ts[i-1]);
//                State state = (State) timelineArray[j][i];
//            }
//
//        }
//    }
//
//
//
//    /** Just the methods from ODERungeSolver **/
//
//    private void init(double tf, double h) {
//
//        this.planets = new ArrayList<>();
//
//        for (Planet planet : system.getPlanets().values()) {
//            planets.add(planet);
//            planets.addAll(planet.getMoons().values());
//        }
//
//        planets.addAll(system.getRockets().values());
//
//        size = (int)Math.round(tf/h)+1;
//        timelineArray = new StateInterface[planets.size()][size];
//    }
//
//    private void addInitialStates() {
//        for (int i = 0; i < planets.size(); i++) {
//            StateInterface state = new State(planets.get(i).getPosition(), planets.get(i).getVelocity(), new Vector3D(0,0,0), planets.get(i));
//            timelineArray[i][0] = state;
//        }
//    }




}
