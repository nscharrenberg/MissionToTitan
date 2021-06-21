package org.um.dke.titan.repositories;

import org.um.dke.titan.interfaces.Vector3dInterface;

public class TestRepository {
    private boolean init = false;
    private double lowestNorm;
    private Vector3dInterface closestCoordinates;
    private double closestError;
    private Vector3dInterface closestFxPrev;
    private Vector3dInterface closestFx1;

    public void checkAndPrint(double norm, Vector3dInterface coordinates, double error, Vector3dInterface FxPrev, Vector3dInterface Fx1) {
        if (norm < lowestNorm || !init) {
            if (norm < 0 && norm > norm + 2575.5e3) {
                return;
            }

            this.lowestNorm = norm;
            this.closestCoordinates = coordinates;
            this.closestError = error;
            this.closestFxPrev = FxPrev;
            this.closestFx1 = Fx1;
            this.init = true;

            System.out.printf("F(xPrev) = %s - F(x1) = %s - norm: %s, Error: %s, x1: %s%n", FxPrev, Fx1, norm, error, coordinates);
        }
    }

    public double getLowestNorm() {
        return lowestNorm;
    }

    public void setLowestNorm(double lowestNorm) {
        this.lowestNorm = lowestNorm;
    }

    public Vector3dInterface getClosestCoordinates() {
        return closestCoordinates;
    }

    public void setClosestCoordinates(Vector3dInterface closestCoordinates) {
        this.closestCoordinates = closestCoordinates;
    }

    public double getClosestError() {
        return closestError;
    }

    public void setClosestError(double closestError) {
        this.closestError = closestError;
    }

    public Vector3dInterface getClosestFxPrev() {
        return closestFxPrev;
    }

    public void setClosestFxPrev(Vector3dInterface closestFxPrev) {
        this.closestFxPrev = closestFxPrev;
    }
}
