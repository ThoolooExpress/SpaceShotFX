package spritegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
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

public class SpriteGame extends Application {

    Random rnd = new Random();
// Layers
    Pane playfieldLayer;
    Pane scoreLayer;
// Image Cache 
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

        loadGame();

        createScoreLayer();
        createPlayers();
        primaryStage.show();
        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                player.processInput(now);

                // add random enemies
                spawnEnemies(true);

                // movement
                player.move();
                enemies.forEach(sprite -> sprite.move());
                bullets.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                player.updateUI();
                enemies.forEach(sprite -> sprite.updateUI());
                bullets.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());
                bullets.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites(enemies);
                removeSprites(bullets);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }

    private void loadGame() {
        playerImage = new Image(this.getClass().getResourceAsStream("resources/player.png"), 100, 100, true, true);
        enemyImage = new Image(this.getClass().getResourceAsStream("resources/enemy.png"), 100, 100, true, true);
        bulletImage = new Image(this.getClass().getResourceAsStream("resources/laser.png"), 30, 30, true, true);
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

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // Create bulletKit, so the player can spawn bullets
        PlayerHandle ph = new PlayerHandle();
        ph.bulletImage = this.bulletImage;
        ph.bullets = this.bullets;
        ph.playFieldLayer = this.playfieldLayer;
        // create player
        player = new Player(playfieldLayer, image, x, y, -90, 0, 0, 0,
                Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED,
                input, ph);

    }

    private void spawnEnemies(boolean random) {

        if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy(playfieldLayer, image, x, y, 90, 0, speed, 0, 1, 1);

        // manage sprite
        enemies.add(enemy);

    }

    private void removeSprites(List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        SpriteBase sprite;
        while (iter.hasNext()) {
            sprite = iter.next();

            if (sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    public void checkCollisions() {

        collision = false;

        for (Enemy enemy : enemies) {
            if (player.collidesWith(enemy)) {
                collision = true;
            }
        }
        for (Bomb bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.collidesWith(enemy)) {
                    collision = true;
                    bullet.attack(enemy);
                }
            }
        }
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
