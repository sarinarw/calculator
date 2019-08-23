package model;

import org.junit.Test;

public class OptimizedCalculatorTest {

    @Test
    public void testSimpleOrderOfOperations() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1+2*5");
        assert(11 == calculator.getResult());
        calculator.calculate("6 * 11 / 2");
        assert(33 == calculator.getResult());
    }

    @Test
    public void testAllOrderOfOperations() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1 + 2 * 5 + 3 * 3 - 6 / 2");
        assert(17 == calculator.getResult());
        calculator.calculate("6 / 2 * 3 + 1 - 10");
        assert(0 == calculator.getResult());
    }

    @Test
    public void testSimilarOperations() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("2 * 2 + 22 * 22");
        assert(488 == calculator.getResult());
    }

    @Test
    public void testNegativeNumbers() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("-2 * 2 + -3");
        assert(-7 == calculator.getResult());
    }

    @Test
    public void testGetPreviousResult() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1 + 2 + 3");
        calculator.calculate("0 + 1 - 0");
        calculator.calculate("0 + 1 * 100");
        assert(1 == calculator.getPreviousResult(1));
        assert(2 == calculator.getPreviousResults().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEquation() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("1+2*5...");
    }

    @Test(expected = ArithmeticException.class)
    public void testAdditionOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Long.MAX_VALUE + "+1");
    }

    @Test(expected = ArithmeticException.class)
    public void testSubtractionOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Long.MIN_VALUE + "-1");
    }

    @Test(expected = ArithmeticException.class)
    public void testMultiplicationOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Long.MAX_VALUE + "*2");
    }
}