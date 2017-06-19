package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.ProfilModel;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import org.controlsfx.control.textfield.CustomTextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ProfilController  implements Initializable{
    public TableColumn abbreviationColumn;
    public TableColumn libelleColumn;
    public TableView profilTable;

    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnNouveau;
    private TableView.TableViewSelectionModel tableProfilModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonSettings();
        buildProfilTable();
        tableProfilModel = profilTable.getSelectionModel();
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
