package spritegame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import spritegame.Sprite;

public class Bomb extends Sprite {
    public Bomb(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        super.collisionRadius = 15;
    }

    @Override
    public void checkRemovability() {

        if( getY() < 0) {
            setRemovable(true);
            System.out.println("Bullet Removed");
        }
    }
    
    public void attack(Enemy enemy) {
        enemy.getDamagedBy(this);
        setRemovable(true);
    }
    @Override
    public void tick(long now) {
        
    }
}