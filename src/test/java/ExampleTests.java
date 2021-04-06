import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Scale;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTests {

    private Calculator calculator;

    @BeforeEach
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Test Multiplication with a nulled a variable")
    public void testMultiplyWithNulledA() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.multiple(null, 1);
        });

        String expectedMessage = "a must be defined";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    @DisplayName("Test Multiplication Accuracy")
    public void testMultiplyAccuracy() {
        int a = 5;
        int b = 5;

        int expected = a*b;
        int actual = calculator.multiple(a, b);

        assertEquals(actual, expected);
    }
}
