package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.PersonneModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/13/2017.
 */
public class DialogParticipantController extends AnchorPane implements Initializable {
    private Formation formation;
    public ListSelectionView<Personne> participants = new ListSelectionView<>();
    private FormationModel formationModel = new FormationModel();
    private PersonneModel personneModel = new PersonneModel();

    public DialogParticipantController(Formation formation) {
        this.formation = formation;
        getChildren().addAll(participants);
        initComponents();
    }

    public List<Personne> getParticipants() {
        return participants.getTargetItems();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initComponents() {
        Label disponibles = new Label("Disponibles");
        disponibles.setStyle("-fx-font-weight: bold;");
        participants.setSourceHeader(disponibles);
        Label selectionnee = new Label("Selectionnés");
        selectionnee.setStyle("-fx-font-weight: bold;");
        participants.setTargetHeader(selectionnee);

        participants.setSourceItems(FXCollections.observableArrayList(personneModel.getList()));
        participants.setTargetItems(FXCollections.observableArrayList(formation.getParticipants()));
    }
}
