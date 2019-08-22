package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class OptimizedCalculator {

    int MAX_HISTORY = 10;

    private ArrayList<Operator> orderedSupportedOperators = new ArrayList<>();

    Long currentResult = null;
    LinkedList<Long> historicalCalculations = new LinkedList<>();

    public OptimizedCalculator() {
        // Order here matters. The Operators are added to this list in opposite preferential order.
        // i.e. DIVIDE is going to happen before SUBTRACT if they are both part of the equation.
        orderedSupportedOperators.add(Operator.ADD);
        orderedSupportedOperators.add(Operator.SUBTRACT);
        orderedSupportedOperators.add(Operator.MUTIPLY);
        orderedSupportedOperators.add(Operator.DIVIDE);
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
        for (Operator o : orderedSupportedOperators) {
            regex.append(Pattern.quote(o.toString()));
        }
        regex.append("0-9_]+$");
        if (!equation.matches(regex.toString())) {
            throw new IllegalArgumentException("Equation must contain only support operators and whole numbers: " + equation);
        }

        // do the actual processing of the equation
        Long result = calculateHelper(equation);
        if (result == null) {
            throw new IllegalArgumentException("Could not produce a result for the given equation: " + equation);
        }

        currentResult = result;
    }

    private Long calculateHelper(String equation) {

        System.out.println("Considering equation: " + equation);
        try {
            return Long.parseLong(equation);
        } catch (NumberFormatException e) {
            Long total = null;
            for (Operator op : this.orderedSupportedOperators) {
                System.out.println("Splitting by operator: " + op.toString());
                String[] opSplit = equation.split(Pattern.quote(op.toString()));
                for (int i = 1; i < opSplit.length; i++) {

                    System.out.println("Parsing: " + opSplit[i - 1] + op.toString() + opSplit[i]);
                    Long left = calculateHelper(opSplit[i - 1]);
                    Long right = calculateHelper(opSplit[i]);

                    if (left == null || right == null) {
                        throw new RuntimeException("A piece of the equation was too large of a value to parse: " + equation);
                    }

                    long result;
                    switch (op) {
                        case ADD:
                            result = left + right;
                            if (result - right != left) {
                                throw new RuntimeException(left + " and " + right + " cannot be added as it causes overflow");
                            }
                            break;
                        case SUBTRACT:
                            result = left - right;
                            if (result + right != left) {
                                throw new RuntimeException(left + " cannot subtract " + right + " as it causes overflow");
                            }
                            break;
                        case MUTIPLY:
                            result = left * right;
                            if (result / right != left) {
                                throw new RuntimeException(left + " and " + right + " cannot be multiplied as it causes overflow");
                            }
                            break;
                        case DIVIDE:
                            double rawDivision = left / (double) right;
                            if (rawDivision * right != left) {
                                throw new RuntimeException(left + " cannot be divided by " + right + " as it causes overflow");
                            }
                            result = left / right;
                            break;
                        default:
                            throw new IllegalArgumentException("Calculations not supported on operator: " + op);
                    }

                    opSplit[i] = String.valueOf(result);
                    total = result;
                    System.out.println("Current Result: " + total);
                }
                if (total != null)
                    return total;
            }
            return total;
        }
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

        return historicalCalculations.get(historicalCalculations.size() - index - 1);
    }

    public Long getResult() {
        return currentResult;
    }
}
