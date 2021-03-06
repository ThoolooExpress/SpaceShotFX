package spritegame;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import spritegame.resources.ImgCache;

/**
 * Was supposed to hold the game state, but we ended up doing it a different way
 * @deprecated 2015-6-6 Really should just be deleted now...
 * @author ThoolooExpress
 */
public abstract class State {
    // The start method for this state
    public abstract void start(Stage mainStage);
    /**
     * The handle for the level
     */
    public class LevelHandle {

        public LevelHandle(ImgCache img, Player player, List<Enemy> enemies,
                List<Projectile> projectiles, Boolean debugColision, HUD hud) {
            this.img = img;
            this.player = player;
            this.enemies = enemies;
            this.projectiles = projectiles;
            this.debugColision = debugColision;
            this.hud = hud;
        }
        private final ImgCache img;
        private final Player player;
        private final List<Enemy> enemies;
        private final List<Projectile> projectiles;
        private final HUD hud;

        private final Boolean debugColision;

        public ImgCache getImg() {
            return img;
        }

        public Player getPlayer() {
            return player;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public List<Projectile> getProjectiles() {
            return projectiles;
        }

        public Boolean getDebugColision() {
            return debugColision;
        }

        public HUD getHud() {
            return hud;
        }

    }

    /**
     * Holds handles the player handle needs
     */
    public class PlayerHandle {

        Image bulletImage;
        List<Projectile> projectiles;
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
