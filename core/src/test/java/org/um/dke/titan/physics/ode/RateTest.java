package org.um.dke.titan.physics.ode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.um.dke.titan.domain.Vector3D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateTest {

    @Test
    @DisplayName("Normal Vector Multiplication")
    public void addMullTest() {
        Rate rate1 = new Rate(new Vector3D(5, 5, 5), new Vector3D(10, 10, 10));
        Rate rate2 = new Rate(new Vector3D(2, 2, 2), new Vector3D(5, 5, 5));

        Rate result = rate1.addMull(2, rate2);

        assertEquals(9, result.getAcceleration().getX());
    }
}