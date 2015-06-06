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
 * Defines a sprite sheet, which can be used for animations, or to easily switch
 * graphics.  Intended to substitute for ImageView
 * @author ThoolooExpress
 */
public class SpriteSheet extends ImageView {
    Image sheet;
    
    /**
     * Defines the coordinates of one frame
     */
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
    
    /**
     * Defines the offsets for either one frame or the whole sheet
     */
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
    
    // The current frame bieng displayed
    int currentFrame;
    
    // All the possible frames
    Rectangle2D[] frames;
    
    /**
     * 
     * @param sheet - the image to be used
     * @param frames - all the frames that can be displayed
     * @param frameWidth - the width of one frame
     * @param frameHeight - the height of one frame
     * @param sheetOffsets - the offsets for the entire sheet
     * @param frameOffsets - the offsets for each frame
     */
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
    /**
     * Goes to the desired frame
     * @param iii - the frame number to go to
     */
    public void setFrame(int iii) {
        super.setViewport(frames[iii]);
    }
}
