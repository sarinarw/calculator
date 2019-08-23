package model;

public class Calculator extends OptimizedCalculator {

    private Double left;
    private Double right;
    private Operator operator;

    public Calculator(String left, String right, String operator) {
        this.left = parseDouble(left);
        this.right = parseDouble(right);
        this.operator = Operator.getBySymbol(operator)
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + operator));
    }

    public void setLeft(String value) {
        this.left = parseDouble(value);
    }

    public void setRight(String value) {
        this.right = parseDouble(value);
    }

    public void calculate() {
        calculate(this.left + this.operator.toString() + this.right);
    }

}
