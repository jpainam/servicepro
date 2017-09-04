package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonnelModel;
import com.cfao.app.reports.PrintFormation;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    static Logger logger = Logger.getLogger(FormationController.class);
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> titreColumn;
    public TableColumn<Formation, LocalDate> datedebutColumn;
    public TableColumn<Formation, LocalDate> datefinColumn;
    public TextField txtCode;
    public TextField txtTitre;
    public DatePicker dateDebut;
    public DatePicker dateFin;
    public TextArea txtDescription;
    public TextField txtScore;
    public TextField txtTP;
    public TextArea txtRemarque;
    public ComboBox<Modele> comboModele;
    public ComboBox<EtatFormation> comboEtatformation;
    public ComboBox<Personnel> comboFormateur;
    public Button btnAjouterFormateur;
    public Button btnSupprimerFormateur;
    public VBox researchBox;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;

    public ListView<Personnel> listeViewFormateurs;
    public ComboBox<Typeformation> comboTypeformation;
    public ComboBox<SocieteFormatrice> comboSocieteFormatrice;
    public Button btnAjouterSupport;
    public Button btnSupprimerSupport;
    public Button btnAfficherSupport;
    public TableView<SupportFormation> supportTable;
    public TableColumn<SupportFormation, String> codeSupportColumn;
    public TableColumn<SupportFormation, String> titreSupportColumn;
    public StackPane formationStackPane;


    private SearchBox searchBox = new SearchBox();

    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;
    public Tab tabPlanification;
    public Tab tabPersonne;

    public FormationModel formationModel = new FormationModel();
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    private FormationPersonneController personneController;
    private FormationCompetenceController competenceController;
    private FormationPlanificationController planificationController;
    private FormationParticipantController participantController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        formationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillFormationFields(formationTable.getSelectionModel().getSelectedItem());
        });
        buildTable();
        buildCombo();
    }

    private void initComponents() {
        titreColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        datedebutColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutColumn.setCellFactory(new DateTableCellFactory<Formation>());
        datefinColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinColumn.setCellFactory(new DateTableCellFactory<Formation>());
        codeSupportColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);


        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.USER_PLUS);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.USER_TIMES);
        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabPersonne, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);
        GlyphsDude.setIcon(tabPlanification, FontAwesomeIcon.CALENDAR);
        GlyphsDude.setIcon(btnAfficherSupport, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouterSupport, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerSupport, FontAwesomeIcon.MINUS_SQUARE);

        ButtonUtil.next(btnNext);
        ButtonUtil.previous(btnPrevious);
        ButtonUtil.print(btnPrint);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.add(btnNouveau);
        ButtonUtil.cancel(btnAnnuler);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                personneController = new FormationPersonneController();
                participantController = new FormationParticipantController();
                competenceController = new FormationCompetenceController();
                planificationController = new FormationPlanificationController();
                tabParticipant.setContent(participantController);
                tabPersonne.setContent(personneController);
                tabCompetenceAssociee.setContent(competenceController);
                tabPlanification.setContent(planificationController);
                return null;
            }
        };
        new Thread(task).run();

    }

    private void buildCombo() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableArrayList((new PersonnelModel()).getList()));
                map.put("modele", FXCollections.observableList((new Model<Modele>("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList((new Model<EtatFormation>("EtatFormation")).getList()));
                map.put("typeformation", FXCollections.observableList((new Model<Typeformation>("Typeformation")).getList()));
                map.put("societeformatrice", FXCollections.observableList((new Model<SocieteFormatrice>("SocieteFormatrice")).getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            ObservableMap<String, ObservableList> map = task.getValue();
            comboFormateur.setItems(map.get("personnel"));
            comboModele.setItems((ObservableList<Modele>) map.get("modele"));
            comboEtatformation.setItems(map.get("etatformation"));
            comboTypeformation.setItems(map.get("typeformation"));
            comboSocieteFormatrice.setItems(map.get("societeformatrice"));
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                AlertUtil.showErrorMessage(new Exception(task.getException()));
                System.exit(0);
            }
        });
    }

    private void buildTable() {
        btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        ServiceproUtil.setDisable(false, btnNouveau, btnModifier, btnSupprimer);
        ServiceproUtil.setDisable(true, comboEtatformation, comboFormateur, comboModele, btnAjouterFormateur,
                btnSupprimerFormateur, comboTypeformation, comboSocieteFormatrice);
        ServiceproUtil.setEditable(false, txtTitre, txtDescription, txtCode, dateDebut, dateFin, txtScore, txtTP);
        ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateDebut, dateFin, txtScore, txtTP);
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableList(formationModel.getList());
            }
        };
        ProgressIndicatorUtil.show(formationStackPane, task);
        formationTable.itemsProperty().bind(task.valueProperty());
        listeViewFormateurs.getItems().clear();
        formationTable.setRowFactory((TableView<Formation> param) -> {
            final TableRow<Formation> row = new TableRow<>();
            final Tooltip tooltip = new Tooltip();
            row.hoverProperty().addListener(observable -> {
                final Formation formation = row.getItem();
                if (row.isHover() && formation != null) {
                    tooltip.setText(formation.getTitre() + " " + formation.getDescription());
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });
        new Thread(task).start();
    }

    private void fillFormationFields(Formation formation) {
        if (formation == null) {
            return;
        }
        txtCode.setText(formation.getCodeformation());
        txtTitre.setText(formation.getTitre());
        txtDescription.setText(formation.getDescription());
        txtScore.setText(formation.getScore() + "");
        txtTP.setText(formation.getTp());
        comboModele.setValue(formation.getModele());
        comboEtatformation.setValue(formation.getEtatFormation());
        comboTypeformation.setValue(formation.getTypeFormation());
        comboSocieteFormatrice.setValue(formation.getSocieteFormatrice());
        listeViewFormateurs.setItems(FXCollections.observableArrayList(formation.getPersonnels()));
        dateDebut.setValue(new java.sql.Date(formation.getDatedebut().getTime()).toLocalDate());
        dateFin.setValue(new java.sql.Date(formation.getDatefin().getTime()).toLocalDate());
        supportTable.setItems(FXCollections.observableArrayList(formation.getSupportFormations()));
        //System.err.println(formation.getFormationPersonnes());
        personneController.setFormation(formation);
        participantController.setFormation(formation);
        competenceController.setFormation(formation);
        planificationController.setFormation(formation);

        /** Realise l'injection pour la classe FormationController */
        personneController.setParticipantTable(participantController.getParticipantTable());
        personneController.buildTable();
        participantController.setPersonneData(personneController.getPersonneData());
        participantController.buildTable();
        competenceController.buildTable();
        planificationController.buildTable();

    }


    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
            listeViewFormateurs.getItems().clear();
            ServiceproUtil.setDisable(true, btnModifier, btnSupprimer);
            ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtScore, txtTP);
            ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtCode, txtTP);
            ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnAjouterFormateur,
                    btnSupprimerFormateur, comboTypeformation, comboSocieteFormatrice);
            stateBtnNouveau = 1;
        } else {
            Formation formation = new Formation();
            formation.setEtatFormation(comboEtatformation.getValue());
            formation.setModele(comboModele.getValue());
            formation.setPersonnels(listeViewFormateurs.getItems());
            formation.setSupportFormations(supportTable.getItems());
            formation.setTitre(txtTitre.getText());
            formation.setDescription(txtDescription.getText());
            formation.setScore(Integer.parseInt(txtScore.getText()));
            formation.setTp(txtTP.getText());
            formation.setRemarque(txtRemarque.getText());
            formation.setCodeformation(txtCode.getText());
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
            formation.setTypeFormation(comboTypeformation.getValue());
            formation.setSocieteFormatrice(comboSocieteFormatrice.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return formationModel.save(formation);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Ajout OK");
                        formationTable.getItems().add(formation);
                        formationTable.getSelectionModel().select(formation);
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
                btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
                btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.add"));
                ServiceproUtil.setDisable(true, btnNouveau, btnSupprimer);
                ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtRemarque, txtScore, txtTP);
                ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnSupprimerFormateur,
                        btnAjouterFormateur, comboTypeformation, comboSocieteFormatrice);
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
            formation.setScore(Integer.parseInt(txtScore.getText()));
            formation.setTp(txtTP.getText());
            formation.setRemarque(txtRemarque.getText());
            formation.setEtatFormation(comboEtatformation.getValue());
            formation.setPersonnels(listeViewFormateurs.getItems());
            formation.setSupportFormations(supportTable.getItems());
            formation.setTypeFormation(comboTypeformation.getValue());
            formation.setSocieteFormatrice(comboSocieteFormatrice.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return formationModel.update(formation);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Modification OK");
                    StageManager.loadContent("/views/formation/formation.fxml");
                } else {
                    ServiceproUtil.notify("Erreur de modification");
                }
            });
            task.setOnFailed(event -> AlertUtil.showErrorMessage(new Exception(task.getException())));
            stateBtnModifier = 0;
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            boolean ok = AlertUtil.showConfirmationMessage("Etes vous sûr de vouloir supprimer " + formation);
            if (ok) {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return formationModel.delete(formation);
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event -> {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Suppression OK");
                        formationTable.getItems().remove(formation);
                        StageManager.loadContent("/views/formation/formation.fxml");
                    } else {
                        ServiceproUtil.notify("Erreur de suppression");
                    }
                });
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation à supprimer");
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
        if (stateBtnModifier == 0 && stateBtnNouveau == 0) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord cliquer sur Modification ou Nouveau");
            return;
        }
        Dialog<Support> dialog = new Dialog<>();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setVisible(false);
        StageManager.getContentLayout().getChildren().add(region);
        region.visibleProperty().bind(dialog.showingProperty());
        try {
            dialog.setTitle("Supports - Formation");
            dialog.setHeaderText("Associer des supports à la formation");
            ButtonType okButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            FormationSupportDialogController formationSupportDialogController = new FormationSupportDialogController();
            dialog.getDialogPane().setContent(formationSupportDialogController);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    return formationSupportDialogController.getSupport();
                else
                    return null;
            });
            Optional<Support> result = dialog.showAndWait();
            result.ifPresent(support -> {
                Formation formation = formationTable.getSelectionModel().getSelectedItem();
                if (!formation.getSupportFormations().contains(support)) {
                    SupportFormation sp = new SupportFormation();
                    sp.setSupport(support);
                    supportTable.getItems().add(sp);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void supprimerSupportAction(ActionEvent actionEvent) {
        if (stateBtnModifier == 0 && stateBtnNouveau == 0) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord cliquer sur Modification ou Nouveau");
            return;
        }
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            SupportFormation support = supportTable.getSelectionModel().getSelectedItem();
            supportTable.getItems().remove(support);
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à supprimer de la formation");
        }
    }

    public void afficherSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Support support = supportTable.getSelectionModel().getSelectedItem().getSupport();
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


    public void previousAction(ActionEvent event) {
        if (formationTable.getSelectionModel().getSelectedIndex() > 0) {
            formationTable.getSelectionModel().selectPrevious();
        }
    }

    public void nextAction(ActionEvent event) {
        if (formationTable.getSelectionModel().getSelectedIndex() < formationTable.getItems().size()) {
            formationTable.getSelectionModel().selectNext();
        }
    }

    public void printAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PrintFormation print = new PrintFormation();
                if (formationTable.getSelectionModel().getSelectedItem() != null) {
                    print.showReport(formationTable.getSelectionModel().getSelectedItem());
                } else {
                    print.showReport();
                }
                return null;
            }
        };
        StageManager.getProgressBar().progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            StageManager.getProgressBar().progressProperty().unbind();
            StageManager.getProgressBar().setProgress(0);
            ServiceproUtil.notify("Impression réussie");
        });

    }
}
