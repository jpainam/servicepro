package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.Constante;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.SearchFieldClassTool;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

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
    public TableView prerequisTable;
    public Button btnAnnuler;
    public SearchBox searchBox = new SearchBox();
    public HBox researchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Liste des compétences : "), searchBox);
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
        Model<Competence> competenceModel = new Model(Model.getBeanPath("Competence"));
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(competenceModel.getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
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
        });
    }

    public void clickNouveau(ActionEvent actionEvent) {
    }

    public void clickModifier(ActionEvent actionEvent) {
    }

    public void clickSupprimer(ActionEvent actionEvent) {
    }

    public void clickAnnuler(ActionEvent actionEvent) {
    }
}
