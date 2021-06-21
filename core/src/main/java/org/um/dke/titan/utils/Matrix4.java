package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.physics.ode.solvers.Vector4D;

import java.util.Arrays;

public class Matrix4 {


    public static void main(String[] args) {
        Vector3D v = new Vector3D(-3.81974895879342E14, 4.306160517497311E14, -7.04675795964096E14);
    }

    public static Vector4D multiply(double[][] matrix, Vector4D vector) {
        sizeCheck(matrix);

        double[] result = new double[4];
        double[] v = { vector.getX(), vector.getY(), vector.getZ() , vector.getV()};

        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 4; i++)
                result[j] += matrix[j][i] * v[i];

        return new Vector4D(result[0], result[1], result[2], result[3]);
    }

    public static double[][] inverse(double[][] matrix) {
        sizeCheck(matrix);

        double determinant = getDeterminant(matrix);
        double[][] adjugate = transpose(matrix);
        double[][] cofactor = getCofactor(adjugate);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                cofactor[i][j] = cofactor[i][j] * 1/determinant;

        return cofactor;
    }


    /**
     * returns the transpose of a given matrix
     */
    private static double[][] transpose(double[][] matrix) {

        double[][] adjugate = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adjugate[i][j] = matrix[j][i];
            }
        }
        return adjugate;
    }



    /**
     * returns the cofactor matrix of a given matrix
     */
    private static double[][] getCofactor(double[][] matrix) {
        double[][] cofactors = new double[4][4];
        Minor[][] minors = getMatrixOfMinors(matrix);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cofactors[i][j] = minors[i][j].getDeterminant();
            }
        }

        cofactors[0][1] = cofactors[0][1] * -1;
        cofactors[0][3] = cofactors[0][3] * -1;
        cofactors[2][1] = cofactors[2][1] * -1;
        cofactors[2][3] = cofactors[2][3] * -1;

        cofactors[1][0] = cofactors[1][0] * -1;
        cofactors[1][2] = cofactors[1][2] * -1;
        cofactors[3][0] = cofactors[3][0] * -1;
        cofactors[3][2] = cofactors[3][2] * -1;

        return cofactors;
    }



    private static double getDeterminant(double[][] matrix) {
        Minor[][] minors = getMatrixOfMinors(matrix);
        return matrix[0][0]*minors[0][0].getDeterminant() -
               matrix[0][1]*minors[0][1].getDeterminant() +
               matrix[0][2]*minors[0][2].getDeterminant() -
               matrix[0][3]*minors[0][3].getDeterminant();
    }


    /**
     * returns the matrix of minors of a given matrix
     */
    private static Minor[][] getMatrixOfMinors(double[][] matrix) {
        sizeCheck(matrix);

        Minor[][] minors = new Minor[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                minors[i][j] = new Minor(getMinor(i,j, matrix));
            }
        }

        return minors;
    }

    /**
     * return the minor of matrix[k][l]
     */
    private static double[][] getMinor(int k, int l, double[][] matrix) {
        double[][] minor = new double[3][3];

        int x = 0;
        int y = 0;
        for (int i = 0; i < 4; i++) {
            if (i == k)
                continue;
            for (int j = 0; j < 4; j++) {
                if (j == l)
                    continue;
                minor[x][y] = matrix[i][j];
                y++;
            }
            y = 0;
            x++;
        }
        return minor;
    }



    /**
     * checks if the given matrix is 3x3
     */
    private static void sizeCheck(double[][] matrix) {
        if(matrix.length != 4 || matrix[0].length != 4)
            throw new IllegalArgumentException("This matrix is not 3x3!");
    }


    private static class Minor {
        double[][] minor;

        public Minor(double[][] minor) {
            this.minor = minor;
        }

        /**
         * returns the determinant of the 3x3 matrix
         */
        public double getDeterminant() {
            return Matrix3.getDeterminant3(minor);
        }

        @Override
        public String toString() {
            return Arrays.toString(minor[0]) + "\n" + Arrays.toString(minor[0]) + "\n" + Arrays.toString(minor[0]);
        }

    }


}
