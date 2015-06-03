package spritegame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spritegame.Sprite;

public class Enemy extends Sprite {
    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        super.collisionRadius = 45;
    }

    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0 || this.health <= 0) {
            setRemovable(true);
        }
    }
    @Override
    public void tick(long now) {
        
    }
}