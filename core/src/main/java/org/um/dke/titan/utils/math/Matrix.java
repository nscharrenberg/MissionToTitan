package org.um.dke.titan.utils.math;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class Matrix {
    private int rows;
    private int columns;
    private double[][] matrix;

    public Matrix(int rows, int columns, double[][] matrix) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        rows = matrix.length;
        columns = matrix[0].length;
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        this.matrix = new double[rows][columns];
    }

    public Vector3dInterface toVector() {
        if (getRows() != 3 && getColumns() != 1) {
            throw new IllegalStateException("Only able to convert a 3 by 1 matrix to a vector");
        }

        return new Vector3D(getValueAt(0, 0), getValueAt(1, 0), getValueAt(2, 0));
    }

    public static Matrix fromVector(Vector3dInterface vector) {
        Matrix matrix = new Matrix(3, 1);
        matrix.setValueAt(0, 0, vector.getX());
        matrix.setValueAt(1, 0, vector.getY());
        matrix.setValueAt(2, 0, vector.getZ());

        return matrix;
    }

    public Matrix transpose() {
        return transpose(this);
    }

    public Matrix inverse() {
        return inverse(this);
    }

    public double determinant() {
        return determinant(this);
    }

    public Matrix subMatrix(int row, int col) {
        return subMatrix(this, row, col);
    }

    public Matrix cofactor() {
        return cofactor(this);
    }

    public Matrix add(Matrix other) {
        return add(this, other);
    }

    public Matrix substract(Matrix other) {
        return substract(this, other);
    }

    public Matrix multiply(Matrix other) {
        return multiply(this, other);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public void setValueAt(int row, int col, double val) {
        this.matrix[row][col] = val;
    }

    public double getValueAt(int row, int col) {
        return this.matrix[row][col];
    }

    public boolean isSquare() {
        return rows == columns;
    }

    public int size() {
        return matrix.length;
    }

    public Matrix multiply(double value) {
        Matrix cMatrix = new Matrix(getRows(), getColumns());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                cMatrix.setValueAt(i, j, matrix[i][j] * value);
            }
        }

        return cMatrix;
    }


    // STATIC METHODS

    public static double determinant(Matrix matrix) {
        if (!matrix.isSquare()) {
            throw new IllegalStateException("Matrix must be square in order to calculate the determinant");
        }

        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }

        if (matrix.size() == 2) {
            return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
        }

        double det = 0.0;

        for (int i = 0; i < matrix.getRows(); i++) {
            det += changeSign(i) * matrix.getValueAt(0, i) *  matrix.subMatrix(0, i).determinant();
        }

        return det;
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix tMatrix = new Matrix(matrix.columns, matrix.rows);
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                tMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }

        return tMatrix;
    }

    public static Matrix subMatrix(Matrix matrix, int row, int col) {
        Matrix sMatrix = new Matrix(matrix.getRows()-1, matrix.getColumns()-1);
        int r = -1;

        for (int i = 0; i < matrix.getRows(); i++) {
            if ( i == row) {
                continue;
            }

            r++;
            int c = -1;
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (j == col) {
                    continue;
                }
                c++;
                sMatrix.setValueAt(r, c, matrix.getValueAt(i, j));
            }
        }

        return sMatrix;
    }

    public static Matrix inverse(Matrix matrix) {
        double det = matrix.determinant();

        if (det == 0.0) {
            throw new IllegalStateException("unable to inverse a singular matrix.");
        }

        Matrix cfMatrix = matrix.cofactor();
        Matrix tMatrix = cfMatrix.transpose();




        return tMatrix.multiply(1.0 / det);
    }

    public static Matrix cofactor(Matrix matrix) {
        Matrix cfMatrix = new Matrix(matrix.getRows(), matrix.getColumns());

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                cfMatrix.setValueAt(i, j, changeSign(i) * changeSign(j) * matrix.subMatrix(i, j).determinant());
            }
        }

        return cfMatrix;
    }

    public static int changeSign(int i) {
        if (i % 2 == 0) {
            return 1;
        }

        return -1;
    }

    public static Matrix add(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalStateException("Both Matrices should have the same dimension");
        }

        Matrix aMatrix = new Matrix(a.getRows(), a.getColumns());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                aMatrix.setValueAt(i, j, a.getValueAt(i, j) + b.getValueAt(i, j));
            }
        }

        return aMatrix;
    }

    public static Matrix substract(Matrix a, Matrix b) {
        return add(a, b.multiply(-1));
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix mMatrix = new Matrix(a.getRows(), b.getColumns());

        for (int i = 0; i < mMatrix.getRows(); i++) {
            for (int j = 0; j < mMatrix.getColumns(); j++) {
                double sum = 0.0;

                for (int k = 0; k < a.getColumns(); k++) {
                    sum += a.getValueAt(i, k) * b.getValueAt(k, j);
                }

                mMatrix.setValueAt(i, j, sum);
            }
        }

        return mMatrix;
    }
}
