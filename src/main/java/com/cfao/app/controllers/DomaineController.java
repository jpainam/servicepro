package com.cfao.app.controllers;

import com.cfao.app.beans.Domaine;
import com.cfao.app.beans.Pays;
import com.cfao.app.beans.Personnel;
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
public class DomaineController implements Initializable {
    public TableView<Domaine> domaineTable;
    public TableColumn<Domaine, String> libelleColumn;
    public Button btnAjouter;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;
    public TextField txtLibelle;
    public TableView<Personnel> personnelTable;
    public TableColumn<Personnel, Pays> paysColumn;
    public TableColumn<Personnel, String> nomColumn;
    public TableColumn<Personnel, String> adresseColumn;
    public TableColumn<Personnel, String> telephoneColumn;
    private boolean stateModification = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponent();
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
        nomColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        adresseColumn.setCellValueFactory(param -> param.getValue().adresseProperty());
        paysColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        telephoneColumn.setCellValueFactory(param -> param.getValue().telephoneProperty());
        domaineTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> personnelTable.itemsProperty().bind(newValue.personnelsProperty()));
    }

    private void initComponent() {
        ButtonUtil.add(btnAjouter);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.cancel(btnAnnuler);
        Task<ObservableList<Domaine>> task = new Task<ObservableList<Domaine>>() {
            @Override
            protected ObservableList<Domaine> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Domaine>("Domaine").getList());
            }
        };
        new Thread(task).start();
        domaineTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> domaineTable.getSelectionModel().selectFirst());
        task.setOnFailed(event -> task.getException().printStackTrace());
    }

    public void ajouterAction(ActionEvent event) {

        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ServiceproUtil.emptyFields(txtLibelle);
            ButtonUtil.save(btnAjouter);
        } else {
            Domaine domaine = new Domaine();
            domaine.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Domaine>("Domaine").save(domaine);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    domaineTable.getItems().add(domaine);
                    ServiceproUtil.notify("Ajout OK");
                }
            });
            ButtonUtil.add(btnAjouter);
            stateModification = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        Domaine domaine = domaineTable.getSelectionModel().getSelectedItem();
        if (domaine == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir le domaine à modifier");
            return;
        }
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ButtonUtil.save(btnModifier);
        } else {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Domaine>("Domaine").update(domaine);
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
        Domaine domaine = domaineTable.getSelectionModel().getSelectedItem();
        if (domaine == null) {
            AlertUtil.showSimpleAlert("Attention", "Veuillez d'abord choisir le domaine à supprimer");
            return;
        }
        boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Etes-vous sûr de vouloir supprimer " + domaine);
        if (!goahead)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Model<Domaine>("Domaine").delete(domaine);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            domaineTable.getItems().remove(domaine);
            ServiceproUtil.notify("Suppression OK");
        });
        task.setOnFailed(event12 -> task.getException().printStackTrace());
    }

    public void annulerAction(ActionEvent event) {
        initComponent();
    }


}
