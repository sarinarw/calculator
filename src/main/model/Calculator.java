package model;

public class Calculator extends OptimizedCalculator {

    private Long left;
    private Long right;
    private Operator operator;

    public Calculator(String left, String right, String operator) {
        this.left = parseLong(left);
        this.right = parseLong(right);
        this.operator = Operator.getBySymbol(operator)
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + operator));
    }

    public void setLeft(String value) {
        this.left = parseLong(value);
    }

    public void setRight(String value) {
        this.right = parseLong(value);
    }

    public void calculate() {
        calculate(this.left + this.operator.toString() + this.right);
    }

    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected whole number but got: " + value);
        }
    }
}
