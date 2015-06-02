/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ThoolooExpress
 */
public class SpriteSheet extends ImageView {
    Image sheet;
    
    public static class Frame {
        int x;
        int y;
        public Frame(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    int frameWidth;
    int frameHeight;
    
    public static class Offsets {
        public final int top;
        public final int left;
        
        public Offsets(int left, int top) {
            this.left = left;
            this.top = top;
        }
    }
    
    Offsets sheetOffsets;
    Offsets frameOffsets;
    
    int currentFrame;
    
    Rectangle2D[] frames;
    
    SpriteSheet(Image sheet, Frame[] frames, int frameWidth, int frameHeight,
            Offsets sheetOffsets, Offsets frameOffsets) {
        super();
        this.sheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.sheetOffsets = sheetOffsets;
        this.frameOffsets = frameOffsets;
        this.frames = new Rectangle2D[frames.length];
        for ( int iii = 0; iii < frames.length ; iii ++) {
            double minX = sheetOffsets.left + (frameOffsets.left * frames[iii].x) + (frameWidth * frames[iii].x);
            double minY = sheetOffsets.top + (frameOffsets.top * frames[iii].y) + (frameHeight * frames[iii].y);
            this.frames[iii] = new Rectangle2D(minX, minY, frameWidth, frameHeight);
        }
        super.setViewport(this.frames[0]);
    }
    
    public void setFrame(int iii) {
        super.setViewport(frames[iii]);
    }
}
