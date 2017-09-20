package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Pays;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.PersonneModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/20/2017.
 */
public class AccueilCiviliteBoxController implements Initializable {
    public TableView<Personne> dossierTable;
    public TableColumn<Personne, Pays> paysDossierColumn;
    public TableColumn<Personne, String> nomDossierColumn;
    public TableView<Personne> attenteTable;
    public TableColumn<Personne, Pays> paysAttenteColumn;
    public TableColumn<Personne, String> nomAttenteColumn;
    public TableColumn numeroDossier;
    public TableColumn numeroAttente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paysAttenteColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        paysDossierColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        nomAttenteColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom() + " "
                + param.getValue().getPrenom()));
        nomDossierColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom() + " "
                + param.getValue().getPrenom()));

        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(new PersonneModel().personnePassportNull());
            }
        };
        new Thread(task).start();
        dossierTable.itemsProperty().bind(task.valueProperty());

        Task<ObservableList<Personne>> task1 = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(new PersonneModel().personneCompetenceEncours());
            }
        };
        new Thread(task1).start();
        attenteTable.itemsProperty().bind(task1.valueProperty());

        numeroAttente.setCellFactory(col -> {
            TableCell<Competence, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        numeroDossier.setCellFactory(col -> {
            TableCell<Competence, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
    }
}
