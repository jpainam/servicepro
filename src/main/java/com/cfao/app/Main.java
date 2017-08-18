package com.cfao.app;

import com.cfao.app.model.Model;
import com.cfao.app.util.FXMLView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.hibernate.Session;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class Main extends Application {
    public static Stage stage;

    // Directory separator
    public static final String DS = File.separator;

    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setMaximized(true);
        stage = primaryStage;
        String iconName = ResourceBundle.getBundle("Application").getString("app.icon");

        Image icon = new Image(getClass().getResourceAsStream(iconName));
        primaryStage.getIcons().add(icon);

        Pane mainPane = FXMLLoader.load(getClass().getResource("/views/template/template.fxml"));
        primaryStage.setTitle(FXMLView.TEMPLATE.getTitle());

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setOnCloseRequest((WindowEvent e) -> Main.this.closeWindow());
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

    @Override
    public void stop() throws Exception {
        Session session = Model.getCurrentSession();
        if(session.isOpen()) {
            session.close();
        }
        super.stop();
    }
}
