package com.example.style;

import javafx.scene.control.Button;

public class Style {
    public void setButtonStyle(Button button, String symbol) {
        if (symbol.equals("X")) {
            button.getStyleClass().add("button-x");
        } else if (symbol.equals("O")) {
            button.getStyleClass().add("button-o");
        }
    }

    public void resetChoice(Button button) {
        button.getStyleClass().removeAll("button-o", "button-x", "winner");
    }

    public void setButtonHoverDisabled(Button button) {
        button.getStyleClass().addAll("hover","disabled");
    }

    public void setButtonWinner(Button button){
        button.getStyleClass().add("winner");
    }
}
