package simplejavacalculator;

import javax.swing.*;
import java.awt.*;

public class UI {

    private final JFrame frame;
    private final JPanel panel;
    private final JTextField text;
    private final Calculator calc;
    private boolean decimalPressed = false;
    private boolean startNewNumber = true; // নতুন ইনপুট ফিল্ড হ্যান্ডেল করার জন্য ফ্লাগ

    private final JButton[] nums = new JButton[10];
    private final JButton btnAdd, btnMinus, btnMultiply, btnDivide, btnEqual, btnCancel,
            btnSquare, btnSquareRoot, btnOneDividedBy, btnCos, btnSin, btnTan, btnLog, btnAbs, btnDecimal;

    public UI() {
        frame = new JFrame("Simple Java Calculator");
        frame.setSize(300, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        calc = new Calculator();
        text = new JTextField(20);
        text.setHorizontalAlignment(JTextField.RIGHT);
        text.setEditable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));

        for (int i = 0; i < 10; i++) {
            nums[i] = new JButton(String.valueOf(i));
        }

        btnAdd = new JButton("+");
        btnMinus = new JButton("-");
        btnMultiply = new JButton("*");
        btnDivide = new JButton("/");
        btnEqual = new JButton("=");
        btnCancel = new JButton("C");
        btnSquare = new JButton("x*x");
        btnSquareRoot = new JButton("sqrt");
        btnOneDividedBy = new JButton("1/x");
        btnCos = new JButton("cos");
        btnSin = new JButton("sin");
        btnTan = new JButton("tan");
        btnLog = new JButton("log");
        btnAbs = new JButton("abs");
        btnDecimal = new JButton(".");

        panel.add(text);
        
        // Row 1
        panel.add(btnCancel);
        panel.add(btnSquare);
        panel.add(btnSquareRoot);
        panel.add(btnDivide);

        // Row 2
        panel.add(nums[7]);
        panel.add(nums[8]);
        panel.add(nums[9]);
        panel.add(btnMultiply);

        // Row 3
        panel.add(nums[4]);
        panel.add(nums[5]);
        panel.add(nums[6]);
        panel.add(btnMinus);

        // Row 4
        panel.add(nums[1]);
        panel.add(nums[2]);
        panel.add(nums[3]);
        panel.add(btnAdd);

        // Row 5
        panel.add(nums[0]);
        panel.add(btnDecimal);
        panel.add(btnOneDividedBy);
        panel.add(btnEqual);

        // Row 6
        panel.add(btnSin);
        panel.add(btnCos);
        panel.add(btnTan);
        panel.add(btnLog);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(text, BorderLayout.NORTH);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            nums[i].addActionListener(e -> {
                if (startNewNumber) {
                    text.setText(String.valueOf(index));
                    startNewNumber = false;
                } else {
                    text.setText(text.getText() + index);
                }
            });
        }

        btnDecimal.addActionListener(e -> {
            if (startNewNumber) {
                text.setText("0.");
                startNewNumber = false;
                decimalPressed = true;
            } else if (!decimalPressed) {
                text.setText(text.getText() + ".");
                decimalPressed = true;
            }
        });

        btnCancel.addActionListener(e -> {
            text.setText("");
            decimalPressed = false;
            startNewNumber = true;
            calc.reset();
        });

        btnAdd.addActionListener(e -> handleBiOp(Calculator.BiOperatorModes.add));
        btnMinus.addActionListener(e -> handleBiOp(Calculator.BiOperatorModes.minus));
        btnMultiply.addActionListener(e -> handleBiOp(Calculator.BiOperatorModes.multiply));
        btnDivide.addActionListener(e -> handleBiOp(Calculator.BiOperatorModes.divide));
        
        btnEqual.addActionListener(e -> {
            if (!text.getText().isEmpty()) {
                Double val = Double.parseDouble(text.getText());
                Double res = calc.calculateEqual(val);
                setTextOrNaN(res);
                decimalPressed = false;
                startNewNumber = true;
            }
        });

        btnSquare.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.square));
        btnSquareRoot.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.squareRoot));
        btnOneDividedBy.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.oneDividedBy));
        btnSin.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.sin));
        btnCos.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.cos));
        btnTan.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.tan));
        btnLog.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.log));
        btnAbs.addActionListener(e -> handleMonoOp(Calculator.MonoOperatorModes.abs));

        frame.setVisible(true);
    }

    private void handleBiOp(Calculator.BiOperatorModes mode) {
        if (!text.getText().isEmpty()) {
            Double val = Double.parseDouble(text.getText());
            Double res = calc.calculateBi(mode, val);
            if (!res.isNaN()) {
                setTextOrNaN(res);
            }
            decimalPressed = false;
            startNewNumber = true;
        }
    }

    private void handleMonoOp(Calculator.MonoOperatorModes mode) {
        if (!text.getText().isEmpty()) {
            Double val = Double.parseDouble(text.getText());
            Double res = calc.calculateMono(mode, val);
            setTextOrNaN(res);
            decimalPressed = false;
            startNewNumber = true;
        }
    }

    private void setTextOrNaN(Double val) {
        if (val.isNaN()) {
            text.setText("NaN");
        } else {
            // যদি সংখ্যাটি পূর্ণসংখ্যা হয়, তাহলে দশমিক বাদ দিয়ে দেখানো ভালো (ঐচ্ছিক, তবে মান ঠিক রাখতে স্ট্রিং রাখলাম)
            if (val == val.longValue()) {
                text.setText(String.valueOf(val.longValue()));
            } else {
                text.setText(String.valueOf(val));
            }
        }
    }
}