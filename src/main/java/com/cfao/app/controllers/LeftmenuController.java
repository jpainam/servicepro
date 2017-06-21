package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by Communication on 13/06/2017.
 */
public class LeftmenuController{

    public ListView listPersonnes;
    public ListView listFormations;
    public ListView listCompetences;
    public ListView listProfils;
    public ListView listTests;
    public ListView listParametres;
    public ListView listRapports;


    public Control parent;


    public LeftmenuController(Control parent, ListView... list) {

        this.parent = parent;
        this.listPersonnes = list[0];
        this.listFormations = list[1];
        this.listCompetences = list[2];
        this.listProfils = list[3];
        this.listTests = list[4];
        this.listParametres = list[5];
        this.listRapports = list[6];
        buildleftMenu();

    }

    private void buildleftMenu() {

        ObservableList<Label> data ;
        data = FXCollections.observableArrayList();
        Label labelmenu1 = GlyphsDude.createIconLabel(FontAwesomeIcon.CLOUD_UPLOAD, "Importer", "11",
                "", ContentDisplay.LEFT);
        Label labelmenu2 = GlyphsDude.createIconLabel(FontAwesomeIcon.BED, "menu 2", "11",
                "", ContentDisplay.LEFT);
        data.addAll(labelmenu1, labelmenu2);
        listPersonnes.setItems(data);
        listPersonnes.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue observable, Object oldValue, Object newValue) -> {
                    switch (listPersonnes.getSelectionModel().getSelectedIndex()){
                        case 0:
                            //fonction menu 1
                            String fxml = "src/main/java/com/cfao/app/views/civilite/import.fxml";
                            StageManager.loadContent(fxml);
                            break;
                        case 1:
                            //fonction menu 2
                            System.out.println("menu 2");
                            break;
                    }
                });

    }


}