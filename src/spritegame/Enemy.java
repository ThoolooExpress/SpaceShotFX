package spritegame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spritegame.SpriteBase;

public class Enemy extends SpriteBase {
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