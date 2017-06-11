package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.util.FXMLView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.swing.plaf.ProgressBarUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class AccueilController implements Initializable, Controller {
    public StackPane content;
    public ImageView cfaoLogo;
    public ProgressBar progressBar;

    public void displayCivilite(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {
            @Override public Void call() {
                 final int max = 1000000;
                for (int i=1; i<=max; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        StageManager.loadContent(FXMLView.PERSONNE.getFXMLFile());
    }

    public void add(ActionEvent actionEvent) {
    }

    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void initialize(URL location, ResourceBundle resources) {
        try {
            cfaoLogo.setImage(new Image(new FileInputStream("src/main/resources/images/logo.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
