package com.example.controller.logic;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerTwoPlayer extends ControllerMainPlay {
    private int playerTurn = 0;

    @Override
    protected void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            setMessageStep();
            style.setButtonStyle(button, button.getText());
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }

    @Override
    protected void setMessageStep() {
        if (playerTurn == 0) {
            message.setText("Step X");
        } else if(playerTurn == 1) {
            message.setText("Step O");
        }
    }

    private void setPlayerSymbol(Button button){
        if (playerTurn % 2 == 0) {
            button.setText("X");
            playerTurn = 1;
        } else {
            button.setText("O");
            playerTurn = 0;
        }
    }

    @Override
    protected boolean checkLine(List<Button> indexButton, String line) {
        if (line.equals("XXX")) {
            gameOver("Win X!", indexButton);
            return true;
        } else if (line.equals("OOO")) {
            gameOver("Win O!", indexButton);
            return true;
        } else if (isDraw()){
            gameOver("Draw", new ArrayList<>());
            return true;
        }
        return false;
    }
}
