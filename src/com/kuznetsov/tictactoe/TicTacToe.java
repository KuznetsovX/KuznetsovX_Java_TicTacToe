package com.kuznetsov.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe {

    //components    -->
    JFrame frame = new JFrame();
    JPanel infoPanel = new JPanel();
    JLabel infoPanelTextField = new JLabel();
    JPanel buttonPanel = new JPanel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton();
    Random random = new Random();
    boolean isPlayer1Move;
    boolean isGameOver;
    byte turnCount;

    //action listeners  -->
    ActionListener buttonsActionListener = e -> {
        //search through all buttons on a button panel    -->
        for (JButton button : buttons) {
            //find out if this button was clicked  -->
            if (e.getSource() == button) {
                //X turn    -->
                if (isPlayer1Move) {
                    if (button.getText().isEmpty()) {
                        button.setText("X");
                        button.setForeground(new Color(200, 0, 0));
                        isPlayer1Move = false;
                        turnCount++;
                        infoPanelTextField.setText("\"O\" turn");
                        infoPanelTextField.setForeground(new Color(0, 0, 200));
                        checkWinningConditionsX();
                    }
                }
                //O turn    -->
                else /*if (!isPlayer1Move)*/ {
                    if (button.getText().isEmpty()) {
                        button.setText("O");
                        button.setForeground(new Color(0, 0, 200));
                        isPlayer1Move = true;
                        turnCount++;
                        infoPanelTextField.setText("\"X\" turn");
                        infoPanelTextField.setForeground(new Color(200, 0, 0));
                        checkWinningConditionsO();
                    }
                }


                //draw conditions   -->
                if (turnCount == 9 && !isGameOver) {
                    draw();
                }
            }
        }
    };
    ActionListener restartButtonActionListener = e -> {
        if (e.getSource() == restartButton) {
            start();
        }
    };


    TicTacToe() {
        //frame settings    -->
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(new Color(15, 15, 15));
        frame.setLayout(new BorderLayout(10, 10));


        //info panel settings   -->
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBounds(0, 0, 600, 100);
        infoPanel.add(infoPanelTextField);


        //info panel text field settings (attached to an "info panel") -->
        infoPanelTextField.setBackground(new Color(15, 15, 15));
//        textField.setForeground(new Color(200, 0, 0));
        infoPanelTextField.setHorizontalAlignment(JLabel.CENTER);
        infoPanelTextField.setFont(new Font("Algerian", Font.ITALIC, 50));
//        textField.setText("Tic-Tac-Toe");
        infoPanelTextField.setOpaque(true);


        //buttons panel settings    -->
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBounds(0, 100, 600, 500);


        //buttons settings  -->
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(new Color(225, 225, 225));
            buttons[i].setFont(new Font("Algerian", Font.PLAIN, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(buttonsActionListener);
            buttonPanel.add(buttons[i]);
        }


        //restart button settings   -->
        restartButton.setText("Restart");
        restartButton.setBackground(new Color(225, 225, 225));
        restartButton.setFont(new Font("Algerian", Font.ITALIC, 50));
        restartButton.setFocusable(false);
        restartButton.addActionListener(restartButtonActionListener);


        //adding components to the frame    -->
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(restartButton, BorderLayout.SOUTH);


        //frame settings    -->
        frame.setResizable(true);
        frame.setVisible(true);


        start();
    }

    private void start() {
        //reset the game    -->
        restartButton.setVisible(false);
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(new Color(225, 225, 225));
            button.setEnabled(true);
        }
        isGameOver = false;
        turnCount = 0;

        //decide who goes first   -->
        if (random.nextInt(2) == 0) {
            isPlayer1Move = true;
            infoPanelTextField.setText("\"X\" turn");
            infoPanelTextField.setForeground(new Color(200, 0, 0));
        } else {
            isPlayer1Move = false;
            infoPanelTextField.setText("\"O\" turn");
            infoPanelTextField.setForeground(new Color(0, 0, 200));
        }
    }

    private void checkWinningConditionsX() {
        //horizontal    -->
        if (buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && buttons[2].getText().equals("X")) {
            victoryX(0, 1, 2);
        }
        if (buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && buttons[5].getText().equals("X")) {
            victoryX(3, 4, 5);
        }
        if (buttons[6].getText().equals("X") && buttons[7].getText().equals("X") && buttons[8].getText().equals("X")) {
            victoryX(6, 7, 8);
        }

        //vertical  -->
        if (buttons[0].getText().equals("X") && buttons[3].getText().equals("X") && buttons[6].getText().equals("X")) {
            victoryX(0, 3, 6);
        }
        if (buttons[1].getText().equals("X") && buttons[4].getText().equals("X") && buttons[7].getText().equals("X")) {
            victoryX(1, 4, 7);
        }
        if (buttons[2].getText().equals("X") && buttons[5].getText().equals("X") && buttons[8].getText().equals("X")) {
            victoryX(2, 5, 8);
        }

        //diagonal  -->
        if (buttons[0].getText().equals("X") && buttons[4].getText().equals("X") && buttons[8].getText().equals("X")) {
            victoryX(0, 4, 8);
        }
        if (buttons[2].getText().equals("X") && buttons[4].getText().equals("X") && buttons[6].getText().equals("X")) {
            victoryX(2, 4, 6);
        }
    }

    private void victoryX(int a, int b, int c) {
        buttons[a].setBackground(new Color(200, 0, 0));
        buttons[b].setBackground(new Color(200, 0, 0));
        buttons[c].setBackground(new Color(200, 0, 0));
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        infoPanelTextField.setText("\"X\" wins!");
        infoPanelTextField.setForeground(new Color(200, 0, 0));
        isGameOver = true;
        restartButton.setVisible(true);
    }

    private void checkWinningConditionsO() {
        //horizontal    -->
        if (buttons[0].getText().equals("O") && buttons[1].getText().equals("O") && buttons[2].getText().equals("O")) {
            victoryO(0, 1, 2);
        }
        if (buttons[3].getText().equals("O") && buttons[4].getText().equals("O") && buttons[5].getText().equals("O")) {
            victoryO(3, 4, 5);
        }
        if (buttons[6].getText().equals("O") && buttons[7].getText().equals("O") && buttons[8].getText().equals("O")) {
            victoryO(6, 7, 8);
        }

        //vertical  -->
        if (buttons[0].getText().equals("O") && buttons[3].getText().equals("O") && buttons[6].getText().equals("O")) {
            victoryO(0, 3, 6);
        }
        if (buttons[1].getText().equals("O") && buttons[4].getText().equals("O") && buttons[7].getText().equals("O")) {
            victoryO(1, 4, 7);
        }
        if (buttons[2].getText().equals("O") && buttons[5].getText().equals("O") && buttons[8].getText().equals("O")) {
            victoryO(2, 5, 8);
        }

        //diagonal  -->
        if (buttons[0].getText().equals("O") && buttons[4].getText().equals("O") && buttons[8].getText().equals("O")) {
            victoryO(0, 4, 8);
        }
        if (buttons[2].getText().equals("O") && buttons[4].getText().equals("O") && buttons[6].getText().equals("O")) {
            victoryO(2, 4, 6);
        }
    }

    private void victoryO(int a, int b, int c) {
        buttons[a].setBackground(new Color(0, 0, 200));
        buttons[b].setBackground(new Color(0, 0, 200));
        buttons[c].setBackground(new Color(0, 0, 200));
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        infoPanelTextField.setText("\"O\" wins!");
        infoPanelTextField.setForeground(new Color(0, 0, 200));
        isGameOver = true;
        restartButton.setVisible(true);
    }

    private void draw() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        infoPanelTextField.setText("Draw!");
        infoPanelTextField.setForeground(new Color(200, 200, 200));
        isGameOver = true;
        restartButton.setVisible(true);
    }

}