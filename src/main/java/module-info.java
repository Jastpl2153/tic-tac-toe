module TicTacToe {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.controller;
    opens com.example.controller to javafx.fxml;
    exports com.example.style;
    opens com.example.style to javafx.fxml;
}