package spritegame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spritegame.Sprite;
import spritegame.TestLevel.PlayerHandle;
/**
 * Defines the player's ship
 * @author ThoolooExpress
 */
public class Player extends Sprite {

    double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;

    Input input;

    double speed;

    PlayerHandle ph; // ph - player handle
    boolean firstShot;
    long lastShot;

    public Player(Pane layer, Image image, double x, double y, double r,
            double dx, double dy, double dr, double health, double damage,
            double speed, Input input, PlayerHandle bulletKit) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        this.firstShot = true;
        this.lastShot = 0;

        super.collisionRadius = 45;

        this.speed = speed;
        this.input = input;
        this.ph = bulletKit;
        init();
    }

    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen 
        playerShipMinX = 0 - image.getWidth() / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - image.getWidth() / 2.0;
        playerShipMinY = 0 - image.getHeight() / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT - image.getHeight() / 2.0;

    }
    /**
     * Deals with input
     * @param now - the current time in nanoseconds
     */
    // TODO : abstract away shooting and physics
    public void processInput(long now) {

        // ------------------------------------
        // movement
        // ------------------------------------
        // vertical direction
        if (input.isMoveUp()) {
            dy = -speed;
        } else if (input.isMoveDown()) {
            dy = speed;
        } else {
            dy = 0d;
        }

        // horizontal direction
        if (input.isMoveLeft()) {
            dx = -speed;
        } else if (input.isMoveRight()) {
            dx = speed;
        } else {
            dx = 0d;
        }

        if (input.isFirePrimaryWeapon()) {
            shootMain(now);
        }

    }

    @Override
    public void move() {

        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();

    }
    /**
     * Shoots the main gun
     * @param now - the current time in milliseconds
     */
    // TODO : abstract away and modularize weapons
    private void shootMain(long now) {
        if (firstShot || now - lastShot > 600 * 1000000) {
            System.out.println("shooting");

            // image
            Image image = ph.bulletImage;

            // bullet speed (vertical)
            double minSpeed = -10;
            double speed = -6 + dy;
            if (speed > minSpeed ) {
                speed = minSpeed;
            }

        // x position range: enemy is always fully inside the screen, no part of it is outside
            // y position: right on top of the view, so that it becomes visible with the next game iteration
            double x = this.getCenterX();
            double y = this.getCenterY();

            // create a sprite
            Projectile enemy = new Projectile(ph.playFieldLayer, image, x, y, 0, dx, speed, 0, 50, 50);

            // manage sprite
            ph.projectiles.add(enemy);
            
            firstShot = false;
        }
        lastShot = now;
    }
    /**
     * Checks to see if the ship is within its bounds
     */
    
    // TODO : Make this less retarded
    private void checkBounds() {

        // vertical
        if (Double.compare(y, playerShipMinY) < 0) {
            y = playerShipMinY;
        } else if (Double.compare(y, playerShipMaxY) > 0) {
            y = playerShipMaxY;
        }

        // horizontal
        if (Double.compare(x, playerShipMinX) < 0) {
            x = playerShipMinX;
        } else if (Double.compare(x, playerShipMaxX) > 0) {
            x = playerShipMaxX;
        }

    }

    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

    @Override
    public void tick(long now) {

    }

}
