package com.cfao.app;


import com.cfao.app.controllers.TemplateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.NotificationPane;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by JP on 6/9/2017.
 */
public class StageManager {
    private static Controller mainController;
    private static NotificationPane notificationPane;
    private static ProgressBar progressBar;

    public static void setMainController(Controller mainController){
        StageManager.mainController = mainController;
    }
    public  static  void loadContent(String fxmlFile){
        try {
            Node content = FXMLLoader.load(TemplateController.class.getResource(fxmlFile));
             mainController.setContent(content);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static void setSelectedCrumb(String key){
        mainController.setSelectedCrumb(key);
    }
    public static NotificationPane getNotificationPane(){
        return notificationPane;
    }
    public static void setNotificationPane(NotificationPane notificationPane){
        StageManager.notificationPane = notificationPane;
    }

    public static ProgressBar getProgressBar(){
        return mainController.getProgressBar();
    }
}
