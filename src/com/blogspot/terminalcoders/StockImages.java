package com.blogspot.terminalcoders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StockImages {
    Image wallImg = null;
    Image doorImg = null;
    Image scaredWallImg = null;
    StockImages(){
        try {
            this.wallImg = new Image(this.getClass().getResource("assets/wall.png").toString());
            this.doorImg = new Image(this.getClass().getResource("assets/door.png").toString());
            this.scaredWallImg = new Image(this.getClass().getResource("assets/scaredwall.png").toString());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public Image getWallImage(){
        return this.wallImg;
    }

    public Image getDoorImg() {
        return doorImg;
    }

    public Image getScaredWallImg(){
        return scaredWallImg;
    }
}
