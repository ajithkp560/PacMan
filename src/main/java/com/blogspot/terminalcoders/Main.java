package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file and set up the scene
        Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));

        // Set the title and scene properties
        primaryStage.setTitle("PacMan ~ AJITH KP ~ TerminalCoders.BlogSpot.Com");
        primaryStage.setScene(new Scene(root, 750, 700));

        // Ensure the root of the scene receives focus
        primaryStage.getScene().getRoot().requestFocus();

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
