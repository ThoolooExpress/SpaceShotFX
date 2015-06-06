/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import spritegame.GameManager;

/**
 * The Splash Screen
 * @author ThoolooExpress
 */
public class Splash {
    GameManager gameManager;
    public Splash(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void start() {
        Group root = new Group();
        ImageView splashImage = new ImageView(gameManager.getImgCache().getImg("splashBG"));
        root.getChildren().add(splashImage);
        Scene scene = new Scene(root);
        scene.onKeyPressedProperty().set( (KeyEvent e) -> {
            // TODO : Make pressing a certian key to get past splash screen do something special
            // TODO : Make this go to the menu
            gameManager.loadTestLevel();
        });
        gameManager.getMainStage().setScene(scene);
    }
}
