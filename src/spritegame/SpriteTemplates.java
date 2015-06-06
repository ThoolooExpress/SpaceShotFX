/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import javafx.scene.image.Image;
import spritegame.SpriteSheet.Frame;
import spritegame.SpriteSheet.Offsets;


/**
 * Holds sprite templates
 * @author ThoolooExpress
 * @deprecated 2015-6-6 - There's gotta be a better way...
 * 
 */
public class SpriteTemplates {
    public static SpriteSheet healthBar(Image image) {
        Frame[] frames = {
            new Frame(4,0),
            new Frame(3,0),
            new Frame(2,0),
            new Frame(1,0)
        };
        int frameWidth = 120;
        int frameHeight = 17;
        Offsets sheetOffsets = new Offsets(0,0);
        Offsets frameOffsets = new Offsets(0,0);
        return new SpriteSheet(image, frames, frameWidth, frameHeight, sheetOffsets, frameOffsets);
    }
}
