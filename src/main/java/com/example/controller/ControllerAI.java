package com.example.controller;

import com.example.playAI.AdversarialSearch;
import com.example.playAI.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class ControllerAI implements Initializable {
    private final String playerChoice;
    private final String aiChoice;
    Random random = new Random();
    ArrayList<Button> buttons;
    AdversarialSearch ticTacToeAI = new AdversarialSearch();
    @FXML
    private Button restart;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Label message;

    public ControllerAI(String playerChoice) {
        this.playerChoice = playerChoice;
        aiChoice = playerChoice.equals("X") ? "O" : "X";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4,
                button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            setupButton(button);
            button.setFocusTraversable(false);
        });
        newGame();
    }

    public void newGame() {
        restart.setOnMouseClicked(mouseEvent -> {
            buttons.forEach(this::resetButton);
            message.setText("Tic-Tac-Toe");
            pickButton(random.nextInt(9));
        });
    }

    public void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            button.setText(playerChoice);
            button.setDisable(true);
            makeAIMove();
            checkIfGameIsOver();
        });
    }

    public void makeAIMove() {
        int move = ticTacToeAI.minimax(getBoardState());
        pickButton(move);
    }

    private void pickButton(int index) {
        buttons.get(index).setText(aiChoice);
        buttons.get(index).setDisable(true);
    }

    public State getBoardState() {
        String[] board = new String[9];

        for (int i = 0; i < buttons.size(); i++) {
            board[i] = buttons.get(i).getText();
        }

        return new State(0, board);
    }

    public void checkIfGameIsOver() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if ((line.equals("XXX") && aiChoice.equals("X")) || (line.equals("OOO") && aiChoice.equals("O"))) {
                message.setText("AI won!");
                buttons.forEach(button -> button.setDisable(true));
            } else if ((line.equals("XXX") && playerChoice.equals("X")) || (line.equals("OOO") && playerChoice.equals("O"))) {
                message.setText("You won!");
                buttons.forEach(button -> button.setDisable(true));
            }
        }
    }
}
