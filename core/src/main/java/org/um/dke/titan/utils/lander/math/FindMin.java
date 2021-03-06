package org.um.dke.titan.utils.lander.math;

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
    public static float findMax(double... s){
        double[] a = Arrays.stream(s).toArray();
        double max = a[0];
        for(double d: a){
            if(d > max)
                max = d;
        }
        return (float)max;
    }
}
