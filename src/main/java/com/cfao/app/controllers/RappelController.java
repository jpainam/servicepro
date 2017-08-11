package com.cfao.app.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/30/2017.
 */
public class RappelController implements Initializable {
    public TreeView rappelTreeview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<String>("Rappels");
        TreeItem<String> personne = new TreeItem<String>("Civilité");
        personne.getChildren().addAll(new TreeItem<>("Carlos NDIANE"),
                new TreeItem<>("Patrice MUKADI"),
                new TreeItem<>("Eddy KINIA"));
        TreeItem<String> formation = new TreeItem<String>("Formations");
        formation.getChildren().add(new TreeItem<>("INTRODUCTION TOYOTA FORKLIFT CI & ELECTRIQUE"));
        TreeItem<String> certification = new TreeItem<String>("Certifications");
        root.setExpanded(true);
        root.getChildren().addAll(personne, formation, certification);
        rappelTreeview.setRoot(root);
    }
}
