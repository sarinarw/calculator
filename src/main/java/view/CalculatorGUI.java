package view;

import controller.CalculatorController;
import model.Operator;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CalculatorGUI {

    private static JTextField equationText;
    private static JTextArea resultText;
    private static JComboBox<String> pastResults;

    private static CalculatorController calculator = new CalculatorController();

    public static void main(String[] args) {
        int FRAME_WIDTH = 400;
        int FRAME_HEIGHT = 500;

        JFrame f = new JFrame();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // PANELS

        JPanel calculatorPanel;
        try {
            Image bgImage = ImageIO.read(new File(System.getProperty("user.dir") + "/src/resources/images/watermelon-simple-bg.png"));
            calculatorPanel = new BackgroundPanel(bgImage);
        } catch (Exception e) { // just don't add the background image if this fails
            calculatorPanel = new JPanel();
        }

        JPanel viewPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel resultAndHistoryPanel = new JPanel();

        viewPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);
        resultAndHistoryPanel.setOpaque(false);

        calculatorPanel.setLayout(new BoxLayout(calculatorPanel, BoxLayout.Y_AXIS));
        viewPanel.setLayout(new GridLayout(2, 1));

        calculatorPanel.add(viewPanel);
        calculatorPanel.add(buttonsPanel);

        // TEXT FIELDS/AREAS

        resultText = new JTextArea();
        resultAndHistoryPanel.add(resultText);
        resultText.setColumns(30);
        resultText.setEditable(false);

        equationText = new JTextField();
        viewPanel.add(equationText);
        equationText.setText("");
        equationText.setColumns(30);
        equationText.addActionListener(new ProduceResultAction());
        viewPanel.add(resultAndHistoryPanel);

        // BUTTONS

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
        JButton multiplyButton = new JButton(Operator.MULTIPLY.toString());
        JButton equalsButton = new JButton("=");

        JButton[] equationAlteringButtons = {b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,addButton,subtractButton,divideButton,multiplyButton};

        buttonsPanel.setLayout(new GridLayout(3, 5));

        Image buttonImg;
        Image buttonImgOnPress;
        try {
            buttonImg = ImageIO.read(new File(System.getProperty("user.dir") + "/src/resources/images/watermelon-seed.png"));
            buttonImgOnPress = ImageIO.read(new File(System.getProperty("user.dir") + "/src/resources/images/watermelon-seed-light.png"));
        } catch (Exception e) {
            // ignore and continue, we just won't use this image
            buttonImg = null;
            buttonImgOnPress = null;
        }
        for (JButton b : equationAlteringButtons) {
            if (buttonImg != null && buttonImgOnPress != null) {
                b.setIcon(new ImageIcon(buttonImg));
                b.setPressedIcon(new ImageIcon(buttonImgOnPress));
                b.setHorizontalTextPosition(JButton.CENTER);
                b.setVerticalTextPosition(JButton.CENTER);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Arial", Font.PLAIN, 20));
            }

            b.addActionListener(new AddToEquationAction());
            buttonsPanel.add(b);
        }


        if (buttonImg != null && buttonImgOnPress != null) {
            equalsButton.setIcon(new ImageIcon(buttonImg));
            equalsButton.setPressedIcon(new ImageIcon(buttonImgOnPress));
            equalsButton.setHorizontalTextPosition(JButton.CENTER);
            equalsButton.setVerticalTextPosition(JButton.CENTER);
            equalsButton.setContentAreaFilled(false);
            equalsButton.setBorderPainted(false);
            equalsButton.setForeground(Color.WHITE);
        }

        equalsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        equalsButton.addActionListener(new ProduceResultAction());

        buttonsPanel.add(subtractButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(multiplyButton);
        buttonsPanel.add(divideButton);
        buttonsPanel.add(equalsButton);

        // OTHER

        pastResults = new JComboBox<>();
        pastResults.addItem("History");
        resultAndHistoryPanel.add(pastResults);

        f.add(calculatorPanel);

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
            if (equationText.getText().length() == 0) {
                return;
            }
            try {
                // process the equation and update the result text
                Double result = calculator.calculate(equationText.getText());
                resultText.setText(String.valueOf(result));

                // update the previous results dropdown list
                String title = pastResults.getItemAt(0);
                pastResults.removeAllItems();
                pastResults.addItem(title);
                for (String s : calculator.pastResults()) {
                    pastResults.addItem(s);
                }
            } catch (Exception e) {
                resultText.setText(e.getMessage());
            } finally {
                equationText.setText("");
            }
        }
    }
}