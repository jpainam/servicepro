package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.Toast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class SocieteController implements Initializable{
    public TableColumn societeColumn;
    public TableColumn adresseColumn;
    public TableView societeTable;
    public TextArea txtModifAdresse;
    public TextField txtModifNom;
    public TextField txtAjoutNom;
    public TextArea txtAjoutAdresse;
    private TableView.TableViewSelectionModel<Societe> societeTableModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societeColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        SocieteModel societeModel = new SocieteModel();
        societeTableModel = societeTable.getSelectionModel();

        societeTableModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addListenerToRow();
        });
        Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
            @Override
            protected ObservableList<Societe> call() throws Exception {
                return FXCollections.observableArrayList(societeModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            societeTable.setItems(task.getValue());
        });

    }

    private void addListenerToRow() {
        if(societeTableModel.getSelectedItem() != null){
            Societe societe = societeTableModel.getSelectedItem();
            txtModifNom.setText(societe.getNom());
            txtModifAdresse.setText(societe.getAdresse());
        }
    }

    public void addSociete(ActionEvent actionEvent) {
        String nom = txtAjoutNom.getText();
        String adresse = txtAjoutAdresse.getText();
        if(nom.isEmpty()){

        }
        if(adresse.isEmpty()){

        }
        if(!nom.isEmpty() && !adresse.isEmpty()){
            Societe societe = new Societe();
            societe.setNom(nom);
            societe.setAdresse(adresse);
            SocieteModel societeModel = new SocieteModel();
            societeModel.insert(societe);

        }
    }

    public void editSociete(ActionEvent actionEvent) {
    }
}
