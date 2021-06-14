package org.um.dke.titan.utils;

import java.util.Arrays;

public class FindMin {
    public static float findMin(double... s){
        double[] a = Arrays.stream(s).toArray();
        double min = a[0];
        for(double d: a){
            if(d < min)
                min = d;
        }
        return (float)min;
    }
}
