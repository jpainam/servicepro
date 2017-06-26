package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.ProfilModel;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchFieldClassTool;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
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
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomTextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ProfilController extends Controller implements Initializable{
    public TableColumn abbreviationColumn;
    public TableColumn libelleColumn;
    public TableView profilTable;

    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnNouveau;
    public TableColumn fondamentalColumn;
    public TableColumn avanceColumn;
    public TableColumn expertColumn;
    public TableColumn listecompetenceColumn;
    public TableColumn connaissanceColumn;
    public TableColumn competenceColumn;
    private TableView.TableViewSelectionModel tableProfilModel;

    public ProfilController(){
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(FXMLView.PROFIL.getFXMLFile()));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnSettings();
        setButtonSettings();
        buildProfilTable();
        tableProfilModel = profilTable.getSelectionModel();
    }

    private void setColumnSettings() {

        VBox vbox = new VBox(new Label(fondamentalColumn.getText()));
        vbox.setRotate(-90);
        Group group = new Group(vbox);
        fondamentalColumn.setText("");
        fondamentalColumn.setGraphic(group);

        vbox = new VBox(new Label(avanceColumn.getText()));
        avanceColumn.setText("");
        vbox.setRotate(-90);
        group = new Group(vbox);
        avanceColumn.setGraphic(group);

        vbox = new VBox(new Label(connaissanceColumn.getText()));
        connaissanceColumn.setText("");
        vbox.setRotate(-90);
        group = new Group(vbox);
        connaissanceColumn.setGraphic(group);

        vbox = new VBox(new Label(competenceColumn.getText()));
        competenceColumn.setText("");
        vbox.setRotate(-90);
        group = new Group(vbox);
        competenceColumn.setGraphic(group);

        vbox = new VBox(new Label(expertColumn.getText()));
        expertColumn.setText("");
        vbox.setRotate(-90);
        group = new Group(vbox);
        expertColumn.setGraphic(group);
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
    }

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
    }

    public void editAction(ActionEvent actionEvent) {
        if(tableProfilModel.getSelectedItem() != null){

        }else{
            JFXDialog dialog = new JFXDialog();
            dialog.show();
            //JOptionPane.showMessageDialog(null, "Veuillez choisir un profil a editer");
        }
    }

    public void supprimerAction(ActionEvent actionEvent) {
        if(tableProfilModel.getSelectedItem() != null){

        }else{
            //JOptionPane.showMessageDialog(null, "Veuillez choisir un profil a supprimer");
            JFXDialog dialog = new JFXDialog();
            dialog.show();
        }
    }
}
