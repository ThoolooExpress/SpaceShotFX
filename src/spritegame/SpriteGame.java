package spritegame;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

public class SpriteGame extends Application {
    GameManager gameManager;
    @Override
    public void start(Stage primaryStage) {
        gameManager = new GameManager(primaryStage);
        gameManager.loadTestLevel();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
