public class Calculator {
    public int multiple(Integer a, Integer b) {
        if (a == null) {
            throw new IllegalArgumentException("a must be defined");
        }

        if (b == null) {
            throw new IllegalArgumentException("b must be defined");
        }
        return a * b;
    }

    public int add(Integer a, Integer b) {
        if (a == null) {
            throw new IllegalArgumentException("a must be defined");
        }

        if (b == null) {
            throw new IllegalArgumentException("b must be defined");
        }
        return a + b;
    }
}
