/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import spritegame.menu.Splash;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.stage.Stage;
import spritegame.resources.ImgCache;

/**
 * Handles transitions between levels  / scenes / menus, holds data that should
 * persist for the session
 * @author ThoolooExpress
 */
public class GameManager {

    Splash splashScreen;
    // Player info
    String playerName;
    int score;
    
    // Debug Collision
    Boolean debugCollision;
    
    Stage mainStage;
    Map<String, Level> levels = new HashMap<>();
    GameLoop gameLoop;
    HUD hud;

    
    Random random;
    
    ImgCache imgCache;
    
    public GameManager(Stage mainStage) {
        this.mainStage = mainStage;
        imgCache = new ImgCache();
        random = new Random(System.currentTimeMillis());
    }

    public Stage getMainStage() {
        return mainStage;
    }
    
    boolean paused = false;
    
    public void pauseGame() {
        paused = true;
        gameLoop.stop();
        //hud.hide();
    }
    
    public void continueGame() {
        if (paused) {
            paused = false;
            gameLoop.start();
            //hud.show();
        }
    }
    
    
    public void splash() {
        
    }
    
    public void menu() {
        
    }
    
    public void loadLevel(String id) {
        
    }
    
    public void loadTestLevel() {
        Level testLevel = new TestLevel(this);
        testLevel.start();
    }
    
    public ImgCache getImgCache() {
        return imgCache;
    }
    public Random getRandom() {
        return random;
    }
}
