/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author ThoolooExpress
 */
public class HUD extends Pane {
    // The debug collision text
    Text debugCollisionText = new Text();
    // A refrence to the main class to get score information
    // TODO: replace this with a specialized handle
    SpriteGame handle;
    public HUD(SpriteGame handle) {
        super();
        this.handle = handle;
        debugCollisionText.setFont(Font.font(null, FontWeight.BOLD, 64));
        debugCollisionText.setStroke(Color.BLACK);
        debugCollisionText.setFill(Color.RED);

        this.getChildren().add(debugCollisionText);

//        scoreLayer.getChildren().add(health);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        debugCollisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - debugCollisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - debugCollisionText.getBoundsInLocal().getHeight()) / 2;
        debugCollisionText.relocate(x, y);
        debugCollisionText.setText("");

        debugCollisionText.setBoundsType(TextBoundsType.VISUAL);
    }
    public void update() {

        if (handle.collision) {
            debugCollisionText.setText("Collision");
        } else {
            debugCollisionText.setText("");
        }
    }
    
}
