package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonnelModel;
import com.cfao.app.model.SocieteModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.SearchFieldClassTool;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class SocieteController implements Initializable {
    public TableColumn<Societe, String> societeColumn;
    public TableColumn<Societe, String> adresseColumn;
    public TableView<Societe> societeTable;
    public Button btnSupprimer;
    public Button btnModifier;
    public Button btnAnnuler;
    public SearchBox searchBox = new SearchBox();
    public SearchBox searchBox2 = new SearchBox();
    public TextField txtNom;
    public TextArea txtAdresse;
    public Button btnNouveau;
    public HBox researchBox;
    public HBox researchBox2;
    public TableColumn<Societe, String> telephoneColumn;
    public TextField txtCode;
    public TextField txtTelephone;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtFax;
    public TableView civiliteTable;
    public TableColumn<Personne, String> nomCiviliteColumn;
    public TableColumn<Personne, String> matriculeCiviliteColumn;
    public TableColumn<Personne, String> profilCiviliteColumn;
    public TableColumn<Personne, String> prenomCiviliteColumn;
    public TableColumn<Personne, String> sectionCiviliteColumn;
    private TableView.TableViewSelectionModel<Societe> societeTableModel;

    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        buildSocieteTable();
    }

    private void initComponents() {

        societeColumn.setCellValueFactory(cellValue -> cellValue.getValue().nomProperty());
        adresseColumn.setCellValueFactory(param -> param.getValue().adresseProperty());
        telephoneColumn.setCellValueFactory(param -> param.getValue().telephoneProperty());
        nomCiviliteColumn.setCellValueFactory(cellValue -> cellValue.getValue().nomProperty());
        matriculeCiviliteColumn.setCellValueFactory(cellValue -> cellValue.getValue().matriculeProperty());
        prenomCiviliteColumn.setCellValueFactory(cellValue -> cellValue.getValue().prenomProperty());
        sectionCiviliteColumn.setCellValueFactory(cellValue -> cellValue.getValue().getSection().libelleProperty());
        societeTableModel = societeTable.getSelectionModel();

        GlyphsDude.setIcon(btnSupprimer, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.EDIT);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE_TEXT);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.TIMES);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        HBox.setHgrow(searchBox2, Priority.ALWAYS);
        searchBox2.setMaxWidth(Double.MAX_VALUE);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setSpacing(10);
        researchBox2.setSpacing(10);
        researchBox.getChildren().setAll(new Label("Sociétés : "), searchBox);
        researchBox2.getChildren().setAll(new Label("Liste des personnes appartenant à la société : "), searchBox2);

    }

    /**
     * Construction de la table View des societes avec les capacites de recherche
     */
    private void buildSocieteTable() {
        ServiceproUtil.setEditable(false, txtNom, txtAdresse, txtContact, txtFax, txtEmail, txtTelephone, txtCode);
        SocieteModel societeModel = new SocieteModel();
        Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
            @Override
            protected ObservableList<Societe> call() throws Exception {
                return FXCollections.observableArrayList(societeModel.select());
            }
        };
        task.run();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            FilteredList<Societe> filteredList = new FilteredList<Societe>(task.getValue(), (Societe p) -> {
                return true;
            });
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
            societeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (societeTable.getSelectionModel().getSelectedItem() != null) {
                    Societe societe = societeTableModel.getSelectedItem();
                    txtNom.setText(societe.getNom());
                    txtAdresse.setText(societe.getAdresse());
                    txtCode.setText(societe.getCode());
                    txtContact.setText(societe.getContact());
                    txtEmail.setText(societe.getEmail());
                    txtFax.setText(societe.getFax());
                    txtTelephone.setText(societe.getTelephone());
                    buildCiviliteTable();
                }
            });
        });
    }

    private void buildCiviliteTable() {
        if (societeTable.getSelectionModel().getSelectedItem() != null) {
            Societe societe = societeTable.getSelectionModel().getSelectedItem();
            PersonnelModel personnelModel = new PersonnelModel();
            Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
                @Override
                protected ObservableList<Personne> call() throws Exception {
                    return FXCollections.observableArrayList(personnelModel.getPersonneBySociete(societe));
                }
            };
            task.run();
            task.setOnSucceeded(event -> {
                FilteredList<Personne> filteredList = new FilteredList<Personne>(FXCollections.observableArrayList(task.getValue()), (Personne p) -> true);
                searchBox2.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(personne -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String valueCompare = newValue.toLowerCase();
                    if (personne.getNom().toLowerCase().contains(valueCompare) || personne.getPrenom().toLowerCase().contains(valueCompare)) {
                        return true;
                    }
                    if (personne.getMatricule().toLowerCase().contains(valueCompare)) {
                        return true;
                    }
                    return false;
                }));
                SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);
                sortedList.comparatorProperty().bind(civiliteTable.comparatorProperty());
                civiliteTable.setItems(sortedList);
            });
        } else {
            civiliteTable.getItems().clear();
        }
    }

    public void editAction(ActionEvent actionEvent) {
        if (societeTable.getSelectionModel().getSelectedItem() != null) {
            if (stateBtnModifier == 0) {
                btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
                btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
                ServiceproUtil.setDisable(true, btnNouveau);
                ServiceproUtil.setEditable(true, txtNom, txtAdresse, txtContact, txtFax, txtEmail, txtTelephone, txtCode);
                stateBtnModifier = 1;
            } else {
                Societe societe = societeTableModel.getSelectedItem();
                societe.setNom(txtNom.getText());
                societe.setAdresse(txtAdresse.getText());
                societe.setTelephone(txtTelephone.getText());
                societe.setCode(txtCode.getText());
                societe.setContact(txtContact.getText());
                societe.setEmail(txtEmail.getText());
                societe.setFax(txtFax.getText());
                Model<Societe> societeModel = new Model<>(Model.getBeanPath("Societe"));
                if (societeModel.update(societe)) {
                    ServiceproUtil.notify("Modification OK");
                } else {
                    ServiceproUtil.notify("Erreur de modification");
                }
                buildSocieteTable();
                ServiceproUtil.emptyFields(txtNom, txtAdresse, txtTelephone, txtEmail, txtFax, txtContact, txtCode);
                ServiceproUtil.setEditable(false, txtNom, txtAdresse, txtContact, txtFax, txtEmail, txtTelephone, txtCode);
                btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                ServiceproUtil.setDisable(false, btnNouveau);
                stateBtnModifier = 0;
            }
        }
    }

    public void annulerAction(ActionEvent actionEvent) {

        ServiceproUtil.emptyFields(txtNom, txtAdresse, txtTelephone, txtEmail, txtFax, txtContact, txtCode);
        ServiceproUtil.setDisable(false, btnNouveau, btnModifier);
        stateBtnNouveau = 0;
        stateBtnModifier = 0;
        btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        buildSocieteTable();
    }

    public void nouveauAction(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            ServiceproUtil.emptyFields(txtNom, txtAdresse, txtTelephone, txtEmail, txtFax, txtContact, txtCode);
            ServiceproUtil.setEditable(true, txtNom, txtAdresse, txtContact, txtFax, txtEmail, txtTelephone, txtCode);
            btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
            ServiceproUtil.setDisable(true, btnModifier);
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            stateBtnNouveau = 1;
        } else {
            Model<Societe> model = new Model<>(Model.getBeanPath("Societe"));
            Societe societe = new Societe();
            societe.setNom(txtNom.getText());
            societe.setAdresse(txtAdresse.getText());
            societe.setTelephone(txtTelephone.getText());
            societe.setCode(txtCode.getText());
            societe.setContact(txtContact.getText());
            societe.setEmail(txtEmail.getText());
            societe.setFax(txtFax.getText());
            model.save(societe);
            ServiceproUtil.emptyFields(txtNom, txtAdresse, txtTelephone, txtEmail, txtFax, txtContact, txtCode);
            ServiceproUtil.setEditable(true, txtNom, txtAdresse, txtContact, txtFax, txtEmail, txtTelephone, txtCode);
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
            ServiceproUtil.setDisable(false, btnModifier);
            buildSocieteTable();
            stateBtnNouveau = 0;
        }
    }

    public void supprimerAction(ActionEvent actionEvent) {
        if (societeTable.getSelectionModel().getSelectedItem() != null) {
            Societe societe = societeTableModel.getSelectedItem();
            SocieteModel societeModel = new SocieteModel();
            boolean okay = AlertUtil.showConfirmationMessage("Suppression", "Etes vous sûr de vouloir supprimer " + societe);
            if (okay) {
                if (societeModel.delete(societe)) {
                    ServiceproUtil.notify("Suppression OK");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
                buildSocieteTable();
                ServiceproUtil.emptyFields(txtNom, txtAdresse, txtTelephone, txtEmail, txtFax, txtContact, txtCode);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la société à supprimer");
        }
    }
}
