package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        for(int i=0;i<4;i++){
            for(int j=0;j<2;j++){
                ghost[i][j] = new Image(this.getClass().getResource("ghost/ghost-"+i+"-"+j+".gif").toString());
                scared[i][j] = new Image(this.getClass().getResource("ghost/scaredghost"+j+".gif").toString());
                recover[i][j] = new Image(this.getClass().getResource("ghost/scaredghostrecovery"+j+".gif").toString());
            }
        }
    }
    public Ghost(ImageView ghostView){
        this.ghostView = ghostView;
        try{
            for(int i=0;i<4;i++){
                for(int j=0;j<2;j++){
                    ghost[i][j] = new Image(this.getClass().getResource("ghost/ghost-"+i+"-"+j+".gif").toString());
                }
            }

            //scared[0] = new Image(this.getClass().getResource("ghost/scaredghost1.gif").toString());
            //scared[1] = new Image(this.getClass().getResource("ghost/scaredghost2.gif").toString());

            //recover[0] = new Image(this.getClass().getResource("ghost/scaredghostrecovery1.gif").toString());
            //recover[1] = new Image(this.getClass().getResource("ghost/scaredghostrecovery2.gif").toString());

            this.ghostView.scaleXProperty().set(0.5);
            this.ghostView.scaleYProperty().set(0.5);
//            ghostAnim = TimelineBuilder.create()
//                    .cycleCount(Animation.INDEFINITE)
//                    .keyFrames(
//                            new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(ghost[0][0]);
//                                }
//                            }),
//                            new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(ghost[0][1]);
//                                }
//                            })
//                    )
//                    .build();
//            scaredAnim = TimelineBuilder.create()
//                    .cycleCount(Animation.INDEFINITE)
//                    .keyFrames(
//                            new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(scared[0]);
//                                }
//                            }),
//                            new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(scared[1]);
//                                }
//                            })
//                    )
//                    .build();
//            recovAnim = TimelineBuilder.create()
//                    .cycleCount(Animation.INDEFINITE)
//                    .keyFrames(
//                            new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(recover[0]);
//                                }
//                            }),
//                            new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>(){
//                                @Override
//                                public void handle(ActionEvent t) {
//                                    ghostView.setImage(recover[1]);
//                                }
//                            })
//                    )
//                    .build();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void animateGhost(){
        ghostAnim.play();
        gAct = true;
        gRec = gScar = false;
    }

    public void animateScared(){
        scaredAnim.play();
        gScar = true;
        gAct = gRec = false;
    }

    public void animateRecover(){
        recovAnim.play();
        gRec = true;
        gAct = gScar = false;
    }
    public Image[][] getGhostImage(){
        return ghost;
    }
    public Image[][] getScGhostImage() {
        return scared;
    }
    public Image[][] getRcGhostImage(){
        return recover;
    }
}
