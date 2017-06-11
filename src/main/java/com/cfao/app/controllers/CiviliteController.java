package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.model.PersonneModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class CiviliteController implements Initializable{
    public Accordion accordion;
    public TitledPane informationPanel;
    public ComboBox searchCombo;

   
    public TableView<Personne> personneTable;
    public TableColumn<Personne, String> personneNom;
    public TableColumn<Personne, String> personneMatricule;
    public TextField txtNom;
    public TextField txtPrenom;
    public TextField txtMatricule;
    public TextField txtNationalite;
    public TextField txtSociete;
    public TextField txtGroupe;
    public TextField txtSection;

    TableView.TableViewSelectionModel<Personne> personneTableModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAccordionExpanded();
        fillPersonneTableWithData();
        personneTableModel = personneTable.getSelectionModel();
        personneTableModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addListenerToRow();
        });
    }

    private void setAccordionExpanded() {
        accordion.setExpandedPane(informationPanel);
        searchCombo.setEditable(true);
        TextFields.bindAutoCompletion(searchCombo.getEditor(), searchCombo.getItems());
        accordion.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) -> {
            // This value will change to false if there's (at least) one pane that is in "expanded" state, so we don't have to expand anything manually
            boolean expand = true;
            for(TitledPane pane: accordion.getPanes()) {
                if(pane.isExpanded()) {
                    expand = false;
                }
            }
        /* Here we already know whether we need to expand the old pane again */
            if((expand == true) && (oldPane != null)) {
                Platform.runLater( () -> {
                    accordion.setExpandedPane(oldPane);
                });
            }
        });
    }

    public void fillPersonneTableWithData(){
        personneNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        personneMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));

        PersonneModel personneModel = new PersonneModel();

        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(personneModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            personneTable.setItems(task.getValue());
        });

    }
    public void addListenerToRow(){
        if(personneTableModel.getSelectedItem() != null){
            Personne p = personneTableModel.getSelectedItem();
            txtMatricule.setText(p.getMatricule());
            txtNom.setText(p.getNom());
            txtPrenom.setText(p.getPrenom());
            txtNationalite.setText(p.getPays().toString());
            txtSociete.setText(p.getSociete().toString());
            txtGroupe.setText(p.getGroupe().toString());
            txtSection.setText(p.getSection().toString());
        }
    }
}
