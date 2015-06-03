package spritegame;

import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import spritegame.SpriteGame.LevelHandle;
import spritegame.resources.ImgCache;

public class GameLoop extends AnimationTimer {

    LevelHandle handle;
    Player player;
    List<Enemy> enemies;
    List<Projectile> projectiles;
    HUD hud;
    ImgCache img;
    Boolean debugCollision;
    
    public GameLoop(LevelHandle handle) {
        super();
        this.handle = handle;
        player = handle.getPlayer();
        enemies = handle.getEnemies();
        projectiles = handle.getProjectiles();
        hud = handle.getHud();
        img = handle.getImg();
        debugCollision = handle.getDebugColision();
        
        
    }

    @Override
    public void handle(long now) {

        // player input
        player.processInput(now);

        // add random enemies
        //spawnEnemies(true);

        // movement
        player.move();
        enemies.forEach(sprite -> sprite.move());
        projectiles.forEach(sprite -> sprite.move());

        // check collisions
        checkCollisions();

        // update sprites in scene
        player.updateUI();
        enemies.forEach(sprite -> sprite.updateUI());
        projectiles.forEach(sprite -> sprite.updateUI());

        // check if sprite can be removed
        enemies.forEach(sprite -> sprite.checkRemovability());
        projectiles.forEach(sprite -> sprite.checkRemovability());

        // remove removables from list, layer, etc
        removeSprites(enemies);
        removeSprites(projectiles);

        // update score, health, etc
        hud.update();
    }


    private void removeSprites(List<? extends Sprite> spriteList) {
        Iterator<? extends Sprite> iter = spriteList.iterator();
        Sprite sprite;
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
//private void spawnEnemies(boolean random) {
//
//        if (random && handle.rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
//            return;
//        }
//
//        // image
//        Image image = handle.img.getImg("enemy");
//
//        // random speed
//        double speed = handle.rnd.nextDouble() * 1.0 + 2.0;
//
//        // x position range: enemy is always fully inside the screen, no part of it is outside
//        // y position: right on top of the view, so that it becomes visible with the next game iteration
//        double x = handle.rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
//        double y = -image.getHeight();
//
//        // create a sprite
//        Enemy enemy = new Enemy(handle.playfieldLayer, image, x, y, 90, 0, speed, 0, 1, 1);
//
//        // manage sprite
//        handle.enemies.add(enemy);
//
//    }
    public void checkCollisions() {
        debugCollision = false;

        for (Enemy enemy : enemies) {
            if (player.collidesWith(enemy)) {
                debugCollision = true;
            }
        }
        for (Projectile bullet : projectiles) {
            for (Enemy enemy : enemies) {
                if (bullet.collidesWith(enemy)) {
                    debugCollision = true;
                    bullet.attack(enemy);
                }
            }
        }
    }
}
