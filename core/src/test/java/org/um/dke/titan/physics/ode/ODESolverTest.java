package org.um.dke.titan.physics.ode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ODESolverTest {
    private double timeStep;
    private ODEFunction f;

    @Before
    public void beforeEach() {
        f = new ODEFunction();
    }

    @Test
    public void testTimeStepSmallerThanZero() {
        System.out.println("Hello");
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);

        timeStep = -10;

        assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
}
