package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.util.FXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class AccueilController implements Initializable, Controller {
    public StackPane content;

    public void displayCivilite(ActionEvent actionEvent) {
        StageManager.loadContent(FXMLView.PERSONNE.getFXMLFile());
    }

    public void add(ActionEvent actionEvent) {
    }

    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
