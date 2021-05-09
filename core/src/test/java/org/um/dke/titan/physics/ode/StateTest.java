package org.um.dke.titan.physics.ode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.um.dke.titan.domain.Vector3D;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {
/**
    private State s;
    @BeforeAll
    public void message(){
        Sytem.out.println("Running the StateTest");
    }
    @BeforeEach
    public void setUp() throws Exception {
        Vector3D p = new Vector3D(3, 5, 9);
        Vector3D v = new Vector3D(7, 7, 7);
        s = new State(p, v, Object);

    }


    @Test
    @DisplayName("Test addMul with an null rate")
    public void testAddMulPassingNullRate() {

        step = 10;
        RateInterface r = null;
        s.addMul(step, r);

        //an error should be thrown whenever a null rate is passed
        Assertions.assertThrows(RuntimeException.class, () -> {
            ;
        });
    }
    @Test
    @DisplayName("Test addMul with a negative step size")
    public void testAddMulPassingNegativeStepSize() {
        step = -10;
        Vector3D v = new Vector3D(7, 7, 7), Vector3D a = new Vector3D(99, 99, 99);
        RateInterface r = new RateInterface(a, v);
        s.addMul(step, r);
        //an error should be thrown by the addMul method whenever a negative step size is passed
        Assertions.assertThrows(RuntimeException.class, () -> {
            ;
        });
    }


    @Test
    @DisplayName("Test addMul with an null state")
    public void testAddMulPassingNullRate() {

        step = 10;
        StateInterface st = null;
        s.addMul(step, st);

        //an error should be thrown whenever a null rate is passed
        Assertions.assertThrows(RuntimeException.class, () -> {
            ;
        });
    }
    @Test
    @DisplayName("Test addMul with a negative step size")
    public void testAddMulPassingNegativeStepSize() {
        step = -10;
        Vector3D v = new Vector3D(7, 7, 7), Vector3D p = new Vector3D(99, 99, 99);
        StateInterface st = new State(p, v, Object);
        s.addMul(step, st);
        //an error should be thrown by the addMul method whenever a negative step size is passed
        Assertions.assertThrows(RuntimeException.class, () -> {
            ;
        });
    }


    @Test
    @DisplayName("Test addMul with a non null state as argument")
    public void testAddPassingANonNullState() {

        Vector3D p = new Vector3D(33, 555, 9);
        Vector3D v = new Vector3D(42, 81, 768);

        StateInterface st = new State(p, v, Object);

        assertEquals(st, s.add(st));
    }
    @Test
    @DisplayName("Test addMul with a null state as argument")
    public void testAddPassingANullState() {

        StateInterface st = null;
        int step = 3;
        //an error should be thrown whenever a null rate is passed
        Assertions.assertThrows(RuntimeException.class, () -> {
            ;
        });
    }


*/
}
