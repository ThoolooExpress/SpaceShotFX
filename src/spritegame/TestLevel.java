/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Replicates the old single level behavior for testing
 *
 * @deprecated 2015-06-05 We should have multiple levels
 * @author ThoolooExpress
 */
// TODO : make this launch using the regular level format
public class TestLevel extends Level {

    public TestLevel(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void tick() {
        // Spawning -- This test level just randomly spawns enemies that move towards the bottom
        // Chances out of 10,000 ticks for an enemy to spawn
        int spawnChance = 125;
        if (Math.abs(getGameManager().getRandom().nextInt()) % 10000 < spawnChance) {
            double x = Math.abs(getGameManager().getRandom().nextInt()) % Settings.SCENE_WIDTH;
            double y = -100;
            double height = 100;
            double width = 100;
            double enemyMaxSpeed = 4.0;
            double enemyMinSpeed = 1.5;
            double speed = getGameManager().getRandom().nextFloat() * (enemyMaxSpeed-enemyMinSpeed) + enemyMinSpeed;
            Enemy e = new Enemy(getPlayFieldLayer(),
                    getGameManager().getImgCache().getImg("enemy"), x, y, 90, 0,
                    speed, 0, width, height );
            getEnemies().add(e);
        }
    }

    @Override
    public boolean playing() {

        return true;
    }

    @Override
    public Player setupPlayer(Input input) {
        Image image = getGameManager().getImgCache().getImg("player");
        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // Create bulletKit, so the player can spawn projectiles
        PlayerHandle ph = new PlayerHandle();
        ph.bulletImage = getGameManager().getImgCache().getImg("laser");
        ph.projectiles = getProjectiles();
        ph.playFieldLayer = getPlayFieldLayer();
        // create player
        return new Player(getPlayFieldLayer(), image, x, y, -90, 0, 0, 0,
                Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED,
                input, ph);
    }

    /**
     * The player handle class, lets the player make bullets
     *
     * @deprecated 2015-06-05 this is a retarded way of doing this
     *
     */
    // TODO : Get rid of this horror story of a class
    @Deprecated
    public class PlayerHandle {

        Image bulletImage;
        List<Projectile> projectiles;
        Pane playFieldLayer;
    }

    /**
     * Sets up the background
     *
     * @return
     */
    @Override
    public Background setupBackground() {
        // TODO : Replace this with an image
        BackgroundFill fill = new BackgroundFill(Color.AQUA, null, null);
        return new Background(fill);

    }

}
