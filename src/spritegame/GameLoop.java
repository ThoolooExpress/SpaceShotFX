package spritegame;

import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 * The main game loop
 * @author ThoolooExpress
 */
public class GameLoop extends AnimationTimer {
    final Level levelHandle;
    public GameLoop(Level levelHandle) {
        super();
        this.levelHandle = levelHandle;
        
        
    }

    @Override
    public void handle(long now) {

        // player input
        levelHandle.getPlayer().processInput(now);

        // add random enemies
        //spawnEnemies(true);

        // movement
        levelHandle.getPlayer().move();
        levelHandle.getEnemies().forEach(sprite -> sprite.move());
        levelHandle.getProjectiles().forEach(sprite -> sprite.move());

        // check collisions
        checkCollisions();

        // update sprites in scene
        levelHandle.getPlayer().updateUI();
        levelHandle.getEnemies().forEach(sprite -> sprite.updateUI());
        levelHandle.getProjectiles().forEach(sprite -> sprite.updateUI());

        // check if sprite can be removed
        levelHandle.getEnemies().forEach(sprite -> sprite.checkRemovability());
        levelHandle.getProjectiles().forEach(sprite -> sprite.checkRemovability());

        // remove removables from list, layer, etc
        removeSprites(levelHandle.getEnemies());
        removeSprites(levelHandle.getProjectiles());

        // update score, health, etc
        levelHandle.getHud().update();
        // Tick the level handle (for spawning, etc)
        // TODO : make it easier for the levelhandle to know the timestamp
        levelHandle.tick();
        // Check to see if the level should continue, if not, then stop this
        if (!levelHandle.playing()) {
            this.stop();
        }
    }

    /**
     * Remove unnecessary sprites from the specified list
     * @param spriteList - the list to be cleaned
     */
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
    /**
     * Check for collisions, and run handlers
     */
    public void checkCollisions() {
        levelHandle.debugColision = false;
    
        for (Enemy enemy : levelHandle.getEnemies()) {
            if (levelHandle.getPlayer().collidesWith(enemy)) {
                levelHandle.debugColision = true;
            }
        }
        for (Projectile bullet : levelHandle.getProjectiles()) {
            for (Enemy enemy : levelHandle.getEnemies()) {
                if (bullet.collidesWith(enemy)) {
                    levelHandle.debugColision = true;
                    System.out.println("Bullet " + bullet.hashCode() + " hit " + enemy.hashCode());
                    bullet.attack(enemy);
                }
            }
        }
    }
}
