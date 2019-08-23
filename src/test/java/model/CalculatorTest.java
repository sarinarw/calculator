package model;

import model.Calculator;
import junit.framework.*;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testAddition() {
        Calculator calculator = new Calculator("9", "1", "+");
        calculator.calculate();
        assert(10 == calculator.getResult());
    }

    @Test
    public void testSubtraction() {
        Calculator calculator = new Calculator("1", "7", "-");
        calculator.calculate();
        assert(-6 == calculator.getResult());
    }

    @Test
    public void testMultiplication() {
        Calculator calculator = new Calculator("8", "8", "*");
        calculator.calculate();
        assert(64 == calculator.getResult());
    }

    @Test
    public void testDivision() {
        Calculator calculator = new Calculator("12", "4", "/");
        calculator.calculate();
        assert(3 == calculator.getResult());
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        Calculator calculator = new Calculator("256", "0", "/");
        calculator.calculate();
    }

    @Test
    public void testSetters() {
        Calculator calculator = new Calculator("1", "2", "-");
        calculator.setLeft("2");
        calculator.setRight("1");
        calculator.calculate();
        assert(1 == calculator.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperator() {
        new Calculator("9", "1", "#");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLeft() {
        new Calculator("", "423", "+");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRight() {
        new Calculator("14", "foo", "+");
    }
}
