package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.reports.ListeInscriptionRapport;
import com.cfao.app.reports.PlanificationModeleRapport;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        GlyphsDude.setIcon(rapportTab, FontAwesomeIcon.CALENDAR);
        GlyphsDude.setIcon(listeInscription, FontAwesomeIcon.FILE_EXCEL_ALT);
        GlyphsDude.setIcon(modelePlanification, FontAwesomeIcon.FILE_EXCEL_ALT);
        //GlyphsDude.setIcon(pivotTableTest, FontAwesomeIcon.FILE_EXCEL_ALT);
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
                    ExcelFormation excelFormation = new ExcelFormation();
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
}
