package model;

import org.junit.Test;

public class OptimizedCalculatorTest {

    @Test
    public void testNoOperators() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("6");
        assert(6 == calculator.getResult());
    }

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
    public void testSubtractingNegative() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("2 - -3");
        assert(5 == calculator.getResult());
    }

    @Test
    public void testGetPreviousResult() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        for (int i = 0; i < calculator.MAX_HISTORY + 2; i++) {
            calculator.calculate("" + i);
        }
        assert(calculator.MAX_HISTORY + 1 == calculator.getResult());
        assert(calculator.MAX_HISTORY == calculator.getPreviousResult(1));
        assert(1 == calculator.getPreviousResult(calculator.MAX_HISTORY));
        assert(calculator.MAX_HISTORY == calculator.getPreviousResults().size());
    }

    @Test
    public void testGetPreviousResultNonexistant() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        assert(null == calculator.getPreviousResult(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPreviousResultInvalidIndexSmall() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.getPreviousResult(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPreviousResultInvalidIndexLarge() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.getPreviousResult(calculator.MAX_HISTORY + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEquation() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("fdafsafds");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEquation() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate("        ");
    }

    @Test(expected = ArithmeticException.class)
    public void testAdditionOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Double.MAX_VALUE + "+" + Double.MAX_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void testSubtractionOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate((-1 * Double.MAX_VALUE) + "-" + (1 * Double.MAX_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void testMultiplicationOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Double.MAX_VALUE + "*2");
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionOverflow() {
        OptimizedCalculator calculator = new OptimizedCalculator();
        calculator.calculate(Double.MAX_VALUE + "/0.2");
    }
}

