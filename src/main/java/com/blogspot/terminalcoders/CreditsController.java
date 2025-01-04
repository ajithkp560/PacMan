package com.blogspot.terminalcoders;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {
    @FXML
    Label authorLbl;

    @FXML
    private void backClicked(ActionEvent evt) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MenuScene.fxml"));
            Parent root = fxmlLoader.load();
            Node node = (Node) evt.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColorLabel();
    }

    private void initColorLabel() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        // Define keyframes with changing gradients
        timeline.getKeyFrames().addAll(
                createKeyFrame(0, createGradient(
                        "#ff9933", "#47d147", "#00ccff", "#00a3cc", "#ff3385", "#e60073", "#ff1a1a", "#ace600"
                )),
                createKeyFrame(300, createGradient(
                        "#ace600", "#ff9933", "#47d147", "#00ccff", "#00a3cc", "#ff3385", "#e60073", "#ff1a1a"
                )),
                createKeyFrame(600, createGradient(
                        "#ff1a1a", "#ace600", "#ff9933", "#47d147", "#00ccff", "#00a3cc", "#ff3385", "#e60073"
                )),
                createKeyFrame(900, createGradient(
                        "#e60073", "#ff1a1a", "#ace600", "#ff9933", "#47d147", "#00ccff", "#00a3cc", "#ff3385"
                )),
                createKeyFrame(1200, createGradient(
                        "#ff3385", "#e60073", "#ff1a1a", "#ace600", "#ff9933", "#47d147", "#00ccff", "#00a3cc"
                ))
        );

        timeline.play();
    }

    private KeyFrame createKeyFrame(double timeMillis, LinearGradient gradient) {
        return new KeyFrame(Duration.millis(timeMillis), event -> authorLbl.setTextFill(gradient));
    }

    private LinearGradient createGradient(String... colors) {
        Stop[] stops = new Stop[colors.length];
        for (int i = 0; i < colors.length; i++) {
            stops[i] = new Stop((double) i / (colors.length - 1), Color.web(colors[i]));
        }
        return new LinearGradient(0, 0, 1, 0, true, CycleMethod.REPEAT, stops);
    }
}
