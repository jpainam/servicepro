package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/19/2017.
 */
public class SituationMatrimonialeController implements Initializable {
    public TableView<SituationMatrimoniale> situationTable;
    public TableColumn<SituationMatrimoniale, String> libelleColumn;
    public Button btnAjouter;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;
    public TextField txtLibelle;
    public TableView<Personne> personneTable;
    public TableColumn<Personne, Pays> paysColumn;
    public TableColumn<Personne, String> nomColumn;
    public TableColumn<Personne, String> adresseColumn;
    public TableColumn<Personne, String> telephoneColumn;
    private boolean stateModification = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponent();
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
        nomColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        adresseColumn.setCellValueFactory(param -> param.getValue().adresseProperty());
        paysColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        telephoneColumn.setCellValueFactory(param -> param.getValue().telephoneProperty());
        situationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                txtLibelle.setText(newValue.getLibelle());
                personneTable.itemsProperty().bind(newValue.personnesProperty());
            }
        });
    }

    private void initComponent() {
        ButtonUtil.add(btnAjouter);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.cancel(btnAnnuler);
        Task<ObservableList<SituationMatrimoniale>> task = new Task<ObservableList<SituationMatrimoniale>>() {
            @Override
            protected ObservableList<SituationMatrimoniale> call() throws Exception {
                return FXCollections.observableArrayList(new Model<SituationMatrimoniale>("SituationMatrimoniale").getList());
            }
        };
        new Thread(task).start();
        situationTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> situationTable.getSelectionModel().selectFirst());
        task.setOnFailed(event -> task.getException().printStackTrace());
    }

    public void ajouterAction(ActionEvent event) {

        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ServiceproUtil.emptyFields(txtLibelle);
            ButtonUtil.save(btnAjouter);
        } else {
            SituationMatrimoniale situation = new SituationMatrimoniale();
            situation.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<SituationMatrimoniale>("SituationMatrimoniale").save(situation);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    situationTable.getItems().add(situation);
                    ServiceproUtil.notify("Ajout OK");
                }
            });
            ButtonUtil.add(btnAjouter);
            stateModification = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        SituationMatrimoniale situation = situationTable.getSelectionModel().getSelectedItem();
        if (situation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir la situation matrimoniale à modifier");
            return;
        }
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ButtonUtil.save(btnModifier);
        } else {
            situation.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<SituationMatrimoniale>("SituationMatrimoniale").update(situation);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ServiceproUtil.notify("Modification OK");
                }
            });
            ButtonUtil.edit(btnModifier);
            stateModification = false;
        }
    }

    public void supprimerAction(ActionEvent event) {
        SituationMatrimoniale situation = situationTable.getSelectionModel().getSelectedItem();
        if (situation == null) {
            AlertUtil.showSimpleAlert("Attention", "Veuillez d'abord choisir la situation matrimoniale à supprimer");
            return;
        }
        boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Etes-vous sûr de vouloir supprimer " + situation);
        if (!goahead)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Model<SituationMatrimoniale>("SituationMatrimoniale").delete(situation);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            situationTable.getItems().remove(situation);
            ServiceproUtil.notify("Suppression OK");
        });
        task.setOnFailed(event12 -> task.getException().printStackTrace());
    }

    public void annulerAction(ActionEvent event) {
        initComponent();
    }


}
