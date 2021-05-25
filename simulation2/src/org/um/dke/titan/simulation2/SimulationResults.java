package org.um.dke.titan.simulation2;

public class SimulationResults {
    private double minDistanceToTitan;
    private int minTimeToTitan;

    private double minDistanceToEarth;
    private int minTimeToEarth;

    public double getMinDistanceToTitan() {
        return minDistanceToTitan;
    }

    public void setMinDistanceToTitan(double minDistanceToTitan) {
        this.minDistanceToTitan = minDistanceToTitan;
    }

    public double getMinDistanceToEarth() {
        return minDistanceToEarth;
    }

    public void setMinDistanceToEarth(double minDistanceToEarth) {
        this.minDistanceToEarth = minDistanceToEarth;
    }

    public int getMinTimeToTitan() {
        return minTimeToTitan;
    }

    public void setMinTimeToTitan(int minTimeToTitan) {
        this.minTimeToTitan = minTimeToTitan;
    }

    public int getMinTimeToEarth() {
        return minTimeToEarth;
    }

    public void setMinTimeToEarth(int minTimeToEarth) {
        this.minTimeToEarth = minTimeToEarth;
    }
}
