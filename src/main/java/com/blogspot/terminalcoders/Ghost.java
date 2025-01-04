package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Ghost {
    private Image[][] ghost = new Image[4][2];
    private Image scared[][] = new Image[4][2];
    private Image recover[][] = new Image[4][2];
    private Timeline ghostAnim, scaredAnim, recovAnim;
    private ImageView ghostView;
    public boolean gAct, gScar, gRec;

    public Ghost(){
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 2; j++) {
                ghost[i][j] = new Image(this.getClass().getResource("/ghost/ghost-" + i + "-" + j + ".gif").toString());
                scared[i][j] = new Image(this.getClass().getResource("/ghost/scaredghost" + j + ".gif").toString());
                recover[i][j] = new Image(this.getClass().getResource("/ghost/scaredghostrecovery" + j + ".gif").toString());
            }
        }
    }

    public Ghost(ImageView ghostView) {
        this.ghostView = ghostView;
        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 2; j++) {
                    ghost[i][j] = new Image(this.getClass().getResource("/ghost/ghost-" + i + "-" + j + ".gif").toString());
                }
            }

            this.ghostView.scaleXProperty().set(0.5);
            this.ghostView.scaleYProperty().set(0.5);

            // Create Timeline for Ghost Animation
            ghostAnim = new Timeline(
                    new KeyFrame(Duration.millis(50), (ActionEvent t) -> ghostView.setImage(ghost[0][0])),
                    new KeyFrame(Duration.millis(100), (ActionEvent t) -> ghostView.setImage(ghost[0][1]))
            );
            ghostAnim.setCycleCount(Animation.INDEFINITE);

            // Create Timeline for Scared Animation
            scaredAnim = new Timeline(
                    new KeyFrame(Duration.millis(100), (ActionEvent t) -> ghostView.setImage(scared[0][0])),
                    new KeyFrame(Duration.millis(200), (ActionEvent t) -> ghostView.setImage(scared[0][1]))
            );
            scaredAnim.setCycleCount(Animation.INDEFINITE);

            // Create Timeline for Recover Animation
            recovAnim = new Timeline(
                    new KeyFrame(Duration.millis(100), (ActionEvent t) -> ghostView.setImage(recover[0][0])),
                    new KeyFrame(Duration.millis(200), (ActionEvent t) -> ghostView.setImage(recover[0][1]))
            );
            recovAnim.setCycleCount(Animation.INDEFINITE);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void animateGhost() {
        ghostAnim.play();
        gAct = true;
        gRec = gScar = false;
    }

    public void animateScared() {
        scaredAnim.play();
        gScar = true;
        gAct = gRec = false;
    }

    public void animateRecover() {
        recovAnim.play();
        gRec = true;
        gAct = gScar = false;
    }

    public Image[][] getGhostImage() {
        return ghost;
    }

    public Image[][] getScGhostImage() {
        return scared;
    }

    public Image[][] getRcGhostImage() {
        return recover;
    }
}