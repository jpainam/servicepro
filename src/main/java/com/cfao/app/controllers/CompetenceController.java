package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Formation;

import com.cfao.app.beans.Profil;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/14/2017.
 */
public class CompetenceController implements Initializable{
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public TableView<Competence> competenceTable;
    public TableColumn<Competence, Void> numeroColumn;
    public TableColumn<Competence, String> libelleColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public Button btnAnnuler;
    public SearchBox searchBox = new SearchBox();
    public HBox researchBox;
    public StackPane competenceStackPane;
    public Tab competenceTabDetails;
    public Tab competenceTabPersonne;
    public TableView<Formation> formationTable;
    public StackPane formationStackPane;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableColumn<Formation, LocalDate> datedebutFormationColumn;
    public TableColumn<Formation, LocalDate> datefinFormationColumn;
    public TableColumn<Profil, String> libelleProfilColumn;
    public TableView<Profil> profilTable;
    public StackPane profilStackPane;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public TableColumn<Competence, String> niveauCompetenceColumn;

    CompetencePersonneController personneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        buildCompetenceTable();
    }
    private void initComponents(){
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.add(btnNouveau);
        ButtonUtil.cancel(btnAnnuler);
        ButtonUtil.print(btnPrint);
        ButtonUtil.next(btnNext);
        ButtonUtil.previous(btnPrevious);
        personneController = new CompetencePersonneController();
        competenceTabPersonne.setContent(personneController);
        ButtonUtil.detailsTab(competenceTabDetails);
        GlyphsDude.setIcon(competenceTabPersonne, FontAwesomeIcon.USERS);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Liste des compétences : "), searchBox);
        competenceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buildFormationTable(newValue);
            buildProfilTable(newValue);
            buildPersonneTable(newValue);
        });
        titreFormationColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        datedebutFormationColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutFormationColumn.setCellFactory(new DateTableCellFactory());
        datefinFormationColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinFormationColumn.setCellFactory(new DateTableCellFactory());

        //libelleProfilColumn.setCellValueFactory(param -> param.getValue().);
        niveauCompetenceColumn.setCellValueFactory(param -> param.getValue().getNiveau().libelleProperty());
    }

    private void buildCompetenceTable() {

        numeroColumn.setCellFactory(col -> {
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

        libelleColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());

        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        CompetenceModel competenceModel = new CompetenceModel();
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(competenceModel.getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(competenceStackPane, task);
        new Thread(task).start();

        /*task.setOnSucceeded(event -> {
            FilteredList<Competence> filteredList = new FilteredList<Competence>(task.getValue(), p -> true);

            searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Competence competence) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Competence
                    String valueCompare = newValue.toLowerCase();
                    if (competence.getDescription().toLowerCase().contains(valueCompare)) {
                        return true;
                    } else if (competence.getType().toLowerCase().contains(valueCompare)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Competence> sortedList = new SortedList<Competence>(filteredList);
            sortedList.comparatorProperty().bind(competenceTable.comparatorProperty());
            competenceTable.setItems(sortedList);
        });*/
    }

    public void buildFormationTable(Competence competence){
        if(competence == null)
            return;
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getFormationByCompetence(competence));
            }
        };
        new ProgressIndicatorUtil(formationStackPane, task);
        formationTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
    public void buildProfilTable(Competence competence){
        /*if(competence == null)
            return;
        System.out.println(competence);

        Task<ObservableList<Profil>> task = new Task<ObservableList<ProfilCompetence>>() {
            @Override
            protected ObservableList<ProfilCompetence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getProfilByCompetence(competence));
            }
        };
        profilTable.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(profilStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println(task.getValue());
            }
        });
        */


    }
    private  void buildPersonneTable(Competence competence){
        personneController.setCompetence(competence);
        personneController.buildTable();
    }
    public void clickNouveau(ActionEvent actionEvent) {
    }

    public void clickModifier(ActionEvent actionEvent) {
    }

    public void clickSupprimer(ActionEvent actionEvent) {
    }

    public void clickAnnuler(ActionEvent actionEvent) {
    }

    public void previousAction(ActionEvent event) {
        if(competenceTable.getSelectionModel().getSelectedIndex() > 0){
            competenceTable.getSelectionModel().selectPrevious();
        }
    }

    public void nextAction(ActionEvent event) {
        if(competenceTable.getSelectionModel().getSelectedIndex() < competenceTable.getItems().size()){
            competenceTable.getSelectionModel().selectNext();
        }
    }

    public void printAction(ActionEvent event) {

    }
}
