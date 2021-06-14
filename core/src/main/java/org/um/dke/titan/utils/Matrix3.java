package org.um.dke.titan.utils;


public class Matrix3 {

    /**
     * return the inverse of a 3x3 matrix
     * [A]^-1 = 1/determinant * adj(A)
     */
    public static double[][] inverse(double[][] matrix) {
        sizeCheck(matrix);

        double determinant = getDeterminant3(matrix);
        double[][] cofactor = getCofactor(matrix);

        cofactor = adjugate(cofactor);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                cofactor[i][j] = cofactor[i][j] * 1/determinant;

        return cofactor;
    }


    // -------------- inverse helper methods --------------

    /**
     * returns the adjugate of a given matrix
     */
    private static double[][] adjugate(double[][] matrix) {

        double[][] adjugate = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                adjugate[i][j] = matrix[j][i];
            }
        }
        return adjugate;
    }

    /**
     * returns the cofactor matrix of a given matrix
     */
    private static double[][] getCofactor(double[][] matrix) {
        double[][] cofactors = new double[3][3];
        Minor[][] minors = getMatrixOfMinors(matrix);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cofactors[i][j] = minors[i][j].getDeterminant();
                if((i == 1 || j == 1) && !(i == 1 && j == 1)) {
                    cofactors[i][j] = cofactors[i][j] * -1;
                }
            }
        }

        return cofactors;
    }

    /**
     * returns the matrix of minors of a given matrix
     */
    private static Minor[][] getMatrixOfMinors(double[][] matrix) {
        sizeCheck(matrix);

        Minor[][] minors = new Minor[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                minors[i][j] = new Minor(getMinor(i,j, matrix));
            }
        }

        return minors;
    }

    /**
     * return the minor of matrix[k][l]
     */
    private static double[][] getMinor(int k, int l, double[][] matrix) {
        double[][] minor = new double[2][2];

        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            if (i == k)
                continue;
            for (int j = 0; j < 3; j++) {
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
     * returns the determinant of a 3x3 matrix
     * @param matrix
     * @return
     */
    private static double getDeterminant3(double[][] matrix) {
        sizeCheck(matrix);

        matrix = getExtendedMatrix(matrix);

        double sum = 0;

        //left to right diagonals
        for (int i = 0; i < 3; i++)
            sum += matrix[0][i] * matrix[1][i + 1] * matrix[2][i + 2];

        //right to left diagonals
        for (int i = 2; i >= 0; i--)
            sum -= matrix[0][i + 2] * matrix[1][i + 1] * matrix[2][i];

        return sum;
    }


    /**
     *  making a matrix where the 4th and 5th column are the same as the 1st and 2nd column
     *  [ 0 1 2 ]     [ 0 1 2  0 1 ]
     *  [ 3 4 5 ] =>  [ 3 4 5  3 4 ]
     *  [ 6 7 8 ]     [ 6 7 8  6 7 ]
     */
    private static double[][] getExtendedMatrix(double[][] matrix) {
        double[][] extendedMatrix = new double[3][5];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                extendedMatrix[i][j] = matrix[i][j];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 2; j++)
                extendedMatrix[i][j + 3] = matrix[i][j];

        return extendedMatrix;
    }

    /**
     * checks if the given matrix is 3x3
     */
    private static void sizeCheck(double[][] matrix) {
        if(matrix.length != 3 || matrix[0].length != 3)
            throw new IllegalArgumentException("This matrix is not 3x3!");
    }

    private static class Minor {
        double[][] minor;

        public Minor(double[][] minor) {
            this.minor = minor;
        }

        /**
         * returns the determinant of the 2x2 matrix
         */
        public double getDeterminant() {
            return minor[0][0] * minor[1][1] - minor[0][1] * minor[1][0];
        }

    }

}
