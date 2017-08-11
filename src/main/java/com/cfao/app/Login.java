package com.cfao.app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ResourceBundle;


public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/views/login/login.fxml"));

        String iconName = ResourceBundle.getBundle("Application").getString("app.icon");

        Image icon = new Image(getClass().getResourceAsStream(iconName));
        stage.getIcons().add(icon);

        Scene scene = new Scene(root, 650, 550);
        /*
        stage.setX(windows.getMinX());
        stage.setY(windows.getMinY());
        stage.setWidth(windows.getWidth());
        stage.setHeight(windows.getHeight());
         */
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());

        stage.setTitle("Page de Connexion");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
