package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.Constante;
import com.cfao.app.util.SearchFieldClassTool;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.CookieStore;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class SocieteController implements Initializable {
    public TableColumn societeColumn;
    public TableColumn adresseColumn;
    public TableView societeTable;
    public Button btnSupprimer;
    public Button btnModifier;
    public Button btnValider;
    public Button btnAnnuler;

    public Label searchBoxLabel;
    public TextField txtNom;
    public TextArea txtAdresse;
    public CustomTextField fieldSearch;
    public Button btnNouveau;
    public HBox notifContent;
    private TableView.TableViewSelectionModel<Societe> societeTableModel;


    public int activeAction = Constante.ADD_BUTTON;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societeColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        societeTableModel = societeTable.getSelectionModel();
        societeTableModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addListenerToRow();
        });
        setButtonSettings();
        buildSocieteTable();


    }

    /**
     * Definir les proprietes des button
     */
    public void setButtonSettings() {
        GlyphsDude.setIcon(btnSupprimer, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnValider, FontAwesomeIcon.SAVE);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.EDIT);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE_TEXT);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.TIMES);
        /** Set Font awesome icon */
        FontAwesomeIconView iconView = new FontAwesomeIconView();
        /*iconView.getStyleClass().add("buttonSearchCloseIcon");
        buttonCloseSearch.setGraphic(iconView);*/
        iconView = new FontAwesomeIconView();
        iconView.getStyleClass().add("searchBoxLabelIcon");
        searchBoxLabel.setGraphic(iconView);
        //Desactiver certain button
        btnNouveau.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    /**
     * Construction de la table View des societes avec les capacites de recherche
     */
    private void buildSocieteTable() {
        SocieteModel societeModel = new SocieteModel();
        Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
            @Override
            protected ObservableList<Societe> call() throws Exception {
                return FXCollections.observableArrayList(societeModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            FilteredList<Societe> filteredList = new FilteredList<Societe>(task.getValue(), p -> true);
            fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(societe -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Societe
                    String valueCompare = newValue.toLowerCase();
                    if (societe.getNom().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else if (societe.getAdresse() != null && societe.getAdresse().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Societe> sortedList = new SortedList<Societe>(filteredList);
            sortedList.comparatorProperty().bind(societeTable.comparatorProperty());
            societeTable.setItems(sortedList);
        });
    }

    private void addListenerToRow() {
        if (societeTableModel.getSelectedItem() != null) {
            txtNom.setEditable(false);
            txtAdresse.setEditable(false);
            Societe societe = societeTableModel.getSelectedItem();
            txtNom.setText(societe.getNom());
            txtAdresse.setText(societe.getAdresse());
            btnSupprimer.setDisable(false);
            btnModifier.setDisable(false);
            btnNouveau.setDisable(false);
            btnValider.setDisable(true);
            btnAnnuler.setDisable(true);
        } else {
            btnSupprimer.setDisable(true);
            btnModifier.setDisable(true);
            txtNom.setText("");
            txtAdresse.setText("");
            btnValider.setDisable(false);
            btnAnnuler.setDisable(false);
        }
    }


    public void editSociete(ActionEvent actionEvent) {
        this.activeAction = Constante.EDIT_BUTTON;

        btnModifier.setDisable(true);
        if (societeTableModel.getSelectedItem() != null) {
            btnValider.setDisable(false);
            btnAnnuler.setDisable(false);
            txtNom.setEditable(true);
            txtAdresse.setEditable(true);
        }
    }

    public void annulerAction(ActionEvent actionEvent) {
        btnValider.setDisable(true);
        btnAnnuler.setDisable(true);
        txtNom.setEditable(false);
        txtAdresse.setEditable(false);
        btnModifier.setDisable(true);
        btnNouveau.setDisable(false);
        txtAdresse.setText("");
        txtNom.setText("");
        btnSupprimer.setDisable(true);
    }

    public void nouveauAction(ActionEvent actionEvent) {
        this.activeAction = Constante.ADD_BUTTON;
        txtAdresse.setText("");
        txtNom.setText("");
        txtAdresse.setEditable(true);
        txtNom.setEditable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        btnNouveau.setDisable(true);
        btnValider.setDisable(false);
        btnAnnuler.setDisable(false);
    }

    public void validerAction(ActionEvent actionEvent) {
        String nom = txtNom.getText();
        String adresse = txtAdresse.getText();
        if (nom.isEmpty()) {
            txtNom.getStyleClass().add("obligatoire");
        }
        if (adresse.isEmpty()) {
            txtAdresse.getStyleClass().add("obligatoire");
        }
        if (!nom.isEmpty() && !adresse.isEmpty()) {
            Societe societe;
            SocieteModel societeModel = new SocieteModel();
            boolean success = false;
            String txtNotif;
            if(this.activeAction == Constante.ADD_BUTTON) {
                societe = new Societe();
                societe.setNom(nom);
                societe.setAdresse(adresse);
                success = societeModel.insert(societe);
                txtNotif = "Société ajoutée avec succès";
            }else{
                societe = societeTableModel.getSelectedItem();
                societe.setNom(nom);
                societe.setAdresse(adresse);
                success = societeModel.update(societe);
                txtNotif = "Société modifiée avec succès";
            }
            if (success) {
                buildSocieteTable();
                txtNom.setText("");
                txtAdresse.setText("");
            }else{
                txtNotif = "L'opération en cours à échouée";
            }
            NotificationPane notif = StageManager.getNotificationPane();
            notif.setText(txtNotif);
            notif.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished( event -> notif.hide());
            delay.play();
        }
    }

    public void supprimerAction(ActionEvent actionEvent) {
        if (societeTableModel.getSelectedItem() != null) {
            Societe societe = societeTableModel.getSelectedItem();
            SocieteModel societeModel = new SocieteModel();
            if (societeModel.delete(societe)) {
                buildSocieteTable();
                NotificationPane notif = StageManager.getNotificationPane();
                notif.setText("Suppression effectuée avec succès");
                notif.show();
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished( event -> notif.hide());
                delay.play();
            }
        }
    }
}
