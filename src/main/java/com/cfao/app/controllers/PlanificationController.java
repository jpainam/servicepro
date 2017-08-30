package com.cfao.app.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class PlanificationController implements Initializable{

    public AnchorPane sujetContent;
    public AnchorPane tacheContent;
    public AnchorPane modeleContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }
    private void initComponents(){
        sujetContent.getChildren().setAll(new PlanificationSujetController());
        tacheContent.getChildren().setAll(new PlanificationTacheController());
        modeleContent.getChildren().setAll(new PlanificationModeleController());
    }
}
