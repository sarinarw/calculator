package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class OptimizedCalculator {

    int MAX_HISTORY = 10;

    private ArrayList<Operator> supportedOperators = new ArrayList<>();

    Long currentResult = null;
    LinkedList<Long> historicalCalculations = new LinkedList<>();

    public OptimizedCalculator() {
        // Order here matters. The Operators are added to this list in opposite preferential order.
        // i.e. DIVIDE is going to happen before SUBTRACT if they are both part of the equation.
        supportedOperators.add(Operator.ADD);
        supportedOperators.add(Operator.SUBTRACT);
        supportedOperators.add(Operator.MULTIPLY);
        supportedOperators.add(Operator.DIVIDE);
    }

    public void calculate(String equation) {
        // add our last calculated result to our historic results
        if (currentResult != null) {
            if (historicalCalculations.size() >= MAX_HISTORY) {
                historicalCalculations.removeFirst();
            }

            historicalCalculations.add(currentResult);
            currentResult = null;
        }

        // remove whitespace and then make sure the equation only consists of operators and numbers
        equation = equation.replaceAll(" ", "");
        StringBuilder regex = new StringBuilder();
        regex.append("^[");
        for (Operator o : supportedOperators) {
            regex.append(Pattern.quote(o.toString()));
        }
        regex.append("0-9_]+$");
        if (!equation.matches(regex.toString())) {
            throw new IllegalArgumentException("Equation must contain only support operators and whole numbers: " + equation);
        }

        // do the actual processing of the equation
        Long result = calculateHelperV2(equation);
        if (result == null) {
            throw new IllegalArgumentException("Could not produce a result for the given equation: " + equation);
        }

        currentResult = result;
    }

    private Long calculateHelperV2(String equation) {

        if (equation.length() == 0) {
            throw new IllegalArgumentException("Equation string must not be empty");
        }

        // replace subtraction symbols by saying we are adding the left value to a negative right value
        // do not replace if the subtraction symbol is proceeds another operator already
        // i.e. 1-1 -> 1+-1 but 1+-1 -> 1+-1
        System.out.println("Parsing equation: " + equation);

        StringBuilder updatedEquation = new StringBuilder();

        updatedEquation.append(equation.charAt(0));
        for (int i = 1; i < equation.length(); i++) {
            if (String.valueOf(equation.charAt(i)).equals(Operator.SUBTRACT.toString())
                    && Character.isDigit(equation.charAt(i - 1))) {
                updatedEquation.append(Operator.ADD.toString());
            }
            updatedEquation.append(equation.charAt(i));

        }

        equation = updatedEquation.toString();

        System.out.println("Updated subtraction equation: " + equation);

        HashSet<String> operators = new HashSet<>();
        operators.add(Operator.ADD.toString());
        operators.add(Operator.DIVIDE.toString());
        operators.add(Operator.MULTIPLY.toString());

        int index1 = equation.indexOf(Operator.DIVIDE.toString());
        int index2 = equation.indexOf(Operator.MULTIPLY.toString());

        while (index1 > 0 || index2 > 0) {
            int index;
            Operator op;
            if (index2 == -1 || (index1 != -1 && index1 < index2)) {
                // do the first operation
                index = index1;
                op = Operator.DIVIDE;
            } else {
                // do the second operation
                index = index2;
                op = Operator.MULTIPLY;
            }

            equation = updateEquation(equation, index, op, operators);

            index1 = equation.indexOf(Operator.DIVIDE.toString());
            index2 = equation.indexOf(Operator.MULTIPLY.toString());
        }

        // now do all the addition
        while (equation.contains(Operator.ADD.toString())) {
            int index = equation.indexOf(Operator.ADD.toString());
            equation = updateEquation(equation, index, Operator.ADD, operators);
        }

        return Long.parseLong(equation);
    }

    private String updateEquation(String equation, int index, Operator op, HashSet<String> operators) {
        // get right and left numbers of the equation
        String left = getLeft(equation, index, operators);
        String right = getRight(equation, index, operators);

        Long result = doOperation(parseLong(left), parseLong(right), op);
        equation = equation.replaceFirst(Pattern.quote(left + op.toString() + right), String.valueOf(result));

        System.out.println("Calculated: " + left + op.toString() + right + " = " + result);
        System.out.println("Updated Equation: " + equation);

        return equation;
    }

    private String getLeft(String equation, int index, HashSet<String> operators) {
        int l = 1;
        StringBuilder left = new StringBuilder();
        while (index - l >= 0 && !operators.contains(String.valueOf(equation.charAt(index - l)))) {
            left.append(equation.charAt(index - l));
            l++;
        }

        return left.reverse().toString();
    }

    private String getRight(String equation, int index, HashSet<String> operators) {
        int r = 1;
        StringBuilder right = new StringBuilder();
        while (index + r < equation.length() && !operators.contains(String.valueOf(equation.charAt(index + r)))) {
            right.append(equation.charAt(index + r));
            r++;
        }
        return right.toString();
    }

    private Long doOperation(long left, long right, Operator op) {
        Long result = null;
            switch (op) {
                case ADD:
                    result = left + right;
                    if ((left > 0 && right > 0 && result <= 0) || (left < 0 && right < 0 && result >= 0)) {
                        throw new ArithmeticException(left + " and " + right + " cannot be added as it causes overflow");
                    }
                    break;
                case SUBTRACT:
                    result = left - right;
                    if ((left > 0 && right < 0 && result <= 0) || (left < 0 && right > 0 && result >= 0)) {
                        throw new ArithmeticException(left + " cannot subtract " + right + " as it causes overflow");
                    }
                    break;
                case MULTIPLY:
                    result = left * right;
                    if (result / right != left) {
                        throw new ArithmeticException(left + " and " + right + " cannot be multiplied as it causes overflow");
                    }
                    break;
                case DIVIDE: // we only support operations on whole numbers therefore overflow with division cannot happen
                    if (right == 0) {
                        throw new ArithmeticException("Division by zero is invalid: " + left + op.toString() + right);
                    }

                    result = left / right;
                    break;
                default:
                    throw new IllegalArgumentException("Calculations not supported on operator: " + op);
            }
            return result;
        }

    public LinkedList<Long> getPreviousResults() {
        return this.historicalCalculations;
    }

    public Long getPreviousResult(int index) {
        if (index > MAX_HISTORY || index < 1) {
            throw new IllegalArgumentException("Previous result index must be between 1 and " + MAX_HISTORY);
        }

        if (index > historicalCalculations.size()) {
            throw new IllegalArgumentException("There is not yet " + index + " number of past calculations");
        }

        return historicalCalculations.get(historicalCalculations.size() - index);
    }

    public Long getResult() {
        return currentResult;
    }

    Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected whole number but got: " + value);
        }
    }
}
