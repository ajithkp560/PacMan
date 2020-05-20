package com.blogspot.terminalcoders;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {
    @FXML
    Label authorLbl;

    @FXML
    private void backClicked(ActionEvent evt){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
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
    private void initColorLabel(){
        LinearGradient lgAuth = LinearGradientBuilder.create()
                .proportional(true)
                .cycleMethod(CycleMethod.REPEAT)
                .stops(new Stop(0, Color.web("#ff9933")), new Stop(0.15, Color.web("#47d147")), new Stop(0.30, Color.web("#00ccff")), new Stop(0.45, Color.web("#00a3cc")), new Stop(0.60, Color.web("#ff3385")), new Stop(0.75, Color.web("#e60073")), new Stop(0.90, Color.web("#ff1a1a")), new Stop(1.0, Color.web("#ace600")))
                .build();
        authorLbl.setTextFill(lgAuth);
        TimelineBuilder.create()
                .cycleCount(Animation.INDEFINITE)
                .keyFrames(
                        new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#ff9933")), new Stop(0.15, Color.web("#47d147")), new Stop(0.30, Color.web("#00ccff")), new Stop(0.45, Color.web("#00a3cc")), new Stop(0.60, Color.web("#ff3385")), new Stop(0.75, Color.web("#e60073")), new Stop(0.90, Color.web("#ff1a1a")), new Stop(1.0, Color.web("#ace600")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#ace600")), new Stop(0.15, Color.web("#ff9933")), new Stop(0.30, Color.web("#47d147")), new Stop(0.45, Color.web("#00ccff")), new Stop(0.60, Color.web("#00a3cc")), new Stop(0.75, Color.web("#ff3385")), new Stop(0.90, Color.web("#e60073")), new Stop(1.0, Color.web("#ff1a1a")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(450), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#ff1a1a")), new Stop(0.15, Color.web("#ace600")), new Stop(0.30, Color.web("#ff9933")), new Stop(0.45, Color.web("#47d147")), new Stop(0.60, Color.web("#00ccff")), new Stop(0.75, Color.web("#00a3cc")), new Stop(0.90, Color.web("#ff3385")), new Stop(1.0, Color.web("#e60073")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#e60073")), new Stop(0.15, Color.web("#ff1a1a")), new Stop(0.30, Color.web("#ace600")), new Stop(0.45, Color.web("#ff9933")), new Stop(0.60, Color.web("#47d147")), new Stop(0.75, Color.web("#00ccff")), new Stop(0.90, Color.web("#00a3cc")), new Stop(1.0, Color.web("#ff3385")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(750), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#ff3385")), new Stop(0.15, Color.web("#e60073")), new Stop(0.30, Color.web("#ff1a1a")), new Stop(0.45, Color.web("#ace600")), new Stop(0.60, Color.web("#ff9933")), new Stop(0.75, Color.web("#47d147")), new Stop(0.90, Color.web("#00ccff")), new Stop(1.0, Color.web("#00a3cc")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(900), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#00a3cc")), new Stop(0.15, Color.web("#ff3385")), new Stop(0.30, Color.web("#e60073")), new Stop(0.45, Color.web("#ff1a1a")), new Stop(0.60, Color.web("#ace600")), new Stop(0.75, Color.web("#ff9933")), new Stop(0.90, Color.web("#47d147")), new Stop(1.0, Color.web("#00ccff")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(1050), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#00ccff")), new Stop(0.15, Color.web("#00a3cc")), new Stop(0.30, Color.web("#ff3385")), new Stop(0.45, Color.web("#e60073")), new Stop(0.60, Color.web("#ff1a1a")), new Stop(0.75, Color.web("#ace600")), new Stop(0.90, Color.web("#ff9933")), new Stop(1.0, Color.web("#47d147")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        }),
                        new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                LinearGradient lgAuth = LinearGradientBuilder.create()
                                        .proportional(true)
                                        .cycleMethod(CycleMethod.REPEAT)
                                        .stops(new Stop(0.0, Color.web("#47d147")), new Stop(0.15, Color.web("#00ccff")), new Stop(0.30, Color.web("#00a3cc")), new Stop(0.45, Color.web("#ff3385")), new Stop(0.60, Color.web("#e60073")), new Stop(0.75, Color.web("#ff1a1a")), new Stop(0.90, Color.web("#ace600")), new Stop(1.0, Color.web("#ff9933")))
                                        .build();
                                authorLbl.setTextFill(lgAuth);
                            }
                        })
                ).build().play();
    }
}
