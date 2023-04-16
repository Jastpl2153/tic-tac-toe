package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Start extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Image icon = new Image(String.valueOf(getClass().getResource("/icon.jpg")));
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main.fxml")));
            stage.getIcons().add(icon);
            stage.setTitle("Tic-Tac-Toe");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
