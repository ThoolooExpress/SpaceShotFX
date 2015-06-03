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

public class SpriteGame extends Application {

    Random rnd = new Random();
// Layers
    Pane playfieldLayer;
    HUD hud;
// Image Cache 
    ImgCache img = new ImgCache();
    //TODO: Move this to its own object
    Image playerImage;
    Image enemyImage;
    Image bulletImage;

// Sprite Management
    //TODO: put this in its own object, it clutters the main file
    Player player;
    List<Enemy> enemies = new ArrayList<>();
    List<Bomb> bullets = new ArrayList<>();
// The debug text that appears whenever a colision happens
    //TODO: remove this from final game
    Text debugCollisionText = new Text();
    //TODO: group this with sprite management in some way
    SpriteSheet health;
    // Did a collision just happen? (for debug)
    //TODO: find a prettier way to handle this
    boolean collision = false;
// The main scene
    Scene scene;

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
        // Set background on playfieldLayer
        BackgroundFill fill = new BackgroundFill(Color.AQUA, null, null);
        Background bg = new Background(fill);
        playfieldLayer.backgroundProperty().set(bg);


        //TODO: replace this with a real handle
        hud = new HUD(this);
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(hud);

        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);


        createPlayers();
        primaryStage.show();
        GameLoop gameLoop = new GameLoop(this);
        gameLoop.start();

    }


    private void createPlayers() {

        // player input
        Input input = new Input(scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = img.getImg("player");

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // Create bulletKit, so the player can spawn bullets
        PlayerHandle ph = new PlayerHandle();
        ph.bulletImage = img.getImg("laser");
        ph.bullets = this.bullets;
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
     * The main handle for the game loop
     */
    public class MainHandle {

    }

    /**
     * Holds handles the player handle needs
     */
    public class PlayerHandle {

        Image bulletImage;
        List<Bomb> bullets;
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
