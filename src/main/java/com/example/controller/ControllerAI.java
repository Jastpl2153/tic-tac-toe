package com.example.controller;

import com.example.style.Style;
import com.example.playAI.Minimax;
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
    @FXML
    private Button restart;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    @FXML
    private Label message;

    private final Random random = new Random();
    private ArrayList<Button> buttons;
    private final Minimax MINIMAX = new Minimax();

    private final String playerChoice;
    private final String aiChoice;

    private Style style = new Style();

    public ControllerAI(String playerChoice, String aiChoice) {
        this.playerChoice = playerChoice;
        this.aiChoice = aiChoice;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4,
                button5, button6, button7, button8, button9));
        restart.getStyleClass().add("hover");
        buttons.forEach(button -> {
            style.setButtonHoverDisabled(button);
            setupButton(button);
            button.setFocusTraversable(false);
        });
        newGame();
    }

    private void newGame() {
        restart.setOnMouseClicked(mouseEvent -> {
            buttons.forEach(this::resetButton);
            message.setText("Tic-Tac-Toe");
            pickButton(random.nextInt(9));
        });
    }

    private void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
        style.resetChoice(button);
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            button.setText(playerChoice);
            style.setButtonStyle(button, playerChoice);
            button.setDisable(true);
            makeAIMove();
            checkIfGameIsOver();
        });
    }

    private void makeAIMove() {
        int move = MINIMAX.minimax(getBoardState());
        pickButton(move);
    }

    private void pickButton(int index) {
        buttons.get(index).setText(aiChoice);
        style.setButtonStyle(buttons.get(index), aiChoice);
        buttons.get(index).setDisable(true);
    }

    private State getBoardState() {
        String[] board = new String[9];
        for (int i = 0; i < buttons.size(); i++) {
            board[i] = buttons.get(i).getText();
        }
        return new State(0, board);
    }

    private void checkIfGameIsOver() {
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

            checkLine(line);
        }
    }

    private void checkLine(String line) {
        if (line.equals("XXX") && aiChoice.equals("X") || line.equals("OOO") && aiChoice.equals("O")) {
            gameOver("AI won!");
        } else if (line.equals("XXX") && playerChoice.equals("X") || line.equals("OOO") && playerChoice.equals("O")) {
            gameOver("You won!");
        }
    }

    private void gameOver(String messageText) {
        message.setText(messageText);
        buttons.forEach(button -> button.setDisable(true));
    }
}
