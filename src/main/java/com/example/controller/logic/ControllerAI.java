package com.example.controller.logic;

import com.example.playAI.Minimax;
import com.example.playAI.State;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ControllerAI extends ControllerMainPlay {
    private String aiChoice;
    private int firstStep;
    private final Random random = new Random();
    private final String playerChoice;
    private boolean isBotTurn = false;

    public ControllerAI(String playerChoice, String aiChoice) {
        this.playerChoice = playerChoice;
        this.aiChoice = aiChoice;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstStep();
        super.initialize(url, resourceBundle);
    }

    @Override
    protected void newGame() {
        restart.setOnMouseClicked(mouseEvent -> {
            buttons.forEach(this::resetButton);
            message.setText("Tic-Tac-Toe");
            firstStep();
        });
    }

    @Override
    protected void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (!isBotTurn) {
                button.setText(playerChoice);
                style.setButtonStyle(button, playerChoice);
                button.setDisable(true);
                if (!checkIfGameIsOver() && !isDraw()) {
                    isBotTurn = true;
                    setMessageStep();
                    makeAIMoveAfterDelay();
                }
            }
        });
    }

    protected void firstStep() {
        firstStep = random.nextInt(2);
        if (firstStep == 0){
            isBotTurn = true;
            setMessageStep();
            makeAIMoveAfterDelay();
        } else {
            setMessageStep();
        }
    }

    @Override
    protected void setMessageStep() {
        if (isGameOver()) {
            return;
        }

        String text = isBotTurn ? "Step AI" : "Step player";
        message.setText(text);
    }

    private boolean isGameOver() {
        String gameResult = message.getText();
        return gameResult.equals("You won!") || gameResult.equals("AI won!");
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
                isBotTurn = false;
                setMessageStep();
                checkIfGameIsOver();
            });
        }).start();
    }

    private void makeAIMove() {
        Minimax MINIMAX = new Minimax(firstStep, aiChoice);
        int move = MINIMAX.minimax(getBoardState());
        if (move >= 0) {
            pickButton(move);
        } else if (move == -2){
            pickButton(pickRandom());
        }
    }

    private int pickRandom() {
        int pick = random.nextInt(9);
        if (buttons.get(pick).getText().isEmpty()) {
            return pick;
        } else {
            pickRandom();
        }
        return 0;
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
    protected boolean checkLine(List<Button> indexButton, String line) {
        if (isWin(aiChoice, line)) {
            gameOver("AI won!", indexButton);
            countWin(winPlayerSecondCount);
            return true;
        } else if (isWin(playerChoice, line)) {
            gameOver("You won!", indexButton);
            countWin(winPlayerFirstCount);
            return true;
        } else if (isDraw()) {
            gameOver("Draw", new ArrayList<>());
            return false;
        }
        return false;
    }

    private boolean isWin(String marker, String line) {
        return line.equals(marker + marker + marker);
    }
}
