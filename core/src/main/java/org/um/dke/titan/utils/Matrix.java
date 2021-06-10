package org.um.dke.titan.utils;

public class Matrix{

    public static <T> T[][] inverse(T[][] matrix) {
        if(matrix.length != 3 && matrix[0].length != 3)
            throw new IllegalArgumentException("This matrix is not 3x3!");

        return (T[][]) new Object[3][3];
    }


}
