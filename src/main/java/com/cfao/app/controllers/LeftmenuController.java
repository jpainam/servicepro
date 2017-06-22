package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by Communication on 13/06/2017.
 */
public class LeftmenuController implements Initializable{

    @FXML
    public Accordion Leftmenu;

    @FXML
    public ListView listPersonnes;
    @FXML
    public ListView listFormations;
    @FXML
    public ListView listCompetences;
    @FXML
    public ListView listProfils;
    @FXML
    public ListView listTests;
    @FXML
    public ListView listParametres;
    @FXML
    public ListView listRapports;
    public TitledPane personnePane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildleftMenu();
        ServiceproUtil.setAccordionExpanded(Leftmenu, personnePane);
    }

    private void buildleftMenu() {

        ObservableList<Label> data ;
        data = FXCollections.observableArrayList();
        Label labelmenu1 = GlyphsDude.createIconLabel(FontAwesomeIcon.CLOUD_UPLOAD, "Importer", "11",
                "", ContentDisplay.LEFT);
        Label labelmenu2 = GlyphsDude.createIconLabel(FontAwesomeIcon.BED, "Exporter", "11",
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
                            Notifications.create().title("Implémentation")
                                    .text("Fonctionnalité non encore implémentée")
                                    .showInformation();

                            break;
                    }
                });

    }


}