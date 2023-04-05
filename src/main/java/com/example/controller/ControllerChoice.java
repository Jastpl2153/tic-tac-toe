package com.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
public class ControllerChoice {
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
