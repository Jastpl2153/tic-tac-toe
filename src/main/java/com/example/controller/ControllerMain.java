package com.example.controller;

import com.example.controller.logic.ControllerTwoPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    @FXML
    private Button but1;

    @FXML
    private Button but2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        but1.setFocusTraversable(false);
        but2.setFocusTraversable(false);
        but1.getStyleClass().addAll("hover", "dropshadow");
        but2.getStyleClass().addAll("hover", "dropshadow");
    }

    @FXML
    void playTwoPlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        ControllerTwoPlayer controller = new ControllerTwoPlayer();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void playAI(ActionEvent event) throws IOException {
        FXMLLoader loaderChoice = new FXMLLoader(getClass().getResource("/choice.fxml"));
        Parent choiceWindow = loaderChoice.load();
        Stage stageChoice = new Stage();
        stageChoice.setScene(new Scene(choiceWindow));
        stageChoice.setResizable(false);
        stageChoice.initModality(Modality.APPLICATION_MODAL);
        stageChoice.showAndWait();
    }
}
