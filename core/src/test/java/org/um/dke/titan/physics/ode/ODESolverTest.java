package org.um.dke.titan.physics.ode;

import org.junit.Before;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.solvers.ODERungeSolver;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ODESolverTest {
	@Before
	public void before() {
		FactoryProvider.getSolarSystemRepository().getPlanets().put(SpaceObjectEnum.EARTH.getName(), new Planet(SpaceObjectEnum.EARTH.getName(), 3, 3, new Vector3D(3,3,3), 3, new Vector3D(3,3,3)));
	}
	
    @Test
    public void solveTSNullTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = null;
    	assertThrows(NullPointerException.class, () -> { odes.solve(f, y0, ts);});
    }
    @Test

    public void solvey0NullTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = null;
    	double[] ts = {0, 0.1, 0.2, 0.3, 0.4, 0.5};
    	odes.getData(f, 0.5, 0.1);
    	assertThrows(NullPointerException.class, () -> { odes.solve(f, y0, ts);});
    }
    @Test
    public void solveFNullTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = null;
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = {0, 0.1, 0.2, 0.3, 0.4, 0.5};
    	assertThrows(NullPointerException.class, () -> {odes.getData(f, 0.5, 0.1);});
    }
    @Test
    public void solveTSLengthZeroTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = new double[0];
    	odes.getData(f, 0.5, 0.1);
    	State expected = new State(new Vector3D(4.5, 4.5, 4.5), new Vector3D(3.0, 3.0, 3.0), new Planet(SpaceObjectEnum.EARTH.getName(), 3, 3, new Vector3D(3,3,3), 3, new Vector3D(3,3,3)));
    	State actual = (State) odes.solve(f, y0, ts)[0];
    	assertEquals(expected.getPosition().getX(), actual.getPosition().getX(), 1e7);
		assertEquals(expected.getPosition().getY(), actual.getPosition().getY(), 1e7);
		assertEquals(expected.getPosition().getZ(), actual.getPosition().getZ(), 1e7);

		assertEquals(expected.getVelocity().getX(), actual.getVelocity().getX(), 1e7);
		assertEquals(expected.getVelocity().getY(), actual.getVelocity().getY(), 1e7);
		assertEquals(expected.getVelocity().getZ(), actual.getVelocity().getZ(), 1e7);
    }
    
    @Test
    public void getIndexOfPlanetY0NullTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = {0, 0.1, 0.2, 0.3, 0.4, 0.5};

    }

	@Test
	public void testRungeSolveMethodComputationsAreCorrect(){
		ODESolver odes = new ODESolver();
		StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
		ODEFunctionInterface f = new ODEFunction();
		double [] ts = { 0.1, 0.2, 0.3, 0.4 };
		double tf = ts.length;
		StateInterface[][] data = odes.getData(f, tf, 0.1);
		StateInterface[] arr = odes.solve(f, y0 , ts);
		StateInterface[] currentPlanetArray = getSingleRow(data, odes.getCurrentPlanetIndex());
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
