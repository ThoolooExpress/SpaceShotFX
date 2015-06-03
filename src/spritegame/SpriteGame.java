package spritegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spritegame.resources.ImgCache;
import java.awt.Toolkit;

public class SpriteGame extends Application {

    Random rnd = new Random();
// Layers
    Pane playfieldLayer;
    HUD hud;
// Image Cache 
    ImgCache img = new ImgCache();

// Sprite Management
    //TODO: put this in its own object, it clutters the main file
    Player player;
    List<Enemy> enemies = new ArrayList<>();
    List<Projectile> projectiles = new ArrayList<>();
// The debug text that appears whenever a colision happens
    //TODO: remove this from final game
    Text debugCollisionText = new Text();
    //TODO: group this with sprite management in some way
    SpriteSheet health;
    Boolean debugCollision;
// The main scene
    Scene scene;

    public SpriteGame() {
        this.debugCollision = new Boolean(false);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
//        
//        health = SpriteTemplates.healthBar(
//            new Image(this.getClass().getResourceAsStream("resources/healthbar.png"),
//            600, 17, true, true)
//        );

        // create layers
        //  TODO: make this for each level not the whole game
        playfieldLayer = new Pane();

        playfieldLayer.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        // If we're dealing with an ancient screen, scale it down
        // Set background on playfieldLayer
        BackgroundFill fill = new BackgroundFill(Color.AQUA, null, null);
        Background bg = new Background(fill);
        playfieldLayer.backgroundProperty().set(bg);

        //TODO: replace this with a real handle
        hud = new HUD(this);
        root.getChildren().add(playfieldLayer);
        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);

        createPlayer();
        LevelHandle thisLevel = new LevelHandle(img, player, enemies,
                projectiles, debugCollision, hud);
        GameLoop gameLoop = new GameLoop(thisLevel);

        // Start the game (don't do any setup after this)
        primaryStage.show();
        gameLoop.start();

    }

    private void createPlayer() {

        // player input
        Input input = new Input(scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = img.getImg("player");

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // Create bulletKit, so the player can spawn projectiles
        PlayerHandle ph = new PlayerHandle();
        ph.bulletImage = img.getImg("laser");
        ph.projectiles = this.projectiles;
        ph.playFieldLayer = this.playfieldLayer;
        // create player
        player = new Player(playfieldLayer, image, x, y, -90, 0, 0, 0,
                Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED,
                input, ph);

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The handle for the level
     */
    public class LevelHandle {

        public LevelHandle(ImgCache img, Player player, List<Enemy> enemies,
                List<Projectile> projectiles, Boolean debugColision, HUD hud) {
            this.img = img;
            this.player = player;
            this.enemies = enemies;
            this.projectiles = projectiles;
            this.debugColision = debugColision;
            this.hud = hud;
        }
        private final ImgCache img;
        private final Player player;
        private final List<Enemy> enemies;
        private final List<Projectile> projectiles;
        private final HUD hud;

        private final Boolean debugColision;

        public ImgCache getImg() {
            return img;
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

    /**
     * Holds handles the player handle needs
     */
    public class PlayerHandle {

        Image bulletImage;
        List<Projectile> projectiles;
        Pane playFieldLayer;
    }

    /**
     * Holds handles the HUD needs
     */
    public class ScoreHandle {

        int score;
        Player player;
    }

}
