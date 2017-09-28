package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.FormationPersonne;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Created by JP on 7/13/2017.
 */
public class FormationPersonneController extends AnchorPane implements Initializable {
    private Formation formation = null;
    private FormationModel formationModel = new FormationModel();
    private PersonneModel personneModel = new PersonneModel();


    public Button btnPreviousParticipant;
    public Button btnNextParticipant;
    public Button btnPrintParticipant;
    public Button btnModifierParticipant;
    public Button btnAnnulerParticipant;
    public Button personneToParticipant;
    public Button personneToParticipantAll;
    public Button participantToPersonne;
    public Button participantToPersonneAll;


    public VBox vboxSearchPersonne;
    public TableView<Personne> personneTable;
    public StackPane personneStackPane;


    public TableColumn<Personne, String> matriculePersonneColumn;
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, String> prenomPersonneColumn;
    public TableColumn<Personne, Label> potentielPersonneColumn;
    private ObjectProperty<Predicate<Personne>> personneFilter = new SimpleObjectProperty<>();

    /** Propriete obtenue par injection dans FormationController */
    private TableView<FormationPersonne> participantTable;


    public int stateBtnModifierParticipant = 0;
    // search personnes
    SearchBox searchBox = new SearchBox();


    private ObservableList<Personne> personneData = FXCollections.observableArrayList();

    public FormationPersonneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/personne.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }
    public void setParticipantTable(TableView pt){
        this.participantTable = pt;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        personneTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        vboxSearchPersonne.getChildren().addAll(new Label("Personnes : (# pour potentiels)"), searchBox);

        personneFilter.bind(Bindings.createObjectBinding(() -> (Predicate<Personne>) personne -> {
                    if (comparePersonne(personne, searchBox.getText())) {
                        return true;
                    }
                    return false;
                }, searchBox.textProperty())
        );
    }

    private boolean comparePersonne(Personne personne, String newValue) {
        newValue = newValue.toLowerCase();
        if (personne.getNom().toLowerCase().contains(newValue) || personne.getPrenom().contains(newValue)) {
            return true;
        }
        if (personne.getSociete() != null && personne.getSociete().toString().toLowerCase().contains(newValue)) {
            return true;
        }
        if (personne.getSection() != null && personne.getSection().toString().toLowerCase().contains(newValue)) {
            return true;
        }
        if (newValue.contains("#")) {
            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                if (formation.getCompetences().contains(pc.getCompetence()) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void buildTable() {
        personneData.clear();
        if (formation == null)
            return;
        potentielPersonneColumn.setCellValueFactory(param -> {
            Personne personne = param.getValue();
            Label label = new Label("N");
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.USER);
            formation.getCompetences();
            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                if (formation.getCompetences().contains(pc.getCompetence()) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                    iconView.setFill(Color.FORESTGREEN);
                    label.setText("P");
                    break;
                }
            }
            label.setGraphic(iconView);
            return new SimpleObjectProperty<>(label);
        });

        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                if (formation.getFormationPersonnes().isEmpty()) {
                    return FXCollections.observableArrayList(personneModel.getList());
                } else {
                    return FXCollections.observableArrayList(formationModel.getNonParticipants(formation));
                }
            }
        };
        //formationTable.itemsProperty().bind(task.valueProperty());
        ProgressIndicatorUtil.show(personneStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                personneData.addAll(task.getValue());
                FilteredList<Personne> filteredList = new FilteredList<Personne>(personneData, p -> true);
                SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);
                sortedList.comparatorProperty().bind(personneTable.comparatorProperty());
                personneTable.setItems(sortedList);
                filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> personneFilter.get(), personneFilter));
            }
        });
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);
    }

    private void initComponents() {
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);

        ButtonUtil.add(btnModifierParticipant);
        ButtonUtil.cancel(btnAnnulerParticipant);
        ButtonUtil.angle_up(participantToPersonne);
        ButtonUtil.angle_down(personneToParticipant);
        ButtonUtil.angle_double_up(participantToPersonneAll);
        ButtonUtil.angle_double_down(personneToParticipantAll);
        ButtonUtil.next(btnNextParticipant);
        ButtonUtil.previous(btnPreviousParticipant);
        ButtonUtil.print(btnPrintParticipant);


        matriculePersonneColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        nomPersonneColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomPersonneColumn.setCellValueFactory(param -> param.getValue().prenomProperty());


    }

    public void personneDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1 && stateBtnModifierParticipant == 1) {
            this.move(personneTable, participantTable);
            //formationTable.getSelectionModel().clearSelection();
        }
    }

    public void personneToParticipantAction(ActionEvent actionEvent) {
        if (personneTable.getSelectionModel().getSelectedItem() != null) {
            this.move(personneTable, participantTable);
            //formationTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la personne à ajouter");
        }
    }

    public void personneToParticipantAllAction(ActionEvent actionEvent) {
        this.move(personneTable, participantTable, new ArrayList(this.personneTable.getItems()));
        //formationTable.getSelectionModel().clearSelection();
    }

    public void participantToPersonneAction(ActionEvent actionEvent) {
        if (participantTable.getSelectionModel().getSelectedItem() != null) {
            List<FormationPersonne> selectedItems = new ArrayList<>(participantTable.getSelectionModel().getSelectedItems());
            Iterator<FormationPersonne> iterator = selectedItems.iterator();
            while (iterator.hasNext()) {
                FormationPersonne fp = iterator.next();
                participantTable.getItems().remove(fp);
                //formationTable.getItems().add(fp.getPersonne());
                personneData.add(fp.getPersonne());
            }
            //participantTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le particpant à exclure de la liste");
        }
    }

    public void participantToPersonneAllAction(ActionEvent actionEvent) {
        List<FormationPersonne> items = new ArrayList<>(this.participantTable.getItems());
        Iterator<FormationPersonne> iterator = items.iterator();
        while (iterator.hasNext()) {
            FormationPersonne fp = iterator.next();
            participantTable.getItems().remove(fp);
            //formationTable.getItems().add(fp.getPersonne());
            personneData.add(fp.getPersonne());
        }
        //participantTable.getSelectionModel().clearSelection();
    }

    private void move(TableView<Personne> viewA, TableView<FormationPersonne> viewB) {
        List<Personne> selectedItems = new ArrayList(viewA.getSelectionModel().getSelectedItems());
        this.move(viewA, viewB, selectedItems);
    }

    private void move(TableView<Personne> viewA, TableView<FormationPersonne> viewB, List<Personne> items) {
        Iterator<Personne> var4 = items.iterator();
        while (var4.hasNext()) {
            Personne item = var4.next();
            FormationPersonne fp = new FormationPersonne();
            fp.setPersonne(item);
            fp.setFormation(formation);
            //viewA.getItems().remove(item);
            personneData.remove(item);
            viewB.getItems().add(fp);
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
            formation.getPersonnes().clear();
            for (FormationPersonne fp : participantTable.getItems()) {
                formation.getPersonnes().add(fp.getPersonne());
            }
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
    public ObservableList<Personne> getPersonneData(){
        return personneData;
    }
}
