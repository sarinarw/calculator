package model;

import java.util.Optional;

public enum Operator {
    // valid values for operator are: "+", "-", "*", "/"
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public static Optional<Operator> getBySymbol(String symbol) {
        for (Operator op : Operator.values()) {
            if (op.symbol.equals((symbol))) {
                return Optional.of(op);
            }
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
