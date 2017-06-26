package com.cfao.app;


import com.cfao.app.controllers.TemplateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.NotificationPane;

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
            Node content = FXMLLoader.load(TemplateController.class.getResource(fxmlFile));
             //mainController.setContent(content);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    /*public static NotificationPane getNotificationPane() {
        return mainController.getNotificationPane();
    }*/
}
