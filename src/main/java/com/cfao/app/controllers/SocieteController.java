package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.Toast;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public Button btnSupprimer;
    public Button btnModifier;
    public Button btnAjouter;
    public Button buttonCloseSearch;
    public Label searchBoxLabel;
    public TextField txtNom;
    public TextArea txtAdresse;
    private TableView.TableViewSelectionModel<Societe> societeTableModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       GlyphsDude.setIcon(btnSupprimer, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnAjouter, FontAwesomeIcon.SAVE);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.EDIT);
        societeColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        SocieteModel societeModel = new SocieteModel();
        societeTableModel = societeTable.getSelectionModel();

        /** Set Font awesome icon */


        FontAwesomeIconView iconView = new FontAwesomeIconView();
        iconView.getStyleClass().add("buttonSearchCloseIcon");
        buttonCloseSearch.setGraphic(iconView);
        iconView = new FontAwesomeIconView();
        iconView.getStyleClass().add("searchBoxLabelIcon");
        searchBoxLabel.setGraphic(iconView);

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
            txtNom.setText(societe.getNom());
            txtAdresse.setText(societe.getAdresse());
        }
    }

    public void addSociete(ActionEvent actionEvent) {
        String nom = txtNom.getText();
        String adresse = txtAdresse.getText();
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
