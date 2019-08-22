package model;

import org.junit.Test;

public class OptimizedCalculatorTest {

    @Test
    public void testSimpleOrderOfOperations() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1+2*5");
        assert(11 == calculator.getResult());
    }

    @Test
    public void testAllOperatorsSingleEquation() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1 + 2 * 5 + 3 * 3 - 2  / 2");
        assert(19 == calculator.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEquation() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1+2*5...");
    }
}
