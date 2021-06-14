package org.um.dke.titan.physicsold.ode.utils;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test strategy
 * input space:
 * rotation: 0 - 360
 * width: real numbers
 * height: real numbers
 *
 * rotation: 0, 1, 90, 214, 360
 * width: big negative, small negative, -1, 0, 1, small pos, big pos
 * height: big negative, small negative, -1, 0, 1, small pos, big pos
 *
 * cases:
 *  1. r:0, w: b n, h: -1
 *  2. r: 1 w: s n, h: b p
 *  3. r: 90 w: -1 h: s n
 *  4. r: 214 w: 0 h: s p
 *  5. r: 360 w: 1 h: b n
 *  6. r: 69 w: s p h: 0
 *  7. r 180 w b p h: 1
 */
public class RotationMatrixText {
}
