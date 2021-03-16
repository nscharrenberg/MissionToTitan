package utils.converter;

public class Scale {
    private double realWorldSize, divisor;
    private double scale;

    public Scale(double realWorldSize, double divisor) {
        this.realWorldSize = realWorldSize;
        this.divisor = divisor;
        scale = realWorldSize/divisor;
    }

    public Scale(String scaleString) {
        String[] split = scaleString.split(":");
        realWorldSize = Double.parseDouble(split[0]);
        divisor = Double.parseDouble(split[1]);
        scale = realWorldSize/divisor;
    }
    public double getRealWorldSize() {
        return realWorldSize;
    }
    public double getDivisor() {
        return divisor;
    }
    public double getScale() {
        return scale;
    }

    public String toString() {
        return realWorldSize + " : "+ divisor;
    }
}
