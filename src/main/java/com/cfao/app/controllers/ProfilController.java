package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Modele;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.ProfilModel;
import com.cfao.app.util.Constante;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.SearchFieldClassTool;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.sun.org.apache.xpath.internal.operations.Bool;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.controlsfx.control.textfield.CustomTextField;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ProfilController implements Initializable {
    public TableColumn abbreviationColumn;
    public TableColumn libelleColumn;
    public TableView<Profil> profilTable;

    public JFXButton btnModifier;
    public JFXButton btnSupprimer;
    public JFXButton btnNouveau;
    public TableColumn<Profilcompetence, Boolean> fondamentalColumn;
    public TableColumn<Profilcompetence, Boolean> initialColumn;
    public TableColumn<Profilcompetence, Boolean> avanceColumn;
    public TableColumn<Profilcompetence, Boolean> expertColumn;
    public TableColumn<Profilcompetence, Competence> listecompetenceColumn;
    public TableColumn<Profilcompetence, Boolean> connaissanceColumn;
    public TableColumn<Profilcompetence, Boolean> competenceColumn;
    public TableView competenceTable;
    public HBox actionButtonBox;
    public StackPane rootPane1;
    public StackPane rootPane2;
    private TableView.TableViewSelectionModel<Profil> tableProfilModel;
    private SearchBox searchBox = new SearchBox();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnSettings();
        setButtonSettings();
        buildProfilTable();
        HBox.setHgrow(profilTable, Priority.ALWAYS);
        tableProfilModel = profilTable.getSelectionModel();
        tableProfilModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> fillCompetenceTable());

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        actionButtonBox.setMaxWidth(Double.MAX_VALUE);
        actionButtonBox.getChildren().setAll(searchBox, btnNouveau, btnModifier, btnSupprimer);

    }

    private void fillCompetenceTable() {
        if (tableProfilModel.getSelectedItem() != null) {
            Profil profil = tableProfilModel.getSelectedItem();
            //ProfilModel profilModel = new ProfilModel(Model.getBeansClass("Profil"));
            CompetenceModel competenceModel = new CompetenceModel();
            System.out.println(competenceModel.getCompetenceParProfil(profil));

            Task<ObservableList<Profilcompetence>> task = new Task<ObservableList<Profilcompetence>>() {
                @Override
                protected ObservableList<Profilcompetence> call() throws Exception {
                    return FXCollections.observableArrayList(competenceModel.getCompetenceParProfil(profil));
                }
            };
            new Thread(task).start();
            task.setOnSucceeded((WorkerStateEvent event) -> {
                if(task.getValue().isEmpty()){
                    competenceTable.getItems().clear();
                    return;
                }
                competenceTable.setItems(task.getValue());
                setColumnProperty(initialColumn, task.getValue(), ProfilModel.INITIAL);
                setColumnProperty(fondamentalColumn,  task.getValue(), ProfilModel.FONDAMENTAL);
                setColumnProperty(avanceColumn,  task.getValue(), ProfilModel.AVANCE);
                setColumnProperty(expertColumn,  task.getValue(), ProfilModel.EXPERT);
                listecompetenceColumn.setCellValueFactory(param -> {
                    Competence competence = param.getValue().getCompetence();
                    return new SimpleObjectProperty<>(competence);
                });
                competenceColumn.setCellValueFactory(param -> {
                    Competence competence = param.getValue().getCompetence();
                    if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                        return new SimpleBooleanProperty(true);
                    } else {
                        return new SimpleBooleanProperty(false);
                    }
                });
                competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
                connaissanceColumn.setCellValueFactory(param -> {
                    Competence competence = param.getValue().getCompetence();
                    if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                        return new SimpleBooleanProperty(true);
                    } else {
                        return new SimpleBooleanProperty(false);
                    }
                });
                connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
            });
        }
    }

    private void setColumnProperty(TableColumn<Profilcompetence, Boolean> tableColumn, ObservableList<Profilcompetence> list, int niveau) {
        tableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Profilcompetence, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Profilcompetence, Boolean> param) {
                Competence competence = param.getValue().getCompetence();
                if(competence.getNiveau().getIdniveaucompetence() == niveau){
                    return new SimpleBooleanProperty(true);
                }else{
                    return new SimpleBooleanProperty(false);
                }
            }
        });

    }

    /**
     * Transformer les text des colonnes en vertical et definir le contenu des colonnes
     */
    private void setColumnSettings() {
        setSingleColumnSetting(fondamentalColumn);
        setSingleColumnSetting(avanceColumn);
        setSingleColumnSetting(connaissanceColumn);
        setSingleColumnSetting(competenceColumn);
        setSingleColumnSetting(expertColumn);
        setSingleColumnSetting(initialColumn);
    }

    /**
     * Transformer une colonne passer en parametre en vertical, utiliser dans setColumnSettings
     * Ajouter une rotation pour que les texte soit vertical
     *
     * @param column
     */
    private void setSingleColumnSetting(TableColumn column) {
        Label label = new Label(column.getText());
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        VBox vbox = new VBox(label);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setMaxWidth(120);
        column.setText("");
        vbox.setRotate(-90);
        Group group = new Group(vbox);
        column.setGraphic(group);
    }

    /**
     * Definir les proprietes des button action
     */
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
    }

    /**
     * Construire la table view profil
     */
    private void buildProfilTable() {
        abbreviationColumn.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        ProfilModel profilModel = new ProfilModel();

        Task<ObservableList<Profil>> task = new Task<ObservableList<Profil>>() {
            @Override
            protected ObservableList<Profil> call() throws Exception {
                return FXCollections.observableArrayList(profilModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            FilteredList<Profil> filteredList = new FilteredList<Profil>(task.getValue(), p -> true);
            SortedList<Profil> sortedList = new SortedList<Profil>(filteredList);
            sortedList.comparatorProperty().bind(profilTable.comparatorProperty());
            profilTable.setItems(sortedList);
        });
    }

    public void nouveauAction(ActionEvent actionEvent) {
        try {
            rootPane2.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/views/profil/add.fxml")));
            btnModifier.setDisable(true);
            btnNouveau.setDisable(true);
            btnSupprimer.setDisable(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void supprimerAction(ActionEvent actionEvent) {
        if (tableProfilModel.getSelectedItem() != null) {

        } else {
            //JOptionPane.showMessageDialog(null, "Veuillez choisir un profil a supprimer");
            JFXDialog dialog = new JFXDialog();
            dialog.show();
        }
    }

    public void editProfil(ActionEvent actionEvent) {
    }
}
