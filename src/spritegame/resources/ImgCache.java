/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spritegame.resources;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

/**
 * Holds all the resources to eliminate path headaches
 *
 * @author ThoolooExpress
 */
public class ImgCache {

    // All the files that could possibly be loaded, their paths, and their names
    final ImgData[] manifest = {
        img("enemy", "enemy.png", 100, 100),
        img("player", "player.png", 100, 100),
        img("laser", "laser.png", 30, 30),
        img("healthbar", "healthbar.png", 600, 17)
    };
    Map<String, ImgData> manifestMap = new HashMap<>();
    Map<String, Image> images = new HashMap<>();

    public ImgCache() {
        for (ImgData top : manifest) {
            manifestMap.put(top.name, top);
        }
    }

    public void load(String ID) {
        if (!images.containsKey(ID)) {
            System.out.println("Disk Hit!");
            System.out.println("Getting " + ID);
            ImgData top = manifestMap.get(ID);
            images.put(ID,
                    new Image(this.getClass().getResourceAsStream(top.path),
                            top.width, top.height, true, true));
        }
    }

    public Image getImg(String ID) {
        Image i = null;
        try {
            if (!images.containsKey(ID)) {
                load(ID);
            }
            i = images.get(ID);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No such image.");
            e.printStackTrace();
        }
        return i;
    }

    private ImgData img(String name, String path, int width, int height) {
        return new ImgData(name, path, width, height);
    }

    public class ImgData {

        String name;
        String path;
        int height;
        int width;

        public ImgData(String name, String path, int width, int height) {
            this.name = name;
            this.path = path;
            this.height = height;
            this.width = width;
        }

    }

}
