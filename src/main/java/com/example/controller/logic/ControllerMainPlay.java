package com.example.controller.logic;

import com.example.style.Style;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public abstract class ControllerMainPlay implements Initializable {
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

    protected abstract void checkLine(String line);

    protected void gameOver(String messageText) {
        message.setText(messageText);
        buttons.forEach(button -> button.setDisable(true));
    }
}
