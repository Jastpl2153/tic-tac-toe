package com.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerTwoPlayer implements Initializable {
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
    private int playerTurn = 0;
    ArrayList<Button> buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>
                (Arrays.asList(button1, button2, button3, button4,
                        button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void newGame() {
        buttons.forEach(this::resetButton);
        message.setText("Tic-Tac-Toe");
    }

    public void resetButton (Button button) {
        button.setText("");
        button.setDisable(false);
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }

    public void setPlayerSymbol(Button button){
        if (playerTurn % 2 == 0) {
            button.setText("X");
            playerTurn = 1;
        } else {
            button.setText("O");
            playerTurn = 0;
        }
    }

    public void checkIfGameIsOver(){
        for (int i = 0; i < 8; i++) {
            String line = switch (i) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button9.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                message.setText("Win X");
                buttons.forEach(button -> button.setDisable(true));
            } else if (line.equals("OOO")) {
                message.setText("Win O");
                buttons.forEach(button -> button.setDisable(true));
            }
        }
    }
}
