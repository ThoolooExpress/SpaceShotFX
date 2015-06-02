/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import spritegame.SpriteSheet;

/**
 *
 * @author ThoolooExpress
 */
public class spriteSheetTest extends Application {
    SpriteSheet healthBarTest;
    
    @Override
    public void start(Stage primaryStage) {
       
        Group root = new Group();
//        healthBarTest = SpriteTemplates.healthBar(new Image(
//                "healthbar.png")
//        );
//        root.getChildren().add(healthBarTest);
        root.getChildren().add(new ImageView(new Image("healthbar.png")));
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        for (int iii = 0; iii < 100; iii++) {
            healthBarTest.setFrame(iii % 5);
            System.out.println("Switched Frame");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(spriteSheetTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
