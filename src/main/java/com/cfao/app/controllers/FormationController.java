package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.reports.FormationExcel;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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

    public VBox researchBox;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;

    public ComboBox<Typeformation> comboTypeformation;
    public ComboBox<LieuFormation> comboLieuFormation;
    public StackPane formationStackPane;
    public Button btnExcel;
    public HBox formateurContainer;

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
    private FormationSupportController supportController;
    private FormationFormateurController formateurController;
    public HBox supportContainer;


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


        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);

        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabPersonne, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);
        GlyphsDude.setIcon(tabPlanification, FontAwesomeIcon.CALENDAR);
        ButtonUtil.next(btnNext);
        ButtonUtil.previous(btnPrevious);
        ButtonUtil.print(btnPrint);
        ButtonUtil.excel(btnExcel);
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
                supportController = new FormationSupportController();
                formateurController = new FormationFormateurController();
                tabParticipant.setContent(participantController);
                tabPersonne.setContent(personneController);
                tabCompetenceAssociee.setContent(competenceController);
                tabPlanification.setContent(planificationController);
                supportContainer.getChildren().setAll(supportController);
                formateurContainer.getChildren().setAll(formateurController);
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
                map.put("modele", FXCollections.observableList((new Model<Modele>("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList((new Model<EtatFormation>("EtatFormation")).getList()));
                map.put("typeformation", FXCollections.observableList((new Model<Typeformation>("Typeformation")).getList()));
                map.put("lieuformation", FXCollections.observableList((new Model<LieuFormation>("LieuFormation")).getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            ObservableMap<String, ObservableList> map = task.getValue();
            comboModele.setItems((ObservableList<Modele>) map.get("modele"));
            comboEtatformation.setItems(map.get("etatformation"));
            comboTypeformation.setItems(map.get("typeformation"));
            comboLieuFormation.setItems(map.get("lieuformation"));
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

        ServiceproUtil.setDisable(false, btnNouveau, btnModifier, btnSupprimer);
        ServiceproUtil.setDisable(true, comboEtatformation, comboModele,
                comboTypeformation, comboLieuFormation);
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
        //formateursTable.getItems().clear();
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
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                formationTable.getSelectionModel().selectFirst();
                fillFormationFields(formationTable.getSelectionModel().getSelectedItem());
            }
        });
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            logger.error(task.getException());
            AlertUtil.showSimpleAlert("Erreur", task.getException().getMessage());
        });
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
        comboLieuFormation.setValue(formation.getLieuFormation());
        dateDebut.setValue(new java.sql.Date(formation.getDatedebut().getTime()).toLocalDate());
        dateFin.setValue(new java.sql.Date(formation.getDatefin().getTime()).toLocalDate());

        supportController.setFormation(formation);
        formateurController.setFormation(formation);
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
        supportController.buildTable();
        formateurController.buildTable();

    }

    public void setFormationParams(Formation formation) {
        formation.setEtatFormation(comboEtatformation.getValue());
        formation.setModele(comboModele.getValue());
        formation.setPersonnels(formateurController.getItems());
        formation.setTitre(txtTitre.getText());
        formation.setDescription(txtDescription.getText());
        if (!txtScore.getText().isEmpty()) {
            formation.setScore(Integer.parseInt(txtScore.getText()));
        }
        formation.setTp(txtTP.getText());
        formation.setRemarque(txtRemarque.getText());
        formation.setCodeformation(txtCode.getText());
        if (dateDebut.getValue() != null) {
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
        }
        if (dateFin.getValue() != null) {
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
        }
        formation.setTypeFormation(comboTypeformation.getValue());
        formation.setLieuFormation(comboLieuFormation.getValue());

    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            ButtonUtil.save(btnNouveau);
            ButtonUtil.edit(btnModifier);
            //formateursTable.getItems().clear();

            ServiceproUtil.setDisable(true, btnModifier, btnSupprimer);
            ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtScore, txtTP);
            ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtScore, txtCode, txtTP);
            ServiceproUtil.setDisable(false, comboModele, comboEtatformation,
                     comboTypeformation, comboLieuFormation);
            formateurController.setActive(true);
            stateBtnNouveau = 1;
        } else {
            Formation formation = new Formation();
            setFormationParams(formation);
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
                        ButtonUtil.add(btnNouveau);
                        stateBtnNouveau = 0;
                        formationTable.getSelectionModel().select(formation);
                        ServiceproUtil.setDisable(false, btnModifier, btnSupprimer);
                        ServiceproUtil.setEditable(false, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtScore, txtCode, txtTP);
                        ServiceproUtil.setDisable(true, comboModele, comboEtatformation, comboTypeformation, comboLieuFormation);
                        formateurController.setActive(false);
                    } else {
                        ServiceproUtil.notify("Erreur d'ajout");
                    }
                }
            });
            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    task.getException().printStackTrace();
                    ServiceproUtil.notify(task.getException().getMessage());
                }
            });
            stateBtnNouveau = 0;
        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (stateBtnModifier == 0) {
            if (formationTable.getSelectionModel().getSelectedItem() != null) {
                ButtonUtil.save(btnModifier);
                ButtonUtil.add(btnNouveau);
                ServiceproUtil.setDisable(true, btnNouveau, btnSupprimer);
                ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtRemarque, txtScore, txtTP);
                ServiceproUtil.setDisable(false, comboModele, comboEtatformation, comboTypeformation, comboLieuFormation);
                formateurController.setActive(true);
                stateBtnModifier = 1;
            }
        } else {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            setFormationParams(formation);
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
                    ButtonUtil.edit(btnModifier);
                    ServiceproUtil.setDisable(false, btnNouveau, btnSupprimer);
                    ServiceproUtil.setEditable(false, txtCode, txtDescription, txtTitre, dateFin, dateDebut, txtRemarque, txtScore, txtTP);
                    ServiceproUtil.setDisable(true, comboModele, comboEtatformation, comboTypeformation, comboLieuFormation);
                    formateurController.setActive(false);
                    //StageManager.loadContent("/views/formation/formation.fxml");
                    formationTable.getSelectionModel().select(formation);
                } else {
                    ServiceproUtil.notify("Erreur de modification");
                }
            });
            task.setOnFailed(event -> task.getException().printStackTrace());
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
                        //StageManager.loadContent("/views/formation/formation.fxml");
                        if (formationTable.getItems().size() > 0) {
                            formationTable.getSelectionModel().selectFirst();
                        }
                        formateurController.setActive(false);
                    } else {
                        ServiceproUtil.notify("Erreur de suppression");
                    }
                });
                task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        task.getException().printStackTrace();
                        ServiceproUtil.notify(task.getException().getMessage());
                    }
                });
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation à supprimer");
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        StageManager.loadContent("/views/formation/formation.fxml");
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

    public void excelAction(ActionEvent event) {
        Formation formation = formationTable.getSelectionModel().getSelectedItem();
        if (formation == null) {
            AlertUtil.showWarningMessage("Information", "Veuillez d'abord choisir une formation");
            return;
        }
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FormationExcel formationExcel = new FormationExcel();
                    formationExcel.printDetails(formation);
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
            task.setOnFailed(event12 -> {
                logger.error(task.getException());
                task.getException().printStackTrace();
            });
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            logger.error(ex);
        }
    }
}
