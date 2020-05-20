package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PacMan {
    Image pacManx[] = new Image[3];
    Image pacMans[][] = new Image[4][3];
    ImageView pacMan;
    static int i=0;
    public PacMan(){}
    public PacMan(ImageView pacMan){
        this.pacMan = pacMan;
        try{
            pacManx[0] = new Image(this.getClass().getResource("pacman/Pac00.gif").toString());
            pacManx[1] = new Image(this.getClass().getResource("pacman/Pac01.gif").toString());
            pacManx[2] = new Image(this.getClass().getResource("pacman/Pac02.gif").toString());

            //this.pacMan.scaleXProperty().set(0.5);
            //this.pacMan.scaleYProperty().set(0.5);

            TimelineBuilder.create()
                    .cycleCount(Animation.INDEFINITE)
                    .keyFrames(
                            new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent t) {
                                    pacMan.setImage(pacManx[0]);
                                }
                            }),
                            new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent t) {
                                    pacMan.setImage(pacManx[1]);
                                }
                            }),
                            new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent t) {
                                    pacMan.setImage(pacManx[2]);
                                }
                            })
                            )
            .build().play();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Image[][] getImages(){
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                pacMans[i][j] = new Image(this.getClass().getResource("pacman/Pac"+i+""+j+".gif").toString());
            }
        }
        return pacMans;
    }
}
