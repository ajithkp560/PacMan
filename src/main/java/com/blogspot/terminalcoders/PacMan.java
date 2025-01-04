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

public class PacMan {
    Image pacManx[] = new Image[3];
    Image pacMans[][] = new Image[4][3];
    ImageView pacMan;

    public PacMan() {}

    public PacMan(ImageView pacMan) {
        this.pacMan = pacMan;
        try {
            pacManx[0] = new Image(this.getClass().getResource("/pacman/Pac00.gif").toString());
            pacManx[1] = new Image(this.getClass().getResource("/pacman/Pac01.gif").toString());
            pacManx[2] = new Image(this.getClass().getResource("/pacman/Pac02.gif").toString());

            // Create Timeline for PacMan Animation
            Timeline pacManAnim = new Timeline(
                    new KeyFrame(Duration.millis(100), (ActionEvent t) -> pacMan.setImage(pacManx[0])),
                    new KeyFrame(Duration.millis(200), (ActionEvent t) -> pacMan.setImage(pacManx[1])),
                    new KeyFrame(Duration.millis(300), (ActionEvent t) -> pacMan.setImage(pacManx[2]))
            );
            pacManAnim.setCycleCount(Animation.INDEFINITE);
            pacManAnim.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Image[][] getImages() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                pacMans[i][j] = new Image(this.getClass().getResource("/pacman/Pac" + i + "" + j + ".gif").toString());
            }
        }
        return pacMans;
    }
}
