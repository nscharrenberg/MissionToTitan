package org.um.dke.titan.physics.ode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class ODESolverTest {
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
    	odes.getData(f, 0.5, 0.1);
    	assertThrows(NullPointerException.class, () -> { odes.solve(f, y0, ts);});
    }
    @Test
    public void solveTSLengthZeroTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = {0, 0.1, 0.2, 0.3, 0.4, 0.5};
    	odes.getData(f, 0.5, 0.1);
    	assertEquals(odes.solve(f, y0, ts)[0], null);
    }
    
    @Test
    public void getIndexOfPlanetY0NullTest() {
    	ODESolver odes = new ODESolver();
    	ODEFunctionInterface f = new ODEFunction();
    	StateInterface y0 = new State(new Vector3D(1, 2, 3), new Vector3D(1, 2, 3), new MovingObject("test", 5000, 100, new Vector3D(1, 2, 3), 1, new Vector3D(3,2,1)));
    	double[] ts = {0, 0.1, 0.2, 0.3, 0.4, 0.5};

    }
}
