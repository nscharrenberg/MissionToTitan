package org.um.dke.titan.physicsold.ode.utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.RotationMatrix;

import static org.junit.Assert.assertEquals;

/**
 * Test strategy
 * input space:
 * rotation: 0 - 360
 * x: real numbers
 * y: real numbers
 *
 * rotation: 0, 1, 90, 214, 360
 * x: big negative, small negative, -1, 0, 1, small pos, big pos
 * y: big negative, small negative, -1, 0, 1, small pos, big pos
 *
 * cases:
 *  1. r:0, x: b n, y: -1
 *  2. r: 1 x: s n, y: b p
 *  3. r: 90 x: -1 y: s n
 *  4. r: 214 x: 0 y: s p
 *  5. r: 360 x: 1 y: b n
 *  6. r: 69 x: s p y: 0
 *  7. r 180 w b p y: 1
 */
@RunWith(GdxTestRunner.class)
public class RotationMatrixTest {
    public static final double DELTA = 1e-2;
    //rotation testing
    /**
     * r:0, x: b n, y: -1
     */
    @Test
    public void testCaseOne(){
        double degrees = 0;
        double x = -872341;
        double y = -1;
        Vector3dInterface newV = RotationMatrix.rotate(degrees,x,y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = -872341;
        double corY = -1;

        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }

    @Test
    /**
     * r: 1 x: s n, y: b p
     */
    public void testCaseTwo(){
        double degrees = 1;
        double x = -20;
        double y = 873000;
        Vector3dInterface newV = RotationMatrix.rotate(degrees,x,y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = 15215.953865845378;
        double corY = 872867.3869196583;

        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }
    @Test
    /**
     * r: 90 x: -1 y: s n
     */
    public void testCaseThree(){
        double degrees = 90;
        double x = -1;
        double y = -37;
        Vector3dInterface newV = RotationMatrix.rotate(degrees,x,y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = -37;
        double corY = 1;

        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }

    /*
     * r: 214 x: 0 y: s p
     */
    @Test
    public void testCaseFour() {
        double degrees = 214;
        double x = 0;
        double y = 37;
        Vector3dInterface newV = RotationMatrix.rotate(degrees, x, y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = -20.7;
        double corY = -30.67439018453655;
        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }

    /*
     * r: 360 x: 1 y: b n
     */
    @Test
    public void testCaseFive(){
        double degrees = 360;
        double x = 1;
        double y = -324532457;
        Vector3dInterface newV = RotationMatrix.rotate(degrees, x, y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = 1;
        double corY = -324532457;
        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }

    /*
     * r: 69 x: s p y: 0
     */
    @Test
    public void testCaseSix(){
        double degrees = 69;
        double x = 18;
        double y = 0;
        Vector3dInterface newV = RotationMatrix.rotate(degrees, x, y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = 6.45;
        double corY = -16.8;
        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }


    /*
     * r 180 w b p y: 1
     */
    @Test
    public void testCaseSeven(){
        double degrees = 180;
        double x = 456756;
        double y = 1;
        Vector3dInterface newV = RotationMatrix.rotate(degrees, x, y);
        double newX = newV.getX();
        double newY = newV.getY();
        double corX = -456756;
        double corY = -1;
        assertEquals(corX, newX, DELTA);
        assertEquals(corY, newY, DELTA);
    }
    //Rotation matrix testing
    @Test
    public void testRMCaseOne(){
        double degrees = 0* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{1,0},{0,1}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
    @Test
    public void testRMCaseTwo(){
        double degrees = 1* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{1,0.0175},{-0.0175,1}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
    @Test
    public void testRMCaseThree(){
        double degrees = 90* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{0,1},{-1,0}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }

    @Test
    public void testRMCaseFour(){
        double degrees = 214* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{-0.829,-0.559},{0.559,-0.829}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
    @Test
    public void testRMCaseFive(){
        double degrees = 360* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{1,0},{0,1}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
    @Test
    public void testRMCaseSix(){
        double degrees = 69* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{0.358,0.934},{-0.934,0.358}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
    @Test
    public void testRMCaseSeven(){
        double degrees = 180* Math.PI/180.0;
        double[][] m = RotationMatrix.rotationMatrix(degrees);
        double[][] corM = {{-1,0},{0,-1}};
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                assertEquals(corM[i][j], m[i][j], DELTA);
            }
        }
    }
}
