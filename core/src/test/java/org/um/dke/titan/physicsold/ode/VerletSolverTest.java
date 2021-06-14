//package org.um.dke.titan.physicsold.ode;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.um.dke.titan.domain.MovingObject;
//import org.um.dke.titan.domain.Vector3D;
//import org.um.dke.titan.interfaces.ODEFunctionInterface;
//import org.um.dke.titan.interfaces.StateInterface;
//import org.um.dke.titan.physicsold.ode.functions.ODEFunction;
//import org.um.dke.titan.physicsold.ode.solvers.ODESolverVerlet;
//import org.um.dke.titan.physicsold.ode.utils.GdxTestRunner;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.*;
//
//@RunWith(GdxTestRunner.class)
//public class VerletSolverTest {
//
//    @Test
//    public void testVerletSolvermethodsolveNullFunction(){
//
//        ODESolverVerlet v = new ODESolverVerlet();
//        ODEFunctionInterface f = null;
//        StateInterface y0 = new State(new Vector3D(1, 1,1 ), new Vector3D(1, 1, 1), new MovingObject("test", 100, 10, new Vector3D(1, 1, 1), 1, new Vector3D(1,1,1)));
//        double[] ts = new double[100];
//        assertThrows(NullPointerException.class, () -> v.solve(f, y0, ts));
//    }
//
//    @Test
//    public void testVerletSolvermethodsolveNullState(){
//
//        ODESolverVerlet v = new ODESolverVerlet();
//        ODEFunctionInterface f = new ODEFunction();
//        StateInterface y0 = null;
//        double[] ts = new double[100];
//        assertThrows(NullPointerException.class, () -> v.solve(f, y0, ts));
//    }
//    @Test
//    public void testVerletSolvermethodsolveNullTimeStepArray(){
//
//        ODESolverVerlet v = new ODESolverVerlet();
//        ODEFunctionInterface f = new ODEFunction();
//        StateInterface y0 = new State(new Vector3D(1, 1,1 ), new Vector3D(1, 1, 1), new MovingObject("test", 100, 10,  new Vector3D(1, 1, 1), 1, new Vector3D(1,1,1)));
//        double[] ts = null;
//        assertThrows(NullPointerException.class, () -> v.solve(f, y0, ts));
//    }
//
//    @Test
//    public void testVerletSolvermethodsolveTSLengthZeroTest() {
//        ODESolverVerlet v = new ODESolverVerlet();
//        ODEFunctionInterface f = new ODEFunction();
//        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
//        double[] ts = new double[0];
//        assertThrows(IllegalArgumentException.class, () -> v.solve(f, y0, ts));
//    }
//
//    @Test
//    public void testVerletStepMethodPassingNullFunction(){
//        ODESolverVerlet v = new ODESolverVerlet();
//        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
//        ODEFunctionInterface f = null;
//        double t = 0; double h = 0;
//        assertThrows(NullPointerException.class, () -> v.step(f, t, y0, h));
//    }
//    @Test
//    public void testVerletStepMethodPassingNullState(){
//        ODESolverVerlet v = new ODESolverVerlet();
//        StateInterface y0 = null;
//        ODEFunctionInterface f = new ODEFunction();
//        double t = 0; double h = 0;
//        assertThrows(NullPointerException.class, () -> v.step(f, t, y0, h));
//    }
//
//    /**possible approach to test the solve method in al of the three solvers**/
//    @Test
//    public void testVerletSolveMethodComputationsAreCorrect(){
//        ODESolverVerlet v = new ODESolverVerlet();
//        StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
//        ODEFunctionInterface f = new ODEFunction();
//        double [] ts = { 0.1, 0.2, 0.3, 0.4 };
//        double tf = ts.length;
//        StateInterface[][] data = v.getData(f, tf, 0.1);
//        StateInterface[] arr = v.solve(f, y0 , ts);
//        StateInterface[] currentPlanetArray = getSingleRow(data, v.getCurrentPlanetIndex());
//        assertTrue(Arrays.equals(arr, currentPlanetArray));
//    }
//
//    public StateInterface[] getSingleRow(StateInterface[][] a, int index) {
//        StateInterface[] row = new StateInterface[a[0].length];
//        for(int i = 0; i < a[0].length; i++){
//            row[i] = a[index][i];
//        }
//        return row;
//    }
//
//}
