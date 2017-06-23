package com.cfao.app;

import com.cfao.app.util.FXMLView;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        String iconName = ResourceBundle.getBundle("Application").getString("app.icon");

        Image icon = new Image(new FileInputStream(iconName));
        primaryStage.getIcons().add(icon);

        FileInputStream f = new FileInputStream(FXMLView.LOGIN.getFXMLFile());
        FXMLLoader loader = new FXMLLoader();
        Pane loginPane = loader.load(f);


        primaryStage.setTitle(FXMLView.LOGIN.getTitle());
        primaryStage.setMaximized(true);

        Scene scene = new Scene(loginPane);
        String css = getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        primaryStage.setOnCloseRequest(e->closeWindow());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public double getWidth(double per){
        double w = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
        return (per * w) / 100;
    }

    public double getHeight(double per){
        double h = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
        return (per * h) / 100;
    }
    private void closeWindow(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
