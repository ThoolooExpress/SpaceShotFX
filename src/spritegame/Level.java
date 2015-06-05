/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

/**
 *
 * @author ThoolooExpress
 */
public abstract class Level {
    private final GameLoop gameLoop;

    
    private final GameManager gameManager;
    // Can't be final because of the way the level creator sets up player stats
    private Player player;
    private final List<Enemy> enemies;
    private final List<Projectile> projectiles;
    private final HUD hud;
    
    private final Pane playFieldLayer;
    private final Group root;
    
    Scene scene;
    
    public Level(GameManager gameManager ) {
        
        this.gameManager = gameManager;
        // TODO : make this its own class.  No particular need, but it would be
        // more consistant. 
        playFieldLayer = new Pane();
        this.hud = new HUD(this);
        gameLoop = new GameLoop(this);
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        
        root = new Group();

    }
    public void start() {
         // input setup
        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        Input input = new Input(scene);
        input.addListeners(); // TODO: remove listeners on game over
        // run level's player setup
        player = setupPlayer(input);
        playFieldLayer.backgroundProperty().set(setupBackground());
        playFieldLayer.minHeightProperty().set(Settings.SCENE_HEIGHT);
        playFieldLayer.minWidthProperty().set(Settings.SCENE_WIDTH);
        root.getChildren().add(playFieldLayer);
        root.getChildren().add(hud);
        
        gameManager.getMainStage().setScene(scene);
        gameLoop.start();
        
    }
    
    /**
     * Function to be done on every tick, probably best used for spawning, and
     * to control the progression of the level;
     */
    public abstract void tick();
    /**
     * Tells the game loop whether the level is still playing, called every tick,
     * but you probably shouldn't put game logic in here, put it in tick() instead.
     * @return 
     */
    public abstract boolean playing();
    /**
     * Sets up the player's ship for this level.  You don't need to worry about
     * input here.
     * @param input The input object you should set your player up with
     * @return The setup player
     */
    // TODO : find a better way to manage the player's ship / stats
    public abstract Player setupPlayer(Input input);
    public abstract Background setupBackground();
    
    // TODO: get rid of this
    public Boolean debugColision;
    
    public GameManager getGameManager() {
        return gameManager;
    }

    public Pane getPlayFieldLayer() {
        return playFieldLayer;
    }

    public Player getPlayer() {
        return player;
        
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public Boolean getDebugColision() {
        return debugColision;
    }

    public HUD getHud() {
        return hud;
    }
}
