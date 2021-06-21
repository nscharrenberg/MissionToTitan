package org.um.dke.titan.utils;

import org.junit.Test;
import org.um.dke.titan.utils.FindMin;

import static org.junit.Assert.assertEquals;

public class FindMinTest {
    @Test
    public void testMin1(){
        assertEquals(3, FindMin.findMin(6,3,8,12), 1e-7);
    }
    @Test
    public void testMin2(){
        assertEquals(3, FindMin.findMin(3,6,3,8,12), 1e-7);
    }
    @Test
    public void testMin3(){
        assertEquals(3, FindMin.findMin(6,8,12,3), 1e-7);
    }
}
