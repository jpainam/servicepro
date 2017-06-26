package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchFieldClassTool;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/14/2017.
 */
public class CompetenceController extends Controller implements Initializable{
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public CustomTextField fieldSearch;
    public TableView competenceTable;
    public TableColumn numeroColumn;
    public TableColumn libelleColumn;
    public TableColumn connaissanceColumn;
    public TableColumn competenceColumn;
    public TableView prerequisTable;

    public CompetenceController(){
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(FXMLView.COMPETENCE.getFXMLFile()));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonSettings();
        buildCompetenceTable();
    }
    public void setButtonSettings() {

        GlyphsDude.setIcon(btnSupprimer, FontAwesomeIcon.TRASH_ALT);
        //GlyphsDude.setIcon(btnValider, FontAwesomeIcon.SAVE);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.EDIT);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE_TEXT_ALT);
        //GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.TIMES);
        /** Set Font awesome icon */
        FontAwesomeIconView iconView = new FontAwesomeIconView();
        /*iconView.getStyleClass().add("buttonSearchCloseIcon");
        buttonCloseSearch.setGraphic(iconView);*/
        iconView = new FontAwesomeIconView();
        iconView.getStyleClass().add("searchBoxLabelIcon");
        //searchBoxLabel.setGraphic(iconView);
        //Desactiver certain button
        btnNouveau.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private void buildCompetenceTable() {
        CompetenceModel competenceModel = new CompetenceModel();
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(competenceModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            FilteredList<Competence> filteredList = new FilteredList<Competence>(task.getValue(), p -> true);

            fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Competence competence) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Competence
                    String valueCompare = newValue.toLowerCase();
                    if (competence.getDescription().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else if (competence.getType().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Competence> sortedList = new SortedList<Competence>(filteredList);
            sortedList.comparatorProperty().bind(competenceTable.comparatorProperty());
            competenceTable.setItems(sortedList);
        });
    }

    public void nouveauAction(ActionEvent actionEvent) {
    }

    public void editSociete(ActionEvent actionEvent) {
    }

    public void supprimerAction(ActionEvent actionEvent) {
    }
}
