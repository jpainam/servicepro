package com.cfao.app;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by JP on 6/9/2017.
 */
public class StageManager {
    private static Controller mainController;
    public static void setMainController(Controller mainController){
        StageManager.mainController = mainController;
    }
    public  static  void loadContent(String fxmlFile){
        try {
            FXMLLoader loader = new FXMLLoader();
            mainController.setContent((Node) loader.load(new FileInputStream(fxmlFile)));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
