import controller.CalculatorController;
import model.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI {

    static int FRAME_WIDTH = 400;
    static int FRAME_HEIGHT = 500;

    static JTextField equationText, resultText;
    static JTextArea pastResultsText;
    static JFrame f;

    static CalculatorController calculator = new CalculatorController();

    public static void main(String[] args) {

        f = new JFrame();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setLayout(new GridLayout(1, 2));

        JPanel calculatorPanel = new JPanel();
        JPanel viewPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel pastResultsPanel = new JPanel();

        calculatorPanel.add(viewPanel);
        calculatorPanel.add(buttonsPanel);

        pastResultsText = new JTextArea();
        pastResultsPanel.add(pastResultsText);

        equationText = new JTextField();
        viewPanel.add(equationText);
        equationText.setText("");
        equationText.setColumns(30);
        equationText.setEditable(false);

        resultText = new JTextField();
        viewPanel.add(resultText);
        resultText.setColumns(30);
        resultText.setEditable(false);

        calculatorPanel.setLayout(new BoxLayout(calculatorPanel, BoxLayout.Y_AXIS));

        JButton b0 = new JButton("0");
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
        JButton b6 = new JButton("6");
        JButton b7 = new JButton("7");
        JButton b8 = new JButton("8");
        JButton b9 = new JButton("9");

        JButton addButton = new JButton(Operator.ADD.toString());
        JButton subtractButton = new JButton(Operator.SUBTRACT.toString());
        JButton divideButton = new JButton(Operator.DIVIDE.toString());
        JButton multiplyButton = new JButton(Operator.MUTIPLY.toString());
        JButton equalsButton = new JButton("=");

        buttonsPanel.setLayout(new GridLayout(3, 5));

        b0.addActionListener(new AddToEquationAction());
        b1.addActionListener(new AddToEquationAction());
        b2.addActionListener(new AddToEquationAction());
        b3.addActionListener(new AddToEquationAction());
        b4.addActionListener(new AddToEquationAction());
        b5.addActionListener(new AddToEquationAction());
        b6.addActionListener(new AddToEquationAction());
        b7.addActionListener(new AddToEquationAction());
        b8.addActionListener(new AddToEquationAction());
        b9.addActionListener(new AddToEquationAction());
        addButton.addActionListener(new AddToEquationAction());
        subtractButton.addActionListener(new AddToEquationAction());
        multiplyButton.addActionListener(new AddToEquationAction());
        divideButton.addActionListener(new AddToEquationAction());
        equalsButton.addActionListener(new ProduceResultAction());

        buttonsPanel.add(b0);
        buttonsPanel.add(b1);
        buttonsPanel.add(b2);
        buttonsPanel.add(b3);
        buttonsPanel.add(b4);
        buttonsPanel.add(b5);
        buttonsPanel.add(b6);
        buttonsPanel.add(b7);
        buttonsPanel.add(b8);
        buttonsPanel.add(b9);
        buttonsPanel.add(b0);
        buttonsPanel.add(subtractButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(multiplyButton);
        buttonsPanel.add(divideButton);
        buttonsPanel.add(equalsButton);

        f.add(calculatorPanel);
        f.add(pastResultsPanel);

        f.pack();

        f.setVisible(true);
    }

    private static class AddToEquationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            equationText.setText(equationText.getText() + ((JButton)e.getSource()).getText());
        }
    }

    private static class ProduceResultAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Long result = calculator.calculate(equationText.getText());
                resultText.setText(String.valueOf(result));
                String resultsStr = String.join("\n", calculator.pastResults());
                pastResultsText.setText(resultsStr);
            } catch (Exception e) {
                resultText.setText(e.getMessage());
            } finally {
                equationText.setText("");
            }
        }
    }
}