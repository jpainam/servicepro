package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.LieuFormation;
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
public class LieuController implements Initializable {
    public TableView<LieuFormation> lieuTable;
    public TableColumn<LieuFormation, String> libelleColumn;
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
        libelleColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        titreColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        etatFormation.setCellValueFactory(param -> param.getValue().getEtatFormation().libelleProperty());
        codeColumn.setCellValueFactory(param -> param.getValue().codeformationProperty());
        descriptionColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        datedebutColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutColumn.setCellFactory(new DateTableCellFactory<Formation>());
        datefinColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinColumn.setCellFactory(new DateTableCellFactory<Formation>());
        lieuTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends LieuFormation> observable, LieuFormation oldValue,
                                                                          LieuFormation newValue) -> {
            if(newValue != null) {
                txtLibelle.setText(newValue.getDescription());
                formationTable.itemsProperty().bind(newValue.formationsProperty());
            }
        });
    }

    private void initComponent() {
        ButtonUtil.add(btnAjouter);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.cancel(btnAnnuler);
        Task<ObservableList<LieuFormation>> task = new Task<ObservableList<LieuFormation>>() {
            @Override
            protected ObservableList<LieuFormation> call() throws Exception {
                return FXCollections.observableArrayList(new Model<LieuFormation>("LieuFormation").getList());
            }
        };
        new Thread(task).start();
        lieuTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(event -> lieuTable.getSelectionModel().selectFirst());
        task.setOnFailed(event -> task.getException().printStackTrace());
    }

    public void ajouterAction(ActionEvent event) {
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ServiceproUtil.emptyFields(txtLibelle);
            ButtonUtil.save(btnAjouter);
        } else {
            LieuFormation lieu = new LieuFormation();
            lieu.setDescription(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<LieuFormation>("LieuFormation").save(lieu);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    lieuTable.getItems().add(lieu);
                    ServiceproUtil.notify("Ajout OK");
                }
            });
            ButtonUtil.add(btnAjouter);
            stateModification = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        LieuFormation lieu = lieuTable.getSelectionModel().getSelectedItem();
        if (lieu == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir le lieu à modifier");
            return;
        }
        if (!stateModification) {
            stateModification = true;
            ServiceproUtil.setEditable(true, txtLibelle);
            ButtonUtil.save(btnModifier);
        } else {
            lieu.setDescription(txtLibelle.getText());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<LieuFormation>("LieuFormation").update(lieu);
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
        LieuFormation lieu = lieuTable.getSelectionModel().getSelectedItem();
        if (lieu == null) {
            AlertUtil.showSimpleAlert("Attention", "Veuillez d'abord choisir le lieu à supprimer");
            return;
        }
        boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Etes-vous sûr de vouloir supprimer " + lieu);
        if (!goahead)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Model<LieuFormation>("LieuFormation").delete(lieu);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            lieuTable.getItems().remove(lieu);
            ServiceproUtil.notify("Suppression OK");
        });
        task.setOnFailed(event12 -> task.getException().printStackTrace());
    }

    public void annulerAction(ActionEvent event) {
        initComponent();
    }


}
