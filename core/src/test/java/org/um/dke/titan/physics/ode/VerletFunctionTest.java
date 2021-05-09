package org.um.dke.titan.physics.ode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEVerletFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerletFunctionTest {

    private double timeStep;
    private ODEVerletFunction f;
    /** Tests for the call method  **/
    @Test
    @DisplayName("Testing the method call passing a time step smaller than zero")
    public void testTimeStepSmallerThanZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = -10;

        Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Testing the method call passing a time step equal to zero")
    public void testTimeStepEqualToZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = 0;

        Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    //I have some doubts on this one since we are already testing what happens if we pass a null object to applyforces but yeah
    @Test
    @DisplayName("Testing the method call passing a null moving object")
    public void movingObjectisNull() {
        MovingObject m = null;
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEVerletFunction();

        timeStep = 0;

        Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Testing the method call passing a null state")
    public void statePassedIsNull() {
        State state = null;
        timeStep = 0.5;
        f = new ODEVerletFunction();

        Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Testing what happens in the call method if legal parameters are passed")
    public void computationsAreDoneCorretlyInTheCallMethod() {
        MovingObject m = new MovingObject("Mars", 100, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        m.setForce((new Vector3D(10, 10, 10)));
        timeStep = 1;
        Vector3D rateAccelleration = new Vector3D(100/10, 100/10, 100/10);
        Vector3dInterface velocity  = state.getVelocity(); // vel + (acc + rateAcc)*dt*1/2
        Vector3dInterface rateVelocity = new Vector3D(velocity.getX() + (rateAccelleration.getX() + 10)* timeStep * (1/2), velocity.getY() + (rateAccelleration.getY() + 10)* timeStep * (1/2), velocity.getZ() + (rateAccelleration.getZ() + 10)* timeStep * (1/2));
        Rate r = new Rate(rateAccelleration, rateVelocity);

        f = new ODEVerletFunction();

        assertEquals(r, f.call(timeStep, state));
    }

}
