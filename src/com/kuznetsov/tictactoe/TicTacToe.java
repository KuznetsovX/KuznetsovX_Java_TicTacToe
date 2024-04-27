package com.kuznetsov.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe {

    //components    -->
    private final JLabel infoPanelTextField = new JLabel();
    private final JButton[] buttons = new JButton[9];
    private final JButton restartButton = new JButton();
    private final Random random = new Random();
    private boolean isPlayer1Move;
    private boolean isGameOver;
    private byte turnCount;
    private byte gamesPlayed;

    TicTacToe() {
        //frame settings    -->
        JFrame frame = new JFrame();
        ImageIcon image = new ImageIcon("src/com/kuznetsov/tictactoe/TicTacToeIcon.png");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tic-Tac-Toe");
        frame.setIconImage(image.getImage());
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(new Color(15, 15, 15));
        frame.setLayout(new BorderLayout(10, 10));


        //info panel settings   -->
        JPanel infoPanel = new JPanel();
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBounds(0, 100, 600, 500);


        //buttons settings  -->
        ActionListener buttonsActionListener = e -> {
            //search through all buttons on a button panel    -->
            for (JButton button : buttons) {
                //find out if this button was clicked  -->
                if (e.getSource() == button) {
                    if (isPlayer1Move) {
                        makePlayer1Move(button);
                    } else {
                        makePlayer2Move(button);
                    }

                    //draw conditions   -->
                    if (turnCount == 9 && !isGameOver) {
                        declareDraw();
                    }
                }
            }
        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(new Color(225, 225, 225));
            buttons[i].setFont(new Font("Algerian", Font.PLAIN, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(buttonsActionListener);
            buttonPanel.add(buttons[i]);
        }


        //restart button settings   -->
        ActionListener restartButtonActionListener = e -> {
            if (e.getSource() == restartButton) {
                startNewGame();
            }
        };
        restartButton.setText("Restart");
        restartButton.setBackground(new Color(225, 225, 225));
        restartButton.setFont(new Font("Algerian", Font.ITALIC, 50));
        restartButton.setFocusable(false);
        restartButton.addActionListener(restartButtonActionListener);
        restartButton.setVisible(false);


        //adding components to the frame    -->
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(restartButton, BorderLayout.SOUTH);


        //frame settings    -->
        frame.setResizable(true);
        frame.setVisible(true);


        startNewGame();
    }

    private void startNewGame() {
        if (gamesPlayed != 0) {
            resetGame();
        }

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

    private void makePlayer1Move(JButton button) {
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

    private void makePlayer2Move(JButton button) {
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

    private void checkWinningConditionsX() {
        //horizontal    -->
        if (buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && buttons[2].getText().equals("X")) {
            declareVictoryX(0, 1, 2);
        }
        if (buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && buttons[5].getText().equals("X")) {
            declareVictoryX(3, 4, 5);
        }
        if (buttons[6].getText().equals("X") && buttons[7].getText().equals("X") && buttons[8].getText().equals("X")) {
            declareVictoryX(6, 7, 8);
        }

        //vertical  -->
        if (buttons[0].getText().equals("X") && buttons[3].getText().equals("X") && buttons[6].getText().equals("X")) {
            declareVictoryX(0, 3, 6);
        }
        if (buttons[1].getText().equals("X") && buttons[4].getText().equals("X") && buttons[7].getText().equals("X")) {
            declareVictoryX(1, 4, 7);
        }
        if (buttons[2].getText().equals("X") && buttons[5].getText().equals("X") && buttons[8].getText().equals("X")) {
            declareVictoryX(2, 5, 8);
        }

        //diagonal  -->
        if (buttons[0].getText().equals("X") && buttons[4].getText().equals("X") && buttons[8].getText().equals("X")) {
            declareVictoryX(0, 4, 8);
        }
        if (buttons[2].getText().equals("X") && buttons[4].getText().equals("X") && buttons[6].getText().equals("X")) {
            declareVictoryX(2, 4, 6);
        }
    }

    private void declareVictoryX(int a, int b, int c) {
        //disable all buttons   -->
        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        //set new text on the information panel -->
        infoPanelTextField.setText("\"X\" wins!");
        infoPanelTextField.setForeground(new Color(200, 0, 0));

        //paint all the buttons of a winning combination    -->
        buttons[a].setBackground(new Color(200, 0, 0));
        buttons[b].setBackground(new Color(200, 0, 0));
        buttons[c].setBackground(new Color(200, 0, 0));

        setGameOverSettings();
    }

    private void checkWinningConditionsO() {
        //horizontal    -->
        if (buttons[0].getText().equals("O") && buttons[1].getText().equals("O") && buttons[2].getText().equals("O")) {
            declareVictoryO(0, 1, 2);
        }
        if (buttons[3].getText().equals("O") && buttons[4].getText().equals("O") && buttons[5].getText().equals("O")) {
            declareVictoryO(3, 4, 5);
        }
        if (buttons[6].getText().equals("O") && buttons[7].getText().equals("O") && buttons[8].getText().equals("O")) {
            declareVictoryO(6, 7, 8);
        }

        //vertical  -->
        if (buttons[0].getText().equals("O") && buttons[3].getText().equals("O") && buttons[6].getText().equals("O")) {
            declareVictoryO(0, 3, 6);
        }
        if (buttons[1].getText().equals("O") && buttons[4].getText().equals("O") && buttons[7].getText().equals("O")) {
            declareVictoryO(1, 4, 7);
        }
        if (buttons[2].getText().equals("O") && buttons[5].getText().equals("O") && buttons[8].getText().equals("O")) {
            declareVictoryO(2, 5, 8);
        }

        //diagonal  -->
        if (buttons[0].getText().equals("O") && buttons[4].getText().equals("O") && buttons[8].getText().equals("O")) {
            declareVictoryO(0, 4, 8);
        }
        if (buttons[2].getText().equals("O") && buttons[4].getText().equals("O") && buttons[6].getText().equals("O")) {
            declareVictoryO(2, 4, 6);
        }
    }

    private void declareVictoryO(int a, int b, int c) {
        //disable all buttons   -->
        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        //set new text on the information panel -->
        infoPanelTextField.setText("\"O\" wins!");
        infoPanelTextField.setForeground(new Color(0, 0, 200));

        //paint all the buttons of a winning combination    -->
        buttons[a].setBackground(new Color(0, 0, 200));
        buttons[b].setBackground(new Color(0, 0, 200));
        buttons[c].setBackground(new Color(0, 0, 200));

        setGameOverSettings();
    }

    private void declareDraw() {
        //disable all buttons   -->
        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        //set new text on the information panel -->
        infoPanelTextField.setText("Draw!");
        infoPanelTextField.setForeground(new Color(200, 200, 200));

        setGameOverSettings();
    }

    private void setGameOverSettings() {
        isGameOver = true;
        gamesPlayed++;
        restartButton.setVisible(true);
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(new Color(225, 225, 225));
            button.setEnabled(true);
        }
        isGameOver = false;
        turnCount = 0;
        restartButton.setVisible(false);
    }

}