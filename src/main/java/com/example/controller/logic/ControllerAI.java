package com.example.controller.logic;

import com.example.playAI.Minimax;
import com.example.playAI.State;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ControllerAI extends ControllerMainPlay {
    private final Random random = new Random();

    private final Minimax MINIMAX = new Minimax();

    private final String playerChoice;
    private final String aiChoice;

    private boolean isBotTurn = false;
    private int playerTurn = 0;

    public ControllerAI(String playerChoice, String aiChoice) {
        this.playerChoice = playerChoice;
        this.aiChoice = aiChoice;
    }

    @Override
    protected void newGame() {
        restart.setOnMouseClicked(mouseEvent -> {
            buttons.forEach(this::resetButton);
            message.setText("Tic-Tac-Toe");
            pickButton(random.nextInt(9));
        });
    }

    @Override
    protected void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (!isBotTurn) {
                button.setText(playerChoice);
                style.setButtonStyle(button, playerChoice);
                button.setDisable(true);
                isBotTurn = true;
                setMessageStep();
                makeAIMoveAfterDelay();
            }
        });
    }

    @Override
    protected void setMessageStep() {
        if (message.getText().equals("You won!") || message.getText().equals("AI won!")) {
            return;
        }

        String text = isBotTurn ? "Step AI" : "Step player";
        message.setText(text);
    }

    private void makeAIMoveAfterDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                makeAIMove();
                checkIfGameIsOver();
                isBotTurn = false;
                setMessageStep();
            });
        }).start();
    }

    private void makeAIMove() {
        int move = MINIMAX.minimax(getBoardState());
        if (move != -1) {
            pickButton(move);
        }
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

    @Override
    protected void checkLine(List<Button> indexButton) {
        String line = indexButton.stream()
                .map(Button::getText)
                .collect(Collectors.joining());
        if (line.equals("XXX") && aiChoice.equals("X") || line.equals("OOO") && aiChoice.equals("O")) {
            gameOver("AI won!", indexButton);
        } else if (line.equals("XXX") && playerChoice.equals("X") || line.equals("OOO") && playerChoice.equals("O")) {
            gameOver("You won!", indexButton);
        }
    }
}
