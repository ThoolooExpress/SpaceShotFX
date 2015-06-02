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

public class GameLoop extends AnimationTimer {

    SpriteGame handle;
    
    public GameLoop(SpriteGame handle) {
        super();
        this.handle = handle;
    }

    @Override
    public void handle(long now) {

        // player input
        handle.player.processInput(now);

        // add random enemies
        spawnEnemies(true);

        // movement
        handle.player.move();
        handle.enemies.forEach(sprite -> sprite.move());
        handle.bullets.forEach(sprite -> sprite.move());

        // check collisions
        checkCollisions();

        // update sprites in scene
        handle.player.updateUI();
        handle.enemies.forEach(sprite -> sprite.updateUI());
        handle.bullets.forEach(sprite -> sprite.updateUI());

        // check if sprite can be removed
        handle.enemies.forEach(sprite -> sprite.checkRemovability());
        handle.bullets.forEach(sprite -> sprite.checkRemovability());

        // remove removables from list, layer, etc
        removeSprites(handle.enemies);
        removeSprites(handle.bullets);

        // update score, health, etc
        handle.updateScore();
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
private void spawnEnemies(boolean random) {

        if (random && handle.rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = handle.enemyImage;

        // random speed
        double speed = handle.rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = handle.rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy(handle.playfieldLayer, image, x, y, 90, 0, speed, 0, 1, 1);

        // manage sprite
        handle.enemies.add(enemy);

    }
    public void checkCollisions() {
        handle.collision = false;

        for (Enemy enemy : handle.enemies) {
            if (handle.player.collidesWith(enemy)) {
                handle.collision = true;
            }
        }
        for (Bomb bullet : handle.bullets) {
            for (Enemy enemy : handle.enemies) {
                if (bullet.collidesWith(enemy)) {
                    handle.collision = true;
                    bullet.attack(enemy);
                }
            }
        }
    }
}