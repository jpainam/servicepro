package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.beans.Societe;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.SearchBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/21/2017.
 */
public class CompetencePersonneController extends AnchorPane implements Initializable {
    public TableColumn<PersonneCompetence, String> nomPersonneColumn;
    public TableColumn<PersonneCompetence, String> telephonePersonneColumn;
    public TableColumn<PersonneCompetence, Societe> societePersonneColumn;
    public TableColumn<PersonneCompetence, Boolean> encoursColumn;
    public TableColumn<PersonneCompetence, Boolean> acertifierColumn;
    public TableColumn<PersonneCompetence, Boolean> certifieColumn;
    public TableView<PersonneCompetence> personneTable;
    private Competence competence = null;
    public VBox vboxRecherchePersonne;
    private SearchBox searchBox = new SearchBox();

    public CompetencePersonneController(Competence competence) {
        this();
        this.competence = competence;
    }

    public CompetencePersonneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/competence/personne.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    public void initComponents() {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        vboxRecherchePersonne.getChildren().setAll(new Label("Personnes : "), searchBox);
        nomPersonneColumn.setCellValueFactory(param -> {
            return new SimpleStringProperty(param.getValue().getPersonne().getNom() + " " + param.getValue().getPersonne().getPrenom());
        });
        encoursColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifieColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        telephonePersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().telephoneProperty());
        societePersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().societe());

    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public void buildTable() {
        List<PersonneCompetence> personnes = competence.getPersonneCompetences();
        encoursColumn.setCellValueFactory(param -> param.getValue().encoursProperty());
        acertifierColumn.setCellValueFactory(param -> param.getValue().acertifierProperty());
        certifieColumn.setCellValueFactory(param -> param.getValue().certifieeProperty());
        personneTable.setItems(FXCollections.observableArrayList(personnes));
    }
}
