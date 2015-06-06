package spritegame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Defines a projectile
 * 
 * @author ThoolooExpress
 */
public class Projectile extends Sprite {
    public Projectile(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        super.collisionRadius = 15;
    }

    @Override
    public void checkRemovability() {
        if( getY() < -40) {
            setRemovable(true);
            System.out.println("Bullet Removed");
        }
    }
    
    public void attack(Enemy enemy) {
        
        enemy.getDamagedBy(this);
        System.out.println("Bullet " + this.hashCode() + " attacking " +
                enemy.hashCode() + " Which has " + enemy.getHealth() + "hp");
        enemy.getHealth();
        setRemovable(true);
    }
    @Override
    public void tick(long now) {
        
    }
}