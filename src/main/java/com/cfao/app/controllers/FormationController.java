package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.prism.impl.FactoryResetException;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.textfield.TextFields;

import javax.jws.WebParam;
import javax.print.ServiceUI;
import javax.swing.text.html.Option;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    public TableView<Formation> formationTable;
    public TableColumn titreColumn;
    public TableColumn datedebutColumn;
    public TableColumn datefinColumn;
    public TextField txtCode;
    public TextField txtTitre;
    public DatePicker dateDebut;
    public DatePicker dateFin;
    public TextArea txtDescription;
    public ComboBox<Modele> comboModele;
    public ComboBox<Etatformation> comboEtatformation;
    public ComboBox<Personnel> comboFormateur;
    public Button btnAjouterFormateur;
    public Button btnSupprimerFormateur;
    public HBox researchBox;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSuppr;
    public Button btnAnnuler;
    public ListView<Personnel> listeViewFormateurs;
    public HBox hboxCompetenceAssociee;
    public ComboBox<Typeformation> comboTypeformation;
    public Button btnAjouterSupport;
    public Button btnSupprimerSupport;
    public Button btnAfficherSupport;
    public TableView<Support> supportTable;
    public TableColumn<Support, String> codeSupportColumn;
    public TableColumn<Support, String> titreSupportColumn;
    private SearchBox searchBox = new SearchBox();
    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;
    SearchBox searchBoxAssocie = new SearchBox();

    public Model<Formation> modelFormation;
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        formationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fillFormationFields());
        tabParticipant.setContent(new ListSelectionView<Formation>());
        buildCombo();
        buildTable();
    }

    private void initComponents() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datedebutColumn.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        codeSupportColumn.setCellValueFactory(param -> param.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);

        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.USER_PLUS);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.USER_TIMES);
        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);
        GlyphsDude.setIcon(btnAfficherSupport, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouterSupport, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerSupport, FontAwesomeIcon.MINUS_SQUARE);
        GlyphsDude.setIcon(btnNext, FontAwesomeIcon.ARROW_RIGHT);
        GlyphsDude.setIcon(btnPrevious, FontAwesomeIcon.ARROW_LEFT);
        GlyphsDude.setIcon(btnPrint, FontAwesomeIcon.PRINT);
        GlyphsDude.setIcon(btnSuppr, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.PENCIL);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.SHARE_SQUARE);

        searchBoxAssocie.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBoxAssocie, Priority.ALWAYS);
        hboxCompetenceAssociee.getChildren().addAll(new Label("Compétences associées : "), searchBoxAssocie);

    }

    private void buildCombo() {
        modelFormation = new Model<>();
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableArrayList(new Model<Personnel>(Model.getBeanPath("Personnel")).getList()));
                map.put("modele", FXCollections.observableList(new Model<Modele>(Model.getBeanPath("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList(new Model<Etatformation>(Model.getBeanPath("Etatformation")).getList()));
                map.put("typeformation", FXCollections.observableList(new Model<Etatformation>(Model.getBeanPath("Typeformation")).getList()));
                return map;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            ObservableMap<String, ObservableList> map = task.getValue();
            comboFormateur.setItems(map.get("personnel"));
            comboModele.setItems(map.get("modele"));
            comboEtatformation.setItems(map.get("etatformation"));
            comboTypeformation.setItems(map.get("typeformation"));
        });
    }

    private void buildTable() {
        btnNouveau.setText("Nouveau/New");
        btnModifier.setText("Modifier/Edit");
        ServiceproUtil.setDisable(false, btnNouveau, btnModifier, btnSuppr);
        ServiceproUtil.setDisable(true, comboEtatformation, comboFormateur, comboModele, btnAjouterFormateur, btnSupprimerFormateur, comboTypeformation);
        ServiceproUtil.setEditable(false, txtTitre, txtDescription, txtCode, dateDebut, dateFin);
        ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateDebut, dateFin);
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableList(new Model<Formation>(Model.getBeanPath("Formation")).getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            formationTable.setItems(task.getValue());
        });
        listeViewFormateurs.getItems().clear();
    }

    private void fillFormationFields() {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            txtCode.setText(formation.getCodeformation());
            txtTitre.setText(formation.getTitre());
            txtDescription.setText(formation.getDescription());
            comboModele.setValue(formation.getModele());
            comboEtatformation.setValue(formation.getEtatformation());
            comboTypeformation.setValue(formation.getTypeformation());
            listeViewFormateurs.setItems(FXCollections.observableArrayList(formation.getFormateurs()));
            dateDebut.setValue(formation.getDatedebut().toLocalDate());
            dateFin.setValue(formation.getDatefin().toLocalDate());
            supportTable.setItems(FXCollections.observableArrayList(formation.getSupports()));
        }
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            btnNouveau.setText("Enregistrer/Save");
            btnModifier.setText("Modifier/Edit");
            listeViewFormateurs.getItems().clear();
            ServiceproUtil.setDisable(true, btnModifier, btnSuppr);
            ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateFin, dateDebut);
            ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut);
            ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnAjouterFormateur, btnSupprimerFormateur, comboTypeformation);
            stateBtnNouveau = 1;
        } else {
            Formation formation = new Formation();
            formation.setEtatformation(comboEtatformation.getValue());
            formation.setModele(comboModele.getValue());
            formation.setFormateurs(listeViewFormateurs.getItems());
            formation.setSupports(supportTable.getItems());
            formation.setTitre(txtTitre.getText());
            formation.setDescription(txtDescription.getText());
            formation.setCodeformation(txtCode.getText());
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
            formation.setTypeformation(comboTypeformation.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return modelFormation.save(formation);
                }
            };
            task.run();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Ajouter avec succees");
                        buildTable();
                    } else {
                        ServiceproUtil.notify("Erreur d'ajout");
                    }
                }
            });
            stateBtnNouveau = 0;
        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (stateBtnModifier == 0) {
            if (formationTable.getSelectionModel().getSelectedItem() != null) {
                btnModifier.setText("Enregistrer/Save");
                btnNouveau.setText("Nouveau/New");
                ServiceproUtil.setDisable(true, btnNouveau, btnSuppr);
                ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut);
                ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnSupprimerFormateur, btnAjouterFormateur, comboTypeformation);
                stateBtnModifier = 1;
            }
        } else {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            formation.setModele(comboModele.getValue());
            formation.setCodeformation(txtCode.getText());
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
            formation.setTitre(txtTitre.getText());
            formation.setDescription(txtDescription.getText());
            formation.setEtatformation(comboEtatformation.getValue());
            formation.setFormateurs(listeViewFormateurs.getItems());
            formation.setSupports(supportTable.getItems());
            formation.setTypeformation(comboTypeformation.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return modelFormation.update(formation);
                }
            };
            task.run();
            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Modification OK");
                    buildTable();
                } else {
                    ServiceproUtil.notify("Erreur de modification");
                }
            });
            stateBtnModifier = 0;
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return modelFormation.delete(formation);
                }
            };
            task.run();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Suppression OK");
                        buildTable();
                    } else {
                        ServiceproUtil.notify("Erreur de suppression");
                    }
                }
            });
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        buildTable();
    }

    public void ajouterFormateur(ActionEvent actionEvent) {
        if (comboFormateur.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = comboFormateur.getSelectionModel().getSelectedItem();
            if (!listeViewFormateurs.getItems().contains(personnel)) {
                listeViewFormateurs.getItems().add(personnel);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sélectionner le formateur à ajouter dans la liste des formateurs");
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void supprimerFormateur(ActionEvent actionEvent) {
        if (listeViewFormateurs.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = listeViewFormateurs.getSelectionModel().getSelectedItem();
            listeViewFormateurs.getItems().remove(personnel);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setTitle("");
            alert.setContentText("Sélectionner le formateur à supprimer dans le tableau des formateurs");
            alert.showAndWait();
        }
    }

    public void ajouterSupportAction(ActionEvent actionEvent) {
        Dialog<Support> dialog = new Dialog<>();
        try {
            dialog.setTitle("Supports - Formation");
            dialog.setHeaderText("Associer des supports à la formation");
            ButtonType okButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            DialogSupportController dialogSupportController = new DialogSupportController();
            dialog.getDialogPane().setContent(dialogSupportController);
            dialog.setResultConverter(new Callback<ButtonType, Support>() {
                @Override
                public Support call(ButtonType param) {
                    return dialogSupportController.getSupport();
                }
            });
            Optional<Support> result = dialog.showAndWait();
            result.ifPresent(new Consumer<Support>() {
                @Override
                public void accept(Support support) {
                    if (!supportTable.getItems().contains(support)) {
                        supportTable.getItems().add(support);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void supprimerSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            Support support = supportTable.getSelectionModel().getSelectedItem();
            supportTable.getItems().remove(support);
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à supprimer de la formation");
        }
    }

    public void afficherSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Support support = supportTable.getSelectionModel().getSelectedItem();
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(support.getLien()));
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à afficher");
        }
    }
}
