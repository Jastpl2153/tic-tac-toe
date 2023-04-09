package com.example.controller.logic;

import com.example.style.Style;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public abstract class ControllerMainPlay implements Initializable {
    private static final int[][] LINE_INDEXES = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
    @FXML
    protected Button restart;
    @FXML
    protected Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    @FXML
    protected Label message;

    protected ArrayList<Button> buttons;

    protected Style style = new Style();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4,
                button5, button6, button7, button8, button9));
        restart.getStyleClass().add("hover");
        buttons.forEach(button -> {
            setMessageStep();
            style.setButtonHoverDisabled(button);
            setupButton(button);
            button.setFocusTraversable(false);
        });
        newGame();
    }

    protected void newGame() {
        restart.setOnMouseClicked(mouseEvent -> {
            buttons.forEach(this::resetButton);
            setMessageStep();
        });
    }

    protected void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
        style.resetChoice(button);
    }

    protected abstract void setupButton(Button button);

    protected abstract void setMessageStep();
    protected void checkIfGameIsOver() {
        for (int[] lineIndexes : LINE_INDEXES) {
            List<Button> indexButton = Arrays.stream(lineIndexes)
                    .mapToObj(buttons::get)
                    .collect(Collectors.toList());
            String line = indexButton.stream()
                    .map(Button::getText)
                    .collect(Collectors.joining());
            checkLine(indexButton, line);
        }
    }

    protected abstract void checkLine(List<Button> indexButton, String line);

    protected void gameOver(String messageText, List<Button> indexButton) {
        message.setText(messageText);
        buttons.forEach(button -> button.setDisable(true));
        indexButton.forEach(button -> style.setButtonWinner(button));
    }

    protected boolean isDraw() {
        return buttons.stream()
                .noneMatch(button -> button.getText().isEmpty());
    }
}
