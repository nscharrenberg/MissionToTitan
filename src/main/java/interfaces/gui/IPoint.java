package interfaces.gui;

import interfaces.Vector3dInterface;

public interface IPoint {
    Vector3dInterface getCoordinates();

    default double distanceTo(IPoint other) {
        return Math.sqrt(Math.pow(getCoordinates().getX() - other.getCoordinates().getX(), 2) + Math.pow(getCoordinates().getY() - other.getCoordinates().getY(), 2) + Math.pow(getCoordinates().getZ() - other.getCoordinates().getZ(), 2));
    }

    default double degreeTo(IPoint other) {
        double degree;
        double fullCircle = 2 * Math.PI;

        if (distanceTo(other) == 0) {
            degree = 0;
        } else if (getCoordinates().getX() < other.getCoordinates().getX()) {
            degree = Math.acos((other.getCoordinates().getX() - getCoordinates().getX()) / distanceTo(other));
        } else {
            degree = fullCircle - Math.acos((other.getCoordinates().getX() - getCoordinates().getX()) / distanceTo(other));
        }

        if (degree >= fullCircle) {
            degree -= fullCircle;
        }

        return degree;
    }

    default boolean isSamePosition(IPoint other) {
        return getCoordinates().getX() == other.getCoordinates().getX()
                && getCoordinates().getY() == other.getCoordinates().getY()
                && getCoordinates().getZ() == other.getCoordinates().getZ();
    }
}
