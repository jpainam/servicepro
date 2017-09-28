package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Modele;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.DateTableCellFactory;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/19/2017.
 * Modele de formation
 */
public class ModeleController implements Initializable {
    public TableView<Modele> modeleTable;
    public TableColumn<Modele, String> libelleColumn;
    public Button btnAjouter;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;
    public TextField txtLibelle;
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> codeColumn;
    public TableColumn<Formation, String> titreColumn;
    public TableColumn<Formation, String> descriptionColumn;
    public TableColumn<Formation, String> etatFormation;
   public TableColumn<Formation, LocalDate> datedebutColumn;
   public TableColumn<Formation, LocalDate> datefinColumn;
    private boolean stateModification = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponent();
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
        titreColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        etatFormation.setCellValueFactory(param -> param.getValue().getEtatFormation().libelleProperty());
        codeColumn.setCellValueFactory(param -> param.getValue().codeformationProperty());
        descriptionColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        datedebutColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutColumn.setCellFactory(new DateTableCellFactory<Formation>());
        datefinColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinColumn.setCellFactory(new DateTableCellFactory<Formation>());
        modeleTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Modele> observable, Modele oldValue,
                                                                            Modele newValue) -> {
            if(newValue != null) {
                txtLibelle.setText(newValue.getLibelle());
                formationTable.itemsProperty().bind(newValue.formationsProperty());
            }
        });
    }

    private void initComponent() {
        ButtonUtil.add(btnAjouter);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.cancel(btnAnnuler);
        Task<ObservableList<Modele>> task = new Task<ObservableList<Modele>>() {
            @Override
            protected ObservableList<Modele> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Modele>("Modele").getList());
            }
        };
        new Thread(task).start();
        modeleTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> modeleTable.getSelectionModel().selectFirst());
        task.setOnFailed(event -> task.getException().printStackTrace());
    }

    public void ajouterAction(ActionEvent event) {
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ServiceproUtil.emptyFields(txtLibelle);
            ButtonUtil.save(btnAjouter);
        } else {
            Modele modele = new Modele();
            modele.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Modele>("Modele").save(modele);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    modeleTable.getItems().add(modele);
                    ServiceproUtil.notify("Ajout OK");
                }
            });
            ButtonUtil.add(btnAjouter);
            stateModification = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        Modele modele = modeleTable.getSelectionModel().getSelectedItem();
        if (modele == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir le modèle à modifier");
            return;
        }
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ButtonUtil.save(btnModifier);
        } else {
            modele.setLibelle(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Modele>("Modele").update(modele);
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
        Modele modele = modeleTable.getSelectionModel().getSelectedItem();
        if (modele == null) {
            AlertUtil.showSimpleAlert("Attention", "Veuillez d'abord choisir le modèle à supprimer");
            return;
        }
        boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Etes-vous sûr de vouloir supprimer " + modele);
        if (!goahead)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Model<Modele>("Modele").delete(modele);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            modeleTable.getItems().remove(modele);
            ServiceproUtil.notify("Suppression OK");
        });
        task.setOnFailed(event12 -> task.getException().printStackTrace());
    }

    public void annulerAction(ActionEvent event) {
        initComponent();
    }


}
