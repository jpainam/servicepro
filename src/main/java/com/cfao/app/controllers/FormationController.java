package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import org.controlsfx.control.ListSelectionView;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> titreColumn;
    public TableColumn<Formation, LocalDate> datedebutColumn;
    public TableColumn<Formation, LocalDate> datefinColumn;
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
    public Button btnSupprimer;
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
    public TableView<Personne> participantTable;
    public TableColumn<Personne, String> matriculeParticipantColumn;
    public TableColumn<Personne, String> nomParticipantColumn;
    public TableColumn<Personne, String> prenomParticipantColumn;
    public TableColumn<Personne, Section> sectionParticipantColumn;
    public HBox hboxSearchParticipant;
    public Button btnPreviousParticipant;
    public Button btnNextParticipant;
    public Button btnPrintParticipant;
    public Button btnAjouterParticipant;
    public Button btnSupprimerParticipant;
    public Button btnAnnulerParticipant;
    private SearchBox searchBox = new SearchBox();
    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;
    SearchBox searchBoxAssocie = new SearchBox();

    public FormationModel formationModel = new FormationModel();
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    public int stateBtnAjouterParticipant = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        formationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fillFormationFields());
        buildCombo();
        buildTable();
    }

    private void initComponents() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datedebutColumn.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datedebutColumn.setCellFactory(new Callback<TableColumn<Formation, LocalDate>, TableCell<Formation, LocalDate>>() {
            @Override
            public TableCell<Formation, LocalDate> call(TableColumn<Formation, LocalDate> param) {
                TableCell cell = new TableCell<Formation, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new Label(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER_RIGHT);
                return cell;
            }
        });
        datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        datefinColumn.setCellFactory(new Callback<TableColumn<Formation, LocalDate>, TableCell<Formation, LocalDate>>() {
            @Override
            public TableCell<Formation, LocalDate> call(TableColumn<Formation, LocalDate> param) {
                TableCell cell = new TableCell<Formation, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new Label(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER_RIGHT);
                return cell;
            }
        });
        codeSupportColumn.setCellValueFactory(param -> param.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        matriculeParticipantColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        nomParticipantColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomParticipantColumn.setCellValueFactory(param -> param.getValue().prenomProperty());
        sectionParticipantColumn.setCellValueFactory(param -> param.getValue().section());
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
        ButtonUtil.next(btnNext, btnNextParticipant);
        ButtonUtil.previous(btnPrevious, btnPreviousParticipant);
        ButtonUtil.print(btnPrint, btnPrintParticipant);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimerParticipant, btnSupprimer);
        ButtonUtil.add(btnNouveau, btnAjouterParticipant);
        ButtonUtil.cancel(btnAnnuler, btnAnnulerParticipant);
        searchBoxAssocie.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBoxAssocie, Priority.ALWAYS);
        hboxCompetenceAssociee.getChildren().addAll(new Label("Compétences associées : "), searchBoxAssocie);

    }

    private void buildCombo() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableArrayList((new PersonnelModel()).getList()));
                map.put("modele", FXCollections.observableList((new Model<Modele>("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList((new Model<Etatformation>("Etatformation")).getList()));
                map.put("typeformation", FXCollections.observableList((new Model<Typeformation>("Typeformation")).getList()));
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
        btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        ServiceproUtil.setDisable(false, btnNouveau, btnModifier, btnSupprimer);
        ServiceproUtil.setDisable(true, comboEtatformation, comboFormateur, comboModele, btnAjouterFormateur, btnSupprimerFormateur, comboTypeformation);
        ServiceproUtil.setEditable(false, txtTitre, txtDescription, txtCode, dateDebut, dateFin);
        ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateDebut, dateFin);
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableList(formationModel.getList());
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
            System.err.println(formation.getParticipants());
            participantTable.setItems(FXCollections.observableArrayList(formation.getParticipants()));
        }
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            btnNouveau.setText("Enregistrer/Save");
            btnModifier.setText("Modifier/Edit");
            listeViewFormateurs.getItems().clear();
            ServiceproUtil.setDisable(true, btnModifier, btnSupprimer);
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
                    return formationModel.save(formation);
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
                ServiceproUtil.setDisable(true, btnNouveau, btnSupprimer);
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
                    return formationModel.update(formation);
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
                    return formationModel.delete(formation);
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
        stateBtnNouveau = 0;
        stateBtnModifier = 0;
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
                System.out.println((new File(support.getLien())).getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à afficher");
        }
    }

    public void ajouterParticipant(ActionEvent actionEvent) {
        if (formationTable.getSelectionModel().getSelectedItem() == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation");
            return;
        }
        Formation formation = formationTable.getSelectionModel().getSelectedItem();
        if (stateBtnAjouterParticipant == 0) {
            Dialog<List<Personne>> dialog = new Dialog<>();
            dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            dialog.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            try {
                ButtonType validerButton = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

                dialog.getDialogPane().getButtonTypes().addAll(validerButton, cancelButton);
                DialogParticipantController dialogParticipantController = new DialogParticipantController(formation);
                dialog.getDialogPane().setContent(dialogParticipantController);
                dialog.setResultConverter(param -> dialogParticipantController.getParticipants());
                Optional<List<Personne>> result = dialog.showAndWait();
                result.ifPresent(personnes -> {
                    participantTable.getItems().clear();
                    participantTable.getItems().addAll(FXCollections.observableArrayList(personnes));
                    formation.setParticipants(participantTable.getItems());
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
            stateBtnAjouterParticipant = 1;
            btnAjouterParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
        } else {
            formation.setParticipants(participantTable.getItems());
            if (formationModel.update(formation)) {
                ServiceproUtil.notify("Participants ajoutés avec succès");
            } else {
                ServiceproUtil.notify("Une erreur est survenue");
            }
            stateBtnAjouterParticipant = 0;
        }
    }

    public void supprimerParticipant(ActionEvent actionEvent) {
        btnAjouterParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.add"));
        if (participantTable.getSelectionModel().getSelectedItem() != null) {
            Personne personne = participantTable.getSelectionModel().getSelectedItem();
            participantTable.getItems().remove(personne);
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            formation.setParticipants(participantTable.getItems());
            if (formationModel.update(formation)) {
                ServiceproUtil.notify("Suppression OK");
            } else {
                ServiceproUtil.notify("Une erreur est survenue");
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le participant à supprimer");
        }
    }

    public void annulerParticipant(ActionEvent actionEvent) {
        participantTable.getItems().clear();
        StageManager.loadContent(FXMLView.FORMATION.getFXMLFile());
    }
}
