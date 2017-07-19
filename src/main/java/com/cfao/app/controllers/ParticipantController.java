package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/13/2017.
 */
public class ParticipantController extends AnchorPane implements Initializable {
    private Formation formation = null;
    private FormationModel formationModel = new FormationModel();
    private PersonneModel personneModel = new PersonneModel();

    public TableView<Personne> participantTable;
    public TableColumn<Personne, String> matriculeParticipantColumn;
    public TableColumn<Personne, String> nomParticipantColumn;
    public TableColumn<Personne, String> prenomParticipantColumn;
    public Button btnPreviousParticipant;
    public Button btnNextParticipant;
    public Button btnPrintParticipant;
    public Button btnModifierParticipant;
    public Button btnAnnulerParticipant;
    public Button personneToParticipant;
    public Button personneToParticipantAll;
    public Button participantToPersonne;
    public Button participantToPersonneAll;
    public HBox hboxSearchParticipant;
    public HBox hboxSearchPersonne;
    public TableView<Personne> personneTable;

    public TableColumn<Personne, String> matriculePersonneColumn;
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, String> prenomPersonneColumn;
    public TableColumn<Personne, String> societeParticipantColumn;
    public TableColumn<Personne, FontAwesomeIconView> potentielPersonneColumn;

    public int stateBtnModifierParticipant = 0;
    // search personnes
    SearchBox searchBox2 = new SearchBox();
    // search participant
    SearchBox searchBox3 = new SearchBox();

    public ParticipantController() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/participant.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        participantTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personneTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox.setHgrow(searchBox2, Priority.ALWAYS);
        hboxSearchPersonne.getChildren().addAll(new Label("Personnes : "), searchBox2);
        HBox.setHgrow(searchBox3, Priority.ALWAYS);
        hboxSearchParticipant.getChildren().addAll(new Label("Participants : "), searchBox3);
    }
    public void setFormation(Formation formation){
        this.formation = formation;
    }
    public void buildTable(){
        if(formation == null)
            return;
        // Participant
        matriculeParticipantColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        nomParticipantColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomParticipantColumn.setCellValueFactory(param -> param.getValue().prenomProperty());
        societeParticipantColumn.setCellValueFactory(param -> param.getValue().getSociete().nomProperty());
        // Personne
        potentielPersonneColumn.setCellValueFactory(param -> {
            Personne personne = param.getValue();
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.USER);
            if(!personne.getProfils().isEmpty()){
                if (personne.getProfils().get(0).getCompetences().contains(formation.getCompetences())) {
                    iconView.setFill(Color.FORESTGREEN);
                }
            }
            return new SimpleObjectProperty<>(iconView);
        });
        matriculePersonneColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        nomPersonneColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomPersonneColumn.setCellValueFactory(param -> param.getValue().prenomProperty());
        if (formation.getParticipants().isEmpty()) {
            personneTable.setItems(FXCollections.observableArrayList(personneModel.getList()));
        } else {
            personneTable.setItems(FXCollections.observableArrayList(formationModel.getNonParticipants(formation)));
        }
        participantTable.setItems(FXCollections.observableArrayList(formation.getParticipants()));
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);
    }
    private void initComponents() {
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);
        ButtonUtil.add(btnModifierParticipant);
        ButtonUtil.cancel(btnAnnulerParticipant);
        ButtonUtil.angle_left(participantToPersonne);
        ButtonUtil.angle_right(personneToParticipant);
        ButtonUtil.angle_double_left(participantToPersonneAll);
        ButtonUtil.angle_double_right(personneToParticipantAll);
        ButtonUtil.next(btnNextParticipant);
        ButtonUtil.previous(btnPreviousParticipant);
        ButtonUtil.print(btnPrintParticipant);

    }
    public void participantDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1 && stateBtnModifierParticipant == 1) {
            this.move(participantTable, personneTable);
            participantTable.getSelectionModel().clearSelection();
        }
    }

    public void personneDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1 && stateBtnModifierParticipant == 1) {
            this.move(personneTable, participantTable);
            personneTable.getSelectionModel().clearSelection();
        }
    }
    public void personneToParticipantAction(ActionEvent actionEvent) {
        if (personneTable.getSelectionModel().getSelectedItem() != null) {
            this.move(personneTable, participantTable);
            personneTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la personne à ajouter");
        }
    }

    public void personneToParticipantAllAction(ActionEvent actionEvent) {
        this.move(personneTable, participantTable, new ArrayList(this.personneTable.getItems()));
        personneTable.getSelectionModel().clearSelection();
    }

    public void participantToPersonneAction(ActionEvent actionEvent) {
        if (participantTable.getSelectionModel().getSelectedItem() != null) {
            this.move(participantTable, personneTable);
            participantTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le particpant à exclure de la liste");
        }
    }

    public void participantToPersonneAllAction(ActionEvent actionEvent) {
        this.move(participantTable, personneTable, new ArrayList<>(this.participantTable.getItems()));
        participantTable.getSelectionModel().clearSelection();
    }
    private void move(TableView<Personne> viewA, TableView<Personne> viewB) {
        List<Personne> selectedItems = new ArrayList(viewA.getSelectionModel().getSelectedItems());
        this.move(viewA, viewB, selectedItems);
    }

    private void move(TableView<Personne> viewA, TableView<Personne> viewB, List<Personne> items) {
        Iterator<Personne> var4 = items.iterator();

        while (var4.hasNext()) {
            Personne item = var4.next();
            viewA.getItems().remove(item);
            viewB.getItems().add(item);
        }

    }
    public void modifierParticipant(ActionEvent actionEvent) {
        if (formation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation");
            return;
        }
        if (stateBtnModifierParticipant == 0) {
            ServiceproUtil.setDisable(false, participantToPersonneAll, participantToPersonne, personneToParticipantAll, personneToParticipant);
            stateBtnModifierParticipant = 1;
            btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
        } else {
            btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
            ServiceproUtil.setDisable(true, participantToPersonneAll, participantToPersonne, personneToParticipantAll, personneToParticipant);
            formation.setParticipants(participantTable.getItems());
            if (formationModel.update(formation)) {
                ServiceproUtil.notify("Participants ajoutés avec succès");
            } else {
                ServiceproUtil.notify("Une erreur est survenue");
            }
            stateBtnModifierParticipant = 0;
        }
    }
    public void annulerParticipant(ActionEvent actionEvent) {
        participantTable.getSelectionModel().clearSelection();
        personneTable.getSelectionModel().clearSelection();
        btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        stateBtnModifierParticipant = 0;
        buildTable();
    }
}
