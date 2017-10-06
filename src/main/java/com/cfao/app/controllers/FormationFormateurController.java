package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personnel;
import com.cfao.app.model.PersonnelModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by JP on 10/1/2017.
 */
public class FormationFormateurController extends AnchorPane implements Initializable {
    public TableView<Personnel> formateursTable;
    public TableColumn<Personnel, String> nomFormateurColumn;
    public TableColumn<Personnel, String> prestataireColumn;
    private Formation formation;
    public Button btnAjouterFormateur;
    public Button btnSupprimerFormateur;

    public FormationFormateurController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/formateur.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.MINUS_SQUARE);

        nomFormateurColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom() + " " +
                param.getValue().getPrenom()));
        prestataireColumn.setCellValueFactory(param -> param.getValue().prestataireProperty());
        ServiceproUtil.setDisable(true, btnAjouterFormateur, btnSupprimerFormateur);

    }

    public void setActive(boolean active) {
        ServiceproUtil.setDisable(!active, btnSupprimerFormateur, btnAjouterFormateur);
    }

    public void buildTable() {
        formateursTable.itemsProperty().bind(formation.personnelsProperty());
        //formateursTable.setItems(FXCollections.observableArrayList(formation.getPersonnels()));
    }

    public List<Personnel> getItems() {
        return formateursTable.getItems();
    }

    public void ajouterFormateur(ActionEvent actionEvent) {
        javafx.scene.control.Dialog<Personnel> dialog = DialogUtil.dialogTemplate();
        try {
            dialog.setTitle("Formateurs - Formation");
            FormationFormateurDialogController formationDialogController = new FormationFormateurDialogController();
            dialog.getDialogPane().setContent(formationDialogController);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    return formationDialogController.getPersonnel();
                else
                    return null;
            });
            Optional<Personnel> result = dialog.showAndWait();
            result.ifPresent(personnel -> {
                if (!formateursTable.getItems().contains(personnel)) {
                    formateursTable.getItems().add(personnel);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void supprimerFormateur(ActionEvent actionEvent) {
        if (formateursTable.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = formateursTable.getSelectionModel().getSelectedItem();
            formateursTable.getItems().remove(personnel);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setTitle("");
            alert.setContentText("Sélectionner le formateur à supprimer dans le tableau des formateurs");
            alert.showAndWait();
        }
    }

    class FormationFormateurDialogController extends AnchorPane implements Initializable {
        @FXML
        public ComboBox<Personnel> comboPersonnels;

        public FormationFormateurDialogController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/addFormateurDialog.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        }

        public Personnel getPersonnel() {
            if (comboPersonnels.getSelectionModel().getSelectedItem() != null) {
                return comboPersonnels.getSelectionModel().getSelectedItem();
            }
            return null;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            Task<ObservableList<Personnel>> task = new Task<ObservableList<Personnel>>() {
                @Override
                protected ObservableList<Personnel> call() throws Exception {
                    return FXCollections.observableArrayList((new PersonnelModel()).getList());
                }
            };
            new Thread(task).start();
            comboPersonnels.itemsProperty().bind(task.valueProperty());
            comboPersonnels.setCellFactory(new Callback<ListView<Personnel>, ListCell<Personnel>>() {
                @Override
                public ListCell<Personnel> call(ListView<Personnel> param) {
                    return new ListCell<Personnel>(){
                        @Override
                        protected void updateItem(Personnel item, boolean empty) {
                            super.updateItem(item, empty);
                            if(!empty && item != null){
                                setText(item.getNom() + " " + item.getPrenom() + " (Prestataire : " + item.getPrestataire() + ")");
                            }
                        }
                    };
                }
            });
        }
    }
}
