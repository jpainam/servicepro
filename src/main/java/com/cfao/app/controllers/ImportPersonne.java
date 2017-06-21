package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Pays;
import com.cfao.app.beans.Societe;
import com.sun.org.apache.xerces.internal.impl.xs.XSModelImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import com.cfao.app.model.Model;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by armel on 17/06/2017.
 */
public class ImportPersonne implements Initializable{
    @FXML
    public Button importer;
    @FXML
    public Button valider;
    @FXML
    public TableView table;
    public TableColumn numeroColumn;
    public TableColumn matriculeColumn;
    public TableColumn nomColumn;
    public TableColumn prenomColumn;
    public TableColumn paysColumn;
    public TableColumn groupColumn;
    public TableColumn societeColumn;
    public TableColumn sectionColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Button getButtonImport(){return importer;}
    public Button getButtonValider(){return valider;}
    public TableView getTable(){return table;}


    public void btnImporter(ActionEvent actionEvent) {
        FileChooser chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Fichier Excel", "*.xls"),
                new FileChooser.ExtensionFilter("Fichier Excel", ".xlsx")
        );

        Stage stage = (Stage)((Parent) importer).getScene().getWindow();

        File selectFile = chooseFile.showOpenDialog(stage);

        if(selectFile != null){
            System.out.println(chooseFile.getSelectedExtensionFilter().getExtensions().get(0));

            try {
                FileReader read = new FileReader(selectFile);
                BufferedReader lecteur = new BufferedReader(read);
                ObservableList<String[]> list = FXCollections.observableArrayList();
                for(String line = lecteur.readLine(); line != null; line = lecteur.readLine()){
                    list.add(line.split(";"));
                }

                table.setItems(list);

                table.setEditable(true);

                numeroColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures param) {
                        SimpleStringProperty simple = new SimpleStringProperty(((String [])param.getValue())[0]);
                        return simple;
                    }
                });

                matriculeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures param) {
                        SimpleStringProperty simple = new SimpleStringProperty(((String [])param.getValue())[1]);
                        return simple;
                    }
                });
                matriculeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                matriculeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent event) {
                        ((String []) event.getTableView().getItems().get(event.getTablePosition().getRow()))[1] = (String) event.getNewValue();
                    }
                });

                nomColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures param) {
                        SimpleStringProperty simple = new SimpleStringProperty(((String [])param.getValue())[2]);
                        return simple;
                    }
                });
                nomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                nomColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent event) {
                        ((String []) event.getTableView().getItems().get(event.getTablePosition().getRow()))[2] = (String) event.getNewValue();
                    }
                });

                prenomColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures param) {
                        SimpleStringProperty simple = new SimpleStringProperty(((String [])param.getValue())[3]);
                        return simple;
                    }
                });
                prenomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                prenomColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent event) {
                        ((String []) event.getTableView().getItems().get(event.getTablePosition().getRow()))[3] = (String) event.getNewValue();
                    }
                });

                paysColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
                List<Pays> listPays = new Model<Pays>(Model.getBeansClass("Pays")).getList();
                System.out.println(list.size());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void btnValider(ActionEvent actionEvent) {
    }
}