package org.um.dke.titan.physics.ode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(GdxTestRunner.class)
public class StateTest {


//	 @Test
//	 public void addMullFunctionalityTest() {
//
//		 State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
//
//		 double step = 3;
//		 Rate r = new Rate(new Vector3D(5, 5, 5), new Vector3D(4, 4,4));
//		 Rate scaled = new Rate(r.getVelocity().mul(step), r.getAcceleration().mul(step));
//
//		 State final_state = new State();
//
//		 assertEquals(original_state.addMul(step, r), final_state);
//	 }



    @Test
    public void testAddMulPassingNullRate() {

        State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
        double step = 10;
        RateInterface r = null;

        assertThrows(NullPointerException.class, () -> original_state.addMul(step, r));
    }
    @Test
    public void testAddMulPassingNullRateSecondMethod() {

        State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
        double step = 10;
        State s = null;

        assertThrows(NullPointerException.class, () -> original_state.addMul(step, s));
    }
    @Test
    public void testAddMulPassingNegativeStepSize() {
        State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
        double step = -10;
        Vector3D v = new Vector3D(7, 7, 7);
        Vector3D a = new Vector3D(99, 99, 99);
        RateInterface r = new Rate(a, v);
        assertThrows(IllegalArgumentException.class, () -> original_state.addMul(step, r));
    }


    @Test
    public void testAddMulPassingNegativeStepSizeSecondMethod() {
        State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
        double step = -10;
        Vector3D v = new Vector3D(7, 7, 7);
        Vector3D p = new Vector3D(99, 99, 99);
        State st = new State(p, v, null);
        assertThrows(IllegalArgumentException.class, () -> original_state.addMul(step, st));
    }


//    @Test
//    public void testAddPassingANonNullState() {
//
//        Vector3D p = new Vector3D(33, 555, 9);
//        Vector3D v = new Vector3D(42, 81, 768);
//
//        StateInterface st = new State(p, v, Object);
//
//        assertEquals(st, s.add(st));
//    }
    @Test
    public void testAddPassingANullState() {

        State original_state = new State(new Vector3D(1, 1, 1), new Vector3D(2, 2,2), null);
        State s = null;
        assertThrows(NullPointerException.class, () -> original_state.add(s));
    }

}
