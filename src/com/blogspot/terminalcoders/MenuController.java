package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    AnchorPane root;

    @FXML
    ImageView pacMan;

    @FXML
    private void creditsClicked(ActionEvent evt){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreditsScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startClicked(ActionEvent evt){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
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
    void moveLeftToRightPacMan(){
        pacMan.scaleXProperty().set(1);
        TranslateTransitionBuilder.create()
                .node(pacMan)
                .fromX(50)
                .toX(480)
                .duration(Duration.seconds(5))
                .onFinished(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        moveRightToLeftPacMan();
                    }
                }).build().play();
    }
    void moveRightToLeftPacMan(){
        pacMan.scaleXProperty().set(-1);
        TranslateTransitionBuilder.create()
                .node(pacMan)
                .fromX(480)
                .toX(50)
                .duration(Duration.seconds(5))
                .onFinished(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        pacMan.scaleXProperty().set(1);
                        moveLeftToRightPacMan();
                    }
                }).build().play();
    }
}
