package controller;

import model.OptimizedCalculator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CalculatorController {

    OptimizedCalculator calculator;

    public CalculatorController() {
        this.calculator = new OptimizedCalculator();
    }

    public Long calculate(String equation) {
        this.calculator.calculate(equation);
        return this.calculator.getResult();
    }

    public List<String> pastResults() {
        List<String> results = this.calculator.getPreviousResults().stream().map(String::valueOf).collect(Collectors.toList());
        Collections.reverse(results);
        return results;
    }
}
