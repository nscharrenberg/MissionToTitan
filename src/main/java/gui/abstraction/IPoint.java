package gui.abstraction;

public interface IPoint {
    double getX();
    double getY();
    double getZ();
    void setX(double val);
    void setY(double val);
    void setZ(double val);
    IPoint clone();

    /**
     * Calculates the degree at which the point is being drawn
     * @param other
     * @return
     */
    default double degreeTo(IPoint other) {
        double degree;

        if (distanceTo(other) == 0) {
            degree = 0;
        } else if (getY() < other.getY()) {
            degree = degreeWave(other);
        } else {
            degree = 2 * Math.PI - degreeWave(other);
        }

        // Over 1 rotation, so just take degree -360 degrees (same location)
        if (degree >= 2 * Math.PI) {
            degree -= 2 * Math.PI;
        }

        return degree;
    }

    default double degreeWave(IPoint other) {
        return Math.acos((other.getX() - getX()) / distanceTo(other));
    }

    /**
     * Calculuate the distance from one point to another point.
     * @param other
     * @return
     */
    default double distanceTo(IPoint other) {
        return Math.sqrt(
                (getX() - other.getX())
                * (getX() - other.getX())
                + (getY() - other.getY())
                * (getY() - other.getY())
                + (getZ() - other.getZ())
                * (getZ() - other.getZ())
        );
    }

    /**
     * Check if the position of the 2 points are the same.
     * @param other
     * @return
     */
    default boolean isSamePosition(IPoint other) {
        return getX() == other.getX()
                && getY() == other.getY()
                && getZ() == other.getZ();
    }
}
