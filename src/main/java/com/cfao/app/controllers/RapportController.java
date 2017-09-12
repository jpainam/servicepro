package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.reports.CiviliteExcel;
import com.cfao.app.reports.FormationExcel;
import com.cfao.app.reports.ListeInscriptionRapport;
import com.cfao.app.reports.PlanificationModeleRapport;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/1/2017.
 */
public class RapportController implements Initializable {
    static Logger logger = Logger.getLogger(RapportController.class);
    public Hyperlink listeInscription;
    public Hyperlink modelePlanification;
    //public Hyperlink pivotTableTest;
    public Tab rapportTab;

    public ComboBox<Formation> comboFormation;
    public Hyperlink btnListeInscritptionParFormation;
    public Hyperlink listeCivilite;
    public ComboBox<Personne> comboPersonne;
    public Hyperlink feuillePresence;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        GlyphsDude.setIcon(rapportTab, FontAwesomeIcon.CALENDAR);
        ButtonUtil.excel(listeInscription, modelePlanification, btnListeInscritptionParFormation,
                listeCivilite, feuillePresence);
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("formation", FXCollections.observableArrayList(new FormationModel().getList()));
                map.put("personne", FXCollections.observableArrayList(new PersonneModel().getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            comboFormation.setItems(task.getValue().get("formation"));
            comboPersonne.setItems(task.getValue().get("personne"));
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            logger.error(task.getException());
        });
    }

    public void listeInscriptionAction(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ListeInscriptionRapport rapport = new ListeInscriptionRapport();
                    rapport.printListe();
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
    /*public void testPivotTable(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FormationExcel excelFormation = new FormationExcel();
                    excelFormation.pivotTable();
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
    }*/

    public void modelePlanificationAction(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    PlanificationModeleRapport modeleRapport = new PlanificationModeleRapport();
                    modeleRapport.printPlanificationModele();
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

    public void listeInscritptionParFormationAction(ActionEvent event) {
        Formation formation = comboFormation.getSelectionModel().getSelectedItem();
        if (formation == null) {
            AlertUtil.showWarningMessage("Information", "Veuillez choisir la formation d'abord");
            return;
        }
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FormationExcel formationExcel = new FormationExcel();
                    formationExcel.printInscription(formation);
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

    public void listeCiviliteAction(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    CiviliteExcel civiliteExcel = new CiviliteExcel();
                    civiliteExcel.printListe();
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

    public void feuillePresenceAction(ActionEvent event) {
        Formation formation = comboFormation.getSelectionModel().getSelectedItem();
        if (formation == null) {
            AlertUtil.showWarningMessage("Information", "Veuillez d'abord choisir une formation");
            return;
        }
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FormationExcel formationExcel = new FormationExcel();
                    formationExcel.printFeuillePresence(formation);
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
