package com.cfao.app;


import com.cfao.app.model.PersonneModel;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;


public class Login extends Application {
    static Logger logger = Logger.getLogger(Login.class);

    @Override
    public void init() throws Exception {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new PersonneModel();
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Database Connectivity OK");
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                task.getException().printStackTrace();
                System.err.println("Error Database Connectivity");
            }
        });
        new Thread(task).start();
    }

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
        //LauncherImpl.launchApplication(Login.class, PreloaderUtil.class, args);
        launch(args);
    }

}
