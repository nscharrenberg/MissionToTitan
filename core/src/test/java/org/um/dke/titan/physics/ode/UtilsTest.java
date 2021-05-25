package org.um.dke.titan.physics.ode;

import org.junit.Test;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.RateInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UtilsTest {

    @Test
    public void getSpaceObjectEnumByName() {
        SpaceObjectEnum found = SpaceObjectEnum.getByName("Earth");

        assertEquals(SpaceObjectEnum.EARTH.getId(), found.getId());
        assertEquals(SpaceObjectEnum.EARTH.getTexturePath(), found.getTexturePath());
    }
}
