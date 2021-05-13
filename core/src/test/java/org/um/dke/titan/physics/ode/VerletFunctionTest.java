package org.um.dke.titan.physics.ode;

import org.graalvm.compiler.debug.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEVerletFunction;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(GdxTestRunner.class)
public class VerletFunctionTest {

    private double timeStep;
    private ODEVerletFunction f;
    /** Tests for the call method  **/
    @Test
    public void testTimeStepSmallerThanZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = -10;

        assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    public void testTimeStepEqualToZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = 0;

        assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    //I have some doubts on this one since we are already testing what happens if we pass a null object to applyforces but yeah
    @Test
    public void movingObjectisNull() {
        MovingObject m = null;
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = 0;

        assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    public void statePassedIsNull() {
        State state = null;
        timeStep = 0.5;
        f = new ODEVerletFunction();

        assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
//    @Test
//    public void computationsAreDoneCorretlyInTheCallMethod() {
//        MovingObject m = new MovingObject("Mars", 100, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
//        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
//        m.setForce((new Vector3D(10, 10, 10)));
//        timeStep = 1;
//        Vector3D rateAccelleration = new Vector3D(100/10, 100/10, 100/10);
//        Vector3dInterface velocity  = state.getVelocity(); // vel + (acc + rateAcc)*dt*1/2
//        Vector3dInterface rateVelocity = new Vector3D(velocity.getX() + (rateAccelleration.getX() + 10)* timeStep * (1/2), velocity.getY() + (rateAccelleration.getY() + 10)* timeStep * (1/2), velocity.getZ() + (rateAccelleration.getZ() + 10)* timeStep * (1/2));
//        Rate r = new Rate(rateAccelleration, rateVelocity);
//        f = new ODEVerletFunction();
//
//        assertEquals(r, f.call(timeStep, state));
//    }

}
