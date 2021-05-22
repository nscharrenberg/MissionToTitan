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
import org.um.dke.titan.physics.ode.solvers.ODEVerletSolver;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;
import org.um.dke.titan.repositories.SolarSystemRepository;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ODERungeSolverTest {

    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();
    private List<MovingObject> planets = new ArrayList<MovingObject>();
    private int size;
    private StateInterface[][] timelineArray;
    private int currentPlanetIndex;



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

    @Test
    public void testStepMethodPassingNullFunction(){
        ODERungeSolver r = new ODERungeSolver();
        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
        ODEFunctionInterface f = null;
        double t = 0; double h = 0;
        assertThrows(NullPointerException.class, () -> r.step(f, t, y0, h));
    }
    @Test
    public void testStepMethodPassingNullState(){
        ODERungeSolver r = new ODERungeSolver();
        StateInterface y0 = null;
        ODEFunctionInterface f = new ODEFunction();
        double t = 0; double h = 0;
        assertThrows(NullPointerException.class, () -> r.step(f, t, y0, h));
    }

    @Test
    public void testRungeSolveMethodComputationsAreCorrect(){
        ODERungeSolver r = new ODERungeSolver();
        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
        ODEFunctionInterface f = new ODEFunction();
        double [] ts = { 0.1, 0.2, 0.3, 0.4 };
        double tf = ts.length;
        StateInterface[][] data = r.getData(f, tf, 0.1);
        StateInterface[] arr = r.solve(f, y0 , ts);
        StateInterface[] currentPlanetArray = getSingleRow(data, r.getCurrentPlanetIndex());
        assertTrue(Arrays.equals(arr, currentPlanetArray));
    }
    public StateInterface[] getSingleRow(StateInterface[][] a, int index) {
        StateInterface[] row = new StateInterface[a[0].length];
        for(int i = 0; i < a[0].length; i++){
            row[i] = a[index][i];
        }
        return row;
    }




}
