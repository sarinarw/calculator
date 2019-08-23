package model;

/**
 * Calculator class to satisfy the single-part-only equation requirements in part 1.
 * i.e. this Calculator can only do 1+1, 2-1, etc. NOT 1+1-2/5....
 *
 * Other than that, the historic tracking and supported numbers are shared with OptimizedCalculator,
 * hence the extension of that class. Any other required functions that are not found in this class,
 * but were on the spec for part 1, are in OptimizedCalculator but can be called off Calculator because
 * of the inheritance.
 */
public class Calculator extends OptimizedCalculator {

    private Double left;
    private Double right;
    private Operator operator;

    public Calculator(String left, String right, String operator) {
        setLeft(left);
        setRight(right);
        setOperator(operator);
    }

    public void setLeft(String value) {
        this.left = parseDouble(value);
    }

    public void setRight(String value) {
        this.right = parseDouble(value);
    }

    public void setOperator(String operator) {
        this.operator = Operator.getBySymbol(operator)
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + operator));
    }

    public void calculate() {
        calculate(this.left + this.operator.toString() + this.right);
    }

}
