package com.cfao.app.controllers;

import com.cfao.app.beans.Agence;
import com.cfao.app.beans.Pays;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.beans.value.ObservableValue;
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
public class AgenceController implements Initializable {
    public TableView<Agence> agenceTable;
    public TableColumn<Agence, String> libelleColumn;
    public Button btnAjouter;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;
    public TextField txtLibelle;
    public TableView<Personne> personneTable;
    public TableColumn<Personne, Pays> paysColumn;
    public TableColumn<Personne, String> nomColumn;
    public TableColumn<Personne, String> societeColumn;
    public TableColumn<Personne, String> sectionColumn;
    public TableColumn<Personne, String> groupeColumn;
    private boolean stateModification = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponent();
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
        nomColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        sectionColumn.setCellValueFactory(param -> param.getValue().getSection().libelleProperty());
        paysColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        societeColumn.setCellValueFactory(param -> param.getValue().getSociete().nomProperty());
        groupeColumn.setCellValueFactory(param -> param.getValue().getGroupe().libelleProperty());
        agenceTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Agence> observable, Agence oldValue,
                                                                            Agence newValue) -> {
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
        Task<ObservableList<Agence>> task = new Task<ObservableList<Agence>>() {
            @Override
            protected ObservableList<Agence> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Agence>("Agence").getList());
            }
        };
        new Thread(task).start();
        agenceTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> agenceTable.getSelectionModel().selectFirst());
        task.setOnFailed(event -> task.getException().printStackTrace());
    }

    public void ajouterAction(ActionEvent event) {
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ServiceproUtil.emptyFields(txtLibelle);
            ButtonUtil.save(btnAjouter);
        } else {
            Agence agence = new Agence();
            agence.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Agence>("Agence").save(agence);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    agenceTable.getItems().add(agence);
                    ServiceproUtil.notify("Ajout OK");
                }
            });
            ButtonUtil.add(btnAjouter);
            stateModification = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        Agence agence = agenceTable.getSelectionModel().getSelectedItem();
        if (agence == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir l'agence à modifier");
            return;
        }
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ButtonUtil.save(btnModifier);
        } else {
            agence.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Agence>("Agence").update(agence);
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
        Agence agence = agenceTable.getSelectionModel().getSelectedItem();
        if (agence == null) {
            AlertUtil.showSimpleAlert("Attention", "Veuillez d'abord choisir l'agence à supprimer");
            return;
        }
        boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Etes-vous sûr de vouloir supprimer " + agence);
        if (!goahead)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Model<Agence>("Agence").delete(agence);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            agenceTable.getItems().remove(agence);
            ServiceproUtil.notify("Suppression OK");
        });
        task.setOnFailed(event12 -> task.getException().printStackTrace());
    }

    public void annulerAction(ActionEvent event) {
        initComponent();
    }


}
