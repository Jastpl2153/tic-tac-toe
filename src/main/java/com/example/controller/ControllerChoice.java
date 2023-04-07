package com.example.controller;

import com.example.controller.logic.ControllerAI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerChoice {
    @FXML
    private Button buttonO;

    @FXML
    private Button buttonX;
    @FXML
    void mouseStyle(MouseEvent event) {
        buttonX.getStyleClass().addAll("hover", "dropshadow");
        buttonO.getStyleClass().addAll("hover", "dropshadow");
    }

    public void getChoiceX(ActionEvent event) throws IOException {
        showGameWindow("X", "O");
        closeWindow(event);
    }

    public void getChoiceO(ActionEvent event) throws IOException {
        showGameWindow("O", "X");
        closeWindow(event);
    }

    private void showGameWindow(String playerType, String aiType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        ControllerAI controllerAI = new ControllerAI(playerType, aiType);
        loader.setController(controllerAI);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void closeWindow(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
