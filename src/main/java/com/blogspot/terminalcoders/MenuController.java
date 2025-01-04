package com.blogspot.terminalcoders;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView pacMan;

    @FXML
    private void creditsClicked(ActionEvent evt) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreditsScene.fxml"));
            Parent root = fxmlLoader.load();
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startClicked(ActionEvent evt) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameScene.fxml"));
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            stage.getScene().setRoot(root);
            stage.getScene().getRoot().requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PacMan pacManx = new PacMan(pacMan);
        moveLeftToRightPacMan();
    }

    private void moveLeftToRightPacMan() {
        pacMan.setScaleX(1); // Ensure PacMan faces the correct direction
        TranslateTransition transition = new TranslateTransition(Duration.seconds(5), pacMan);
        transition.setFromX(50);
        transition.setToX(480);
        transition.setOnFinished(event -> moveRightToLeftPacMan());
        transition.play();
    }

    private void moveRightToLeftPacMan() {
        pacMan.setScaleX(-1); // Flip PacMan to face the opposite direction
        TranslateTransition transition = new TranslateTransition(Duration.seconds(5), pacMan);
        transition.setFromX(480);
        transition.setToX(50);
        transition.setOnFinished(event -> moveLeftToRightPacMan());
        transition.play();
    }
}
