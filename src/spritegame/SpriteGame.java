package spritegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import spritegame.resources.ImgCache;

public class SpriteGame extends Application {

    Random rnd = new Random();
// Layers
    Pane playfieldLayer;
    Pane scoreLayer;
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
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add(playfieldLayer);
        root.getChildren().add(scoreLayer);

        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);


        createScoreLayer();
        createPlayers();
        primaryStage.show();
        GameLoop gameLoop = new GameLoop(this);
        gameLoop.start();

    }


    private void createScoreLayer() {

        debugCollisionText.setFont(Font.font(null, FontWeight.BOLD, 64));
        debugCollisionText.setStroke(Color.BLACK);
        debugCollisionText.setFill(Color.RED);

        scoreLayer.getChildren().add(debugCollisionText);

//        scoreLayer.getChildren().add(health);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        debugCollisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - debugCollisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - debugCollisionText.getBoundsInLocal().getHeight()) / 2;
        debugCollisionText.relocate(x, y);
        debugCollisionText.setText("");

        debugCollisionText.setBoundsType(TextBoundsType.VISUAL);

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



    public void updateScore() {

        if (collision) {
            debugCollisionText.setText("Collision");
        } else {
            debugCollisionText.setText("");
        }
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
