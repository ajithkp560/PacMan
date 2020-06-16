package com.blogspot.terminalcoders;

//
// coded by AJITH K P [ @ajithkp560 ]
// Blog: http://www.terminalcoders.blogspot.com
//

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class GameController  implements Initializable, Runnable {
    final static int rowX = 19, colY = 22, boxSize = 24;
    final static long superTimeMillis = 5000;
    static final int pacNSpeed = 3, pacSSpeed = 4, maxGhosts = 6, newGhostTimeOut=20000;
    static int Px, Py, pDir, pDirTmp, pInd, ghostDir[], ghostIndex[], ghostPos[][], numGhosts = 3, bigCircleRad = 6, animBigCir = 0, pacSpeed = 3, ghostSpeed = 3, gateX, gateY, ghostHome[][] = new int[3][2], lifePending = 3, iniPx, iniPy;
    static int refreshRate = 50;
    static boolean scared = false, recover=false, start=false, superPac=false, dirChng=false, newGhCreated=true;
    static boolean ghostOut[] = new boolean[maxGhosts], ghostBlocked[] = new boolean[maxGhosts];
    static char map[][] = new char[colY][rowX];
    static int score = 0, numEggs = 0;
    static long cMillis = 0, lastBigEgg = 0;
    static Image[][] pacMans;
    static Image[][] ghosts;
    static Image[][] scGhost;
    static Image[][] rcGhost;
    @FXML
    AnchorPane gameView, gameGfx;
    @FXML
    Label winLbl, failLbl, lifeLbl, scoreLbl;
    @FXML
    Button startBtn;
    int startX = 1*boxSize, startY = 4*boxSize;
    //HashMap<ImageView, Ghost> ghosts = new HashMap<ImageView, Ghost>();
    StockImages wall = new StockImages();
    MyCanvas canvas = new MyCanvas(700, 700);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initVars();
        //Thread t = new Thread(this);
        //t.start();
        Platform.runLater(()->{
            Thread t = new Thread(this);
            t.start();
        });
        initGhosts();
        startBtn.setText("START");
    }

    @FXML
    private void KeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.UP){
            pDirTmp = 1;
            dirChng = true;
        } else if(event.getCode() == KeyCode.DOWN){
            pDirTmp = 3;
            dirChng = true;
        } else if(event.getCode() == KeyCode.LEFT){
            pDirTmp = 2;
            dirChng = true;
        } else if(event.getCode() == KeyCode.RIGHT){
            pDirTmp = 0;
            dirChng = true;
        } else  if(event.getCode() == KeyCode.SPACE){
            start = !start;
        }
    }

    private void repaint() {
        while (true) {
            try {
                Thread.sleep(refreshRate);
            } catch (Exception ex){
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            canvas.repaint();
            if(lifePending>0) {
                if (start) {
                    animatePacMan();
                    animateGhost();
                    animateFood();
                    movePacMan();
                    moveGhosts();
                    updateScoreLife();
                }
            }
            //movePacMan();
        }
    }

    private void animatePacMan(){
        pInd = (pInd+1)%pacMans[0].length;
    }

    private void animateGhost(){
        for(int i=0;i<numGhosts;i++){
            ghostIndex[i] = (ghostIndex[i] + 1) % ghosts[0].length;
        }
    }

    private void animateFood(){
        animBigCir = (animBigCir+1)%5;
    }

    public void startPlay(ActionEvent actionEvent) {
        start=!start;
        String text = start?"PAUSE":"START";
        startBtn.setText(text);
    }

    private void initGhosts(){
        ghostDir = new int[maxGhosts];
        ghostIndex = new int[maxGhosts];
        for(int i=0;i<numGhosts;i++){
            ghostDir[i] = 0;
            ghostIndex[i] = 0;
        }
        ghostPos = new int[maxGhosts][2];
    }

    private void initVars(){
        PacMan pm = new PacMan();
        pacMans = pm.getImages();
        Ghost gh = new Ghost();
        ghosts = gh.getGhostImage();
        scGhost = gh.getScGhostImage();
        rcGhost = gh.getRcGhostImage();
        gameGfx.getChildren().add(canvas);
        pDir = 0;
        pInd = 0;
        initGhosts();
        lifeLbl.setText("Life: "+lifePending);
        scoreLbl.setText("Score: "+score);
    }

    private void movePacMan(){
        int cPosX, cPosY;
        boolean block = false;
        System.out.println("Eggs: "+numEggs);
        if(numEggs==0){
            start = false;
            System.out.println("You Won!!!");
            winLbl.setVisible(true);
            FadeTransition fTrans = new FadeTransition(Duration.millis(3000), winLbl);
            fTrans.setFromValue(0);
            fTrans.setByValue(1);
            RotateTransition rTrans = new RotateTransition(Duration.millis(3000), winLbl);
            rTrans.setByAngle(360f);
            rTrans.setAutoReverse(true);
            ParallelTransition pTrans = new ParallelTransition();
            pTrans.getChildren().addAll(fTrans, rTrans);
            pTrans.play();
        }
        for(int i=-4;i<=4;i++){
            for(int j=-4;j<=4;j++){
                cPosX = (Px + pacSpeed * j);
                cPosY = (Py + pacSpeed * i);
                if(cPosX%boxSize==0 && cPosY%boxSize==0){
                    int boxX = cPosX/boxSize;
                    int boxY = cPosY/boxSize;
                    System.out.println(boxX+","+boxY+","+map[boxY][boxX]);
                    if(map[boxY][boxX]=='.'){
                        map[boxY][boxX] = '-';
                        score = score + 10;
                    }
                    if(map[boxY][boxX]=='O'){
                        map[boxY][boxX] = '-';
                        score = score + 50;
                        superPac = true;
                        cMillis = System.currentTimeMillis();
                        lastBigEgg = System.currentTimeMillis();
                        newGhCreated = false;
                    }
                }
            }
        }
        System.out.println(score);
        cPosX = Px%boxSize;
        cPosY = Py%boxSize;
        if(cPosX==0 && cPosY==0){
            int boxX = (Px/boxSize<0)?0:(Px/boxSize);
            int boxY = (Py/boxSize<0)?0:(Py/boxSize);
            pacSpeed = superPac?pacSSpeed:pacNSpeed;
            if(dirChng){
                if(pDirTmp==0){
                    if(boxX < rowX) {
                        if (map[boxY][boxX + 1] != '#' && map[boxY][boxX + 1] != 'X' && map[boxY][boxX + 1]!='X') {
                            pDir = pDirTmp;
                            dirChng = false;
                        }
                    }
                } else if(pDirTmp==1){
                    if(boxY > 0) {
                        if (map[boxY - 1][boxX] != '#' && map[boxY - 1][boxX] != 'X' && map[boxY -1][boxX]!='X') {
                            pDir = pDirTmp;
                            dirChng = false;
                        }
                    }
                } else if(pDirTmp==2){
                    if(boxX > 0) {
                        if (map[boxY][boxX - 1] != '#' && map[boxY][boxX - 1] != 'X' && map[boxY][boxX - 1] != 'X') {
                            pDir = pDirTmp;
                            dirChng = false;
                        }
                    }
                } else if(pDirTmp==3){
                    if(boxY < colY) {
                        if (map[boxY + 1][boxX] != '#' && map[boxY + 1][boxX] != 'X' && map[boxY + 1][boxX] != 'X') {
                            pDir = pDirTmp;
                            dirChng = false;
                        }
                    }
                }
            }
            System.out.println("Box:["+boxY+","+boxX+"]");
            if(pDir==0){
                if(boxX < rowX-1) {
                    System.out.println("Map:"+map[boxY][boxX + 1]);
                    if (map[boxY][boxX + 1] == '#') {
                        block = true;
                    }
                }else {
                    Px = 0;
                }
            } else if(pDir==1){
                if(boxY > 0) {
                    System.out.println("Map:"+map[boxY - 1][boxX]);
                    if (map[boxY - 1][boxX] == '#') {
                        block = true;
                    }
                }else {
                    Py = 0;
                }
            } else if(pDir==2){
                if(boxX > 0) {
                    System.out.println("Map:"+map[boxY][boxX - 1]);
                    if (map[boxY][boxX - 1] == '#') {
                        block =  true;
                    }
                }else {
                    Px = boxSize * (rowX-1);
                }
            } else if(pDir==3){
                if(boxY < colY-1) {
                    System.out.println("Map:"+map[boxY + 1][boxX]);
                    if (map[boxY + 1][boxX] == '#') {
                        block = true;
                    }
                }else {
                    Py = boxSize * (colY-1);
                }
            }
        }
        if(superPac){
            scared = true;
            System.out.println("Scared: " +scared+","+recover);
            System.out.println("Time: "+(System.currentTimeMillis()-cMillis));
            if(System.currentTimeMillis()-cMillis<superTimeMillis){
                if(System.currentTimeMillis()-cMillis>3750) {
                    recover = true;
                }
            }
            else{
                recover = false;
                superPac = false;
                scared = false;
            }
        }
        System.out.println("Block:"+block);
        if(!block){
            if(pDir==0){
                Px = Px + pacSpeed;
            } else if(pDir==1){
                Py = Py - pacSpeed;
            } else if(pDir==2){
                Px = Px - pacSpeed;
            } else if(pDir==3){
                Py = Py + pacSpeed;
            }
            System.out.println((superPac?pacSSpeed:pacNSpeed)+",Px:"+Px+",Py:"+Py);
        }
    }

    private void moveGhosts(){
        for(int i=0;i<numGhosts;i++){
            Integer gateDir = null;
            List<Integer> availPath = new ArrayList<Integer>();
            boolean wayAvailable = false;
            if(ghostPos[i][0]%boxSize==0 && ghostPos[i][1]%boxSize==0){
                int boxX = ghostPos[i][0]/boxSize;
                int boxY = ghostPos[i][1]/boxSize;
                System.out.println("GhostPos: ["+boxY+","+boxX+"]");
                boxX = (boxX<0)?0:boxX;
                boxY = (boxY<0)?0:boxY;
                if(boxX<rowX-1){
                    if (map[boxY][boxX + 1] != '#') {
                        if(map[boxY][boxX + 1] == 'X'){
                            gateDir = 0;
                            System.out.println("GATE: "+gateDir);
                        } else {
                            if(ghostDir[i]==1 || ghostDir[i]==3) {
                                wayAvailable = true;
                            }
                        }
                        availPath.add(0);
                    } else {
                        if(ghostDir[i] == 0) {
                            ghostBlocked[i] = true;
                        }
                    }
                }
                if(boxY > 0) {
                    if (map[boxY - 1][boxX] != '#') {
                        if(map[boxY - 1][boxX] == 'X'){
                            gateDir = 1;
                            System.out.println("GATE: "+gateDir);
                        } else {
                            if(ghostDir[i]==0 || ghostDir[i]==2) {
                                wayAvailable = true;
                            }
                        }
                        availPath.add(1);
                    } else {
                        if(ghostDir[i] == 1) {
                            ghostBlocked[i] = true;
                        }
                    }
                }
                if(boxX > 0) {
                    if (map[boxY][boxX - 1] != '#') {
                        if (map[boxY][boxX - 1] == 'X') {
                            gateDir = 2;
                            System.out.println("GATE: "+gateDir);
                        } else {
                            if(ghostDir[i]==1 || ghostDir[i]==3) {
                                wayAvailable = true;
                            }
                        }
                        availPath.add(2);
                    } else {
                        if(ghostDir[i] == 2) {
                            ghostBlocked[i] = true;
                        }
                    }
                }
                if(boxY < colY-1) {
                    if (map[boxY + 1][boxX] != '#') {
                        if (map[boxY + 1][boxX] == 'X') {
                            gateDir = 3;
                            System.out.println("GATE: "+gateDir);
                        } else {
                            if(ghostDir[i]==0 || ghostDir[i]==2) {
                                wayAvailable = true;
                            }
                        }
                        availPath.add(3);
                    } else {
                        if(ghostDir[i] == 3) {
                            ghostBlocked[i] = true;
                        }
                    }
                }
                if(ghostDir[i] == 0) {
                    if (!(boxX < rowX - 1)) {
                        ghostPos[i][0] = 0;
                    }
                } else if(ghostDir[i]==1) {
                    if (!(boxY > 0)) {
                        ghostPos[i][1] = 0;
                    }
                } else if(ghostDir[i]==2) {
                    if (!(boxX > 0)) {
                        ghostPos[i][0] = boxSize * (rowX-1);
                    }
                } else if(ghostDir[i]==3) {
                    if (!(boxY < colY - 1)) {
                        ghostPos[i][1] = boxSize * (colY-1);
                    }
                }

                for(int x=0;x<3;x++){
                    if(boxX == ghostHome[x][0] && boxY == ghostHome[x][1]){
                        System.out.println("I'm Trapped");
                        if(gateDir != null) {
                            ghostDir[i] = gateDir;
                        }
                    }
                }

                if(gateDir!=null && ghostOut[i]==false){
                    ghostDir[i] = gateDir;
                    ghostOut[i] = true;
                } else if(ghostBlocked[i]){
                    int gDir = availPath.get(new Random().nextInt(availPath.size()));
                    if(!scared) {
                        int tmpX = Px - ghostPos[i][0];
                        int tmpY = Py - ghostPos[i][1];
                        int tmp = new Random().nextInt(4);
                        if (tmp % 3 == 0){
                            if (tmpX > 0) {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 0) {
                                        if (availPath.contains(0)) {
                                            gDir = 0;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 2) {
                                        if (availPath.contains(2)) {
                                            gDir = 2;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        } else if (tmp % 2 == 1) {
                            if (tmpY > 0) {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 3) {
                                        if (availPath.contains(3)) {
                                            gDir = 3;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 1) {
                                        if (availPath.contains(1)) {
                                            gDir = 1;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        }
                    }
                    else {
                        int tmpX = ghostPos[i][0] - Px;
                        int tmpY = ghostPos[i][1] - Py;
                        int tmp = new Random().nextInt(2);
                        if (tmp % 2 == 0){
                            if (tmpX > 0) {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 0) {
                                        if (availPath.contains(0)) {
                                            gDir = 0;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 2) {
                                        if (availPath.contains(2)) {
                                            gDir = 2;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        } else if (tmp % 2 == 1) {
                            if (tmpY > 0) {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 3) {
                                        if (availPath.contains(3)) {
                                            gDir = 3;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 1) {
                                        if (availPath.contains(1)) {
                                            gDir = 1;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        }
                    }
                    ghostDir[i] = gDir;
                    ghostBlocked[i] = false;
                } else if(wayAvailable){
                    int gDir = availPath.get(new Random().nextInt(availPath.size()));
                    if(!scared) {
                        int tmpX = Px - ghostPos[i][0];
                        int tmpY = Py - ghostPos[i][1];
                        int tmp = new Random().nextInt(4);
                        if (tmp % 3 == 0){
                            if (tmpX > 0) {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 0) {
                                        if (availPath.contains(0)) {
                                            gDir = 0;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 2) {
                                        if (availPath.contains(2)) {
                                            gDir = 2;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        } else if (tmp % 2 == 1) {
                            if (tmpY > 0) {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 3) {
                                        if (availPath.contains(3)) {
                                            gDir = 3;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(4);
                                if (tmp % 4 != 0) {
                                    if (ghostDir[i] != 1) {
                                        if (availPath.contains(1)) {
                                            gDir = 1;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        }
                    }
                    else {
                        int tmpX = ghostPos[i][0] - Px;
                        int tmpY = ghostPos[i][1] - Py;
                        int tmp = new Random().nextInt(2);
                        if (tmp % 2 == 0){
                            if (tmpX > 0) {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 0) {
                                        if (availPath.contains(0)) {
                                            gDir = 0;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 2) {
                                        if (availPath.contains(2)) {
                                            gDir = 2;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        } else if (tmp % 2 == 1) {
                            if (tmpY > 0) {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 3) {
                                        if (availPath.contains(3)) {
                                            gDir = 3;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            } else {
                                tmp = new Random().nextInt(2);
                                if (tmp % 2 == 0) {
                                    if (ghostDir[i] != 1) {
                                        if (availPath.contains(1)) {
                                            gDir = 1;
                                        }
                                    }
                                } else {
                                    if (availPath.size() > 1 && ghostDir[i] % 2 == gDir % 2) {
                                        availPath.remove(availPath.indexOf(gDir));
                                        gDir = availPath.get(new Random().nextInt(availPath.size()));
                                    }
                                }
                            }
                        }
                    }
                    ghostDir[i] = gDir;
                }

                if(numGhosts<maxGhosts){
                    if (System.currentTimeMillis() - lastBigEgg > newGhostTimeOut) {
                        if(!newGhCreated) {
                            ghostPos[numGhosts][0] = ghostPos[i][0];
                            ghostPos[numGhosts][1] = ghostPos[i][1];
                            numGhosts++;
                            newGhCreated = true;
                        }
                    }
                }
            }

            System.out.println("GATE: "+gateDir);

            if(ghostDir[i]==0){
                ghostPos[i][0] = ghostPos[i][0] + ghostSpeed;
            } else if(ghostDir[i]==1){
                ghostPos[i][1] = ghostPos[i][1] - ghostSpeed;
            } else if(ghostDir[i]==2){
                ghostPos[i][0] = ghostPos[i][0] - ghostSpeed;
            } else if(ghostDir[i]==3){
                ghostPos[i][1] = ghostPos[i][1] + ghostSpeed;
            }

            for(int z=-4;z<=4;z++){
                for(int y=-4;y<=4;y++){
                    int gPosX = (ghostPos[i][0] + pacSpeed * y);
                    int gPosY = (ghostPos[i][1] + pacSpeed * z);
                    if(gPosX%boxSize==0 && gPosY%boxSize==0) {
                        int ghX = gPosX / boxSize;
                        int ghY = gPosY / boxSize;
                        int paX = Px / boxSize;
                        int paY = Py / boxSize;
                        if (!scared) {
                            if (ghX == paX && ghY == paY) {
                                start = false;
                                lifePending = lifePending - 1;
                                Px = iniPx;
                                Py = iniPy;
                                pDir = 0;
                                pInd = 1;
                                if (lifePending == 0) {
                                    System.out.println("You Failed!!!");
                                    failLbl.setVisible(true);
                                    FadeTransition fTrans = new FadeTransition(Duration.millis(3000), failLbl);
                                    fTrans.setFromValue(0);
                                    fTrans.setByValue(1);
                                    RotateTransition rTrans = new RotateTransition(Duration.millis(3000), failLbl);
                                    rTrans.setByAngle(360f);
                                    rTrans.setAutoReverse(true);
                                    ParallelTransition pTrans = new ParallelTransition();
                                    pTrans.getChildren().addAll(fTrans, rTrans);
                                    pTrans.play();
                                }
                            }
                        } else {
                            if (ghX == paX && ghY == paY) {
                                ghostPos[i][0] = ghostPos[numGhosts - 1][0];
                                ghostPos[i][1] = ghostPos[numGhosts - 1][1];
                                numGhosts = numGhosts - 1;
                                if (numGhosts == 0) {
                                    numGhosts = 3;
                                    for (int x = 0; x < numGhosts; x++) {
                                        ghostPos[i][0] = ghostHome[i][0];
                                        ghostPos[i][1] = ghostHome[i][1];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateScoreLife(){
        System.out.println("Life: "+lifePending+" , Score: "+score);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                lifeLbl.setText("Life: "+lifePending);
                scoreLbl.setText("Score: "+score);
                String text = start?"PAUSE":"START";
                startBtn.setText(text);
            }
        });
    }
    
    @Override
    public void run() {
        System.out.println("Running...");
        repaint();
    }

    private class MyCanvas extends Canvas{
        boolean initial = true;
        public MyCanvas(int width, int height){
            this.setWidth(width);
            this.setHeight(height);
            try {
                InputStream in = getClass().getResourceAsStream("assets/Map.txt");
                BufferedReader fr = new BufferedReader(new InputStreamReader(in));
                //BufferedReader fr = new BufferedReader(new InputStreamReader(Class.forName("com.blogspot.terminalcoders.GameController").getClassLoader().getResourceAsStream(this.getClass().getResource("assets/Map.txt").toString())));
                //FileReader fr = new FileReader(new File(this.getClass().getResource("assets/Map.txt").toURI()));
                int i = 0, j = 0, x;
                while ((x = fr.read()) != -1) {
                    if (x == 13) {
                        j = 0;
                        i++;
                        System.out.println();
                    } else if (x != 10) {
                        map[i][j++] = (char) x;
                        System.out.print(map[i][j-1]+":["+i+","+(j-1)+"] ");
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
        public void repaint() {
            GraphicsContext gc = this.getGraphicsContext2D();
            gc.clearRect(0, 0, 700, 700);
            numEggs = 0;
            int gIndex = 0;
            try {

                for (int i = 0; i < colY; i++) {
                    for (int j = 0; j < rowX; j++) {
                        char c = map[i][j];
                        //System.out.print(c);
                        if (c == '#') {
                            if(scared) {
                                gc.drawImage(wall.getScaredWallImg(), startX + j * boxSize, startY + i * boxSize, boxSize, boxSize);
                            } else {
                                gc.drawImage(wall.getWallImage(), startX + j * boxSize, startY + i * boxSize, boxSize, boxSize);
                            }
                        } else if (c == 'P') {
                            Px = j * boxSize;
                            Py = i * boxSize;
                            iniPx = j * boxSize;
                            iniPy = i * boxSize;
                            map[i][j] = '-';
                            //gc.drawImage(pm.getImage(), startX+j*boxWidth, startY+i*boxHeight, boxWidth, boxHeight);
                        } else if (c == 'G') {
                            ghostPos[gIndex][0] = j * boxSize;
                            ghostPos[gIndex][1] = i * boxSize;
                            ghostHome[gIndex][0] = j;
                            ghostHome[gIndex][1] = i;
                            gIndex = gIndex+1;
                            map[i][j] = '-';
                            //gc.drawImage(ghost.getImage(), j*boxWidth, i*boxHeight, boxWidth, boxHeight);
                        }
                        else if (c == 'O') {
                            gc.setFill(Color.rgb(15, 255, 200));
                            gc.fillOval(startX + j * boxSize + boxSize/2 - (bigCircleRad+animBigCir)/2, startY + i * boxSize + boxSize/2 - (bigCircleRad+animBigCir)/2, bigCircleRad + animBigCir, bigCircleRad + animBigCir);
                            gc.fill();
                            gc.stroke();
                            numEggs++;
                        }
                        else if (c == '.') {
                            gc.setFill(Color.rgb(255, 15, 50));
                            gc.fillOval(startX + j * boxSize + boxSize/2 - 3/2, startY + i * boxSize + boxSize/2 - 3/2, 3, 3);
                            gc.fill();
                            gc.stroke();
                            numEggs++;
                        }
                        else if (c == '&') {
                            gc.drawImage(wall.getDoorImg(), startX + j * boxSize, startY + i * boxSize, boxSize, boxSize);
                            gateX = j;
                            gateY = i;
                            map[i][j] = 'X';
                        }
                    }
                    //System.out.println();
                }
                if(initial) {
                    for (int i = 0; i < pacMans.length; i++) {
                        for (int j = 0; j < pacMans[pDir].length; j++) {
                            gc.drawImage(pacMans[i][j], Px, Py, boxSize, boxSize);
                        }
                    }
                    for(int i=0;i<ghosts.length;i++){
                        for(int j=0;j<ghosts[0].length;j++){
                            gc.drawImage(ghosts[i][j], ghostPos[0][0], ghostPos[0][1], boxSize, boxSize);
                        }
                    }
                    System.out.println("Initial");
                    initial = false;
                }
                gc.drawImage(pacMans[pDir][pInd], startX + Px, startY + Py, boxSize, boxSize);
                //gc.drawImage(ghosts[ghostDir[0]][ghostIndex[0]], Px, Py, boxWidth, boxHeight);
                for (int i=0;i<numGhosts;i++){
                    if (scared) {
                        if (recover) {
                            gc.drawImage(rcGhost[ghostDir[i]][ghostIndex[i]], startX + ghostPos[i][0], startY + ghostPos[i][1], boxSize, boxSize);
                        } else {
                            gc.drawImage(scGhost[ghostDir[i]][ghostIndex[i]], startX + ghostPos[i][0], startY + ghostPos[i][1], boxSize, boxSize);
                        }
                    }
                    else{
                        gc.drawImage(ghosts[ghostDir[i]][ghostIndex[i]], startX + ghostPos[i][0], startY + ghostPos[i][1], boxSize, boxSize);
                    }
                    //System.out.println(ghosts.length);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println();
            }
        }
        @Override
        public boolean isResizable() {
            return false;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }
}
