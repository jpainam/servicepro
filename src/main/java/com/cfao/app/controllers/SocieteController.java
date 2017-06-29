package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.*;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.CookieStore;
import java.net.URL;
import java.util.Optional;
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
    public SearchBox searchBox = new SearchBox();
    public TextField txtNom;
    public TextArea txtAdresse;
    public Button btnNouveau;
    public HBox notifContent;
    public HBox researchBox;
    public TableColumn telephoneColumn;
    public TextField txtCode;
    public TextField txtTelephone;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtFax;
    private TableView.TableViewSelectionModel<Societe> societeTableModel;


    public int activeAction = Constante.ADD_BUTTON;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Societe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Societe, String> cellValue) {
                return cellValue.getValue().nomProperty();
            }
        });
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
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
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setSpacing(10);
        researchBox.getChildren().setAll(new Label("Sociétés : "), searchBox);
        //Desactiver certain button
        btnValider.setDisable(true);
        btnAnnuler.setDisable(true);
        txtCode.setEditable(false);
        txtContact.setEditable(false);
        txtFax.setEditable(false);
        txtEmail.setEditable(false);
        txtTelephone.setEditable(false);
        txtAdresse.setEditable(false);
        txtNom.setEditable(false);
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
            searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(societe -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Societe
                    String valueCompare = newValue.toLowerCase();
                    if (societe.getNom().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(searchBox, false);
                        return true;
                    } else if (societe.getAdresse() != null && societe.getAdresse().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(searchBox, false);
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
            Societe societe = societeTableModel.getSelectedItem();
            txtNom.setText(societe.getNom());
            txtAdresse.setText(societe.getAdresse());
            txtCode.setText(societe.getCode());
            txtContact.setText(societe.getContact());
            txtEmail.setText(societe.getEmail());
            txtFax.setText(societe.getFax());
            txtTelephone.setText(societe.getTelephone());

        }
    }


    public void editSociete(ActionEvent actionEvent) {
        this.activeAction = Constante.EDIT_BUTTON;
        if (societeTableModel.getSelectedItem() != null) {
            btnValider.setDisable(false);
            btnAnnuler.setDisable(false);
            btnSupprimer.setDisable(true);
            btnNouveau.setDisable(true);
            enableTextField(true);
        } else {
            enableTextField(false);
        }
    }

    public void annulerAction(ActionEvent actionEvent) {
        btnValider.setDisable(true);
        btnAnnuler.setDisable(true);
        enableTextField(false);
        emptyTextField();
    }

    private void enableTextField(boolean enabled) {
        txtNom.setEditable(enabled);
        txtAdresse.setEditable(enabled);
        txtContact.setEditable(enabled);
        txtFax.setEditable(enabled);
        txtEmail.setEditable(enabled);
        txtTelephone.setEditable(enabled);
        txtCode.setEditable(enabled);
    }

    private void disableButton(boolean disabled) {

    }

    private void emptyTextField() {
        txtNom.setText("");
        txtAdresse.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
        txtFax.setText("");
        txtContact.setText("");
        txtCode.setText("");
    }

    public void nouveauAction(ActionEvent actionEvent) {
        this.activeAction = Constante.ADD_BUTTON;
        emptyTextField();
        enableTextField(true);

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        btnValider.setDisable(false);
        btnAnnuler.setDisable(false);
    }

    public void validerAction(ActionEvent actionEvent) {
        String nom = txtNom.getText();
        String adresse = txtAdresse.getText();
        String code = txtCode.getText();
        String contact = txtContact.getText();
        String telephone = txtTelephone.getText();
        String email = txtEmail.getText();
        String fax = txtFax.getText();
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
            String txtNotif = "Aucune opération effectuée";
            if (this.activeAction == Constante.ADD_BUTTON) {
                societe = new Societe();
                societe.setNom(nom);
                societe.setAdresse(adresse);
                societe.setTelephone(telephone);
                societe.setCode(code);
                societe.setContact(contact);
                societe.setEmail(email);
                societe.setFax(fax);
                success = societeModel.insert(societe);
                txtNotif = "Enregistrement OK";
            } else if (this.activeAction == Constante.EDIT_BUTTON) {
                societe = societeTableModel.getSelectedItem();
                societe.setNom(nom);
                societe.setAdresse(adresse);
                societe.setTelephone(telephone);
                societe.setCode(code);
                societe.setContact(contact);
                societe.setEmail(email);
                societe.setFax(fax);
                success = societeModel.update(societe);
                txtNotif = "Modification OK";
            }
            if (success) {
                buildSocieteTable();
                emptyTextField();
            }
            btnNouveau.setDisable(false);
            btnSupprimer.setDisable(false);
            btnModifier.setDisable(false);
            btnValider.setDisable(true);
            btnAnnuler.setDisable(true);
            ServiceproUtil.notify(txtNotif);
        }
    }

    public void supprimerAction(ActionEvent actionEvent) {

        if (societeTableModel.getSelectedItem() != null) {
            Societe societe = societeTableModel.getSelectedItem();
            SocieteModel societeModel = new SocieteModel();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("");
            alert.setContentText("Etes vous sûr de vouloir supprimer " + societe.getNom());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (societeModel.delete(societe)) {
                    buildSocieteTable();
                    ServiceproUtil.notify("Suppression OK");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            }
        }
    }
}
