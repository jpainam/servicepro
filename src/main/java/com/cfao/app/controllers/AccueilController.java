package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Planification;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.reports.PrintCivilite;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/21/2017.
 */
public class AccueilController implements Initializable {
    static Logger logger = Logger.getLogger(AccueilController.class);

    public AnchorPane personneStatContent;
    public AccueilPersonneController personneController;

    public Label lblNbCivilite;
    public Label lblCiviliteInfo1;
    public Label lblCiviliteInfo2;
    public Label lblNbFormation;

    public Label lblNbprofil;
    public Label lblFormationInfo2;
    public Label lblFormationInfo1;
    public Label lblFormationInfo3;
    public Label lblFormationInfo4;

    public Label lblNbCompetence;
    public Label lblNbNiveau;
    public Label lblNbTest;
    public VBox vboxPlanification;
    public Label lblNbPlanification;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        personneController = new AccueilPersonneController();
        personneStatContent.getChildren().setAll(personneController);
        notificationUpdate();
        notificationPlanificationUpdate();

        Slider slider = new Slider();
        slider.setMin(0.0);
        slider.setMax(10.0);
        slider.setMinWidth(30);
        slider.setMaxWidth(Double.MAX_VALUE);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(59);
        slider.setBlockIncrement(1.0);
        slider.setId("ControlSlider");
        slider.setEffect(new Lighting());

        HBox.setHgrow(slider, Priority.ALWAYS);
        //setTimeSlider(TimeUnit.MINUTES);
        slider.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //logger.debug("Slider Value:" +slider.getValue());
                //syncSliderToTimeline(slider.getValue());
                System.err.println(slider.getValue());
            }
        });
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 60) return "" + n.intValue();
                Double d = new Double(n / 60);
                return "" + d.intValue();
            }

            @Override
            public Double fromString(String s) {
                return new Double(s);
            }
        });
    }

    private void notificationUpdate() {
        if (LoginController.serviceNotification != null)
            LoginController.serviceNotification.setOnSucceeded(event -> {

                ArrayList<Map<String, String>> mapArray = LoginController.serviceNotification.getValue();
                if (mapArray != null && mapArray.size() > 0) {
                    Map<String, String> mapCivilite = mapArray.get(0);
                    Map<String, String> mapFormation = mapArray.get(1);
                    Map<String, String> mapPlanification = mapArray.get(2);
                    Map<String, String> mapProfil = mapArray.get(3);

                    /**
                     * MISE A JOUR DES LABLES
                     */
                    Platform.runLater(() -> {
                        /** LABELS CIVILITE **/
                        lblNbCivilite.setText(mapCivilite.get("info1"));
                        lblCiviliteInfo1.setText(mapCivilite.get("info2") + " Dossiers Incomplets");
                        lblCiviliteInfo2.setText(mapCivilite.get("info3") + " en Attente de Certification");

                        /** LABELS FORMATIONS **/
                        lblNbFormation.setText(mapFormation.get("info1"));
                        lblFormationInfo1.setText(mapFormation.get("info2") + " terminées / " + mapFormation.get("info3") + " annulées");
                        lblFormationInfo2.setText(mapFormation.get("info3") + " en préparation");

                        /** LABELS Planifications **/
                        lblNbPlanification.setText(mapPlanification.get("info1"));

                        /** LABELS PROFIL **/
                        lblNbprofil.setText(mapProfil.get("info1"));
                        lblNbCompetence.setText(mapProfil.get("nbCompetence") + " Compétence");
                        lblNbNiveau.setText(mapProfil.get("nbNiveau") + " Niveaux");
                        lblNbTest.setText(mapProfil.get("nbTest") + " Tests");
                    });
                }

            });
    }


    private void initComponents() {

    }

    public void printProfilAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PrintCivilite print = new PrintCivilite();
                //if (personne != null) {
                //print.showDetails(personne);
                Personne personne = new PersonneModel().getList().get(0);
                print.printDetails(personne);
                //}
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
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                task.getException().printStackTrace();
                ServiceproUtil.notify("Erreur d'impression");
                StageManager.getProgressBar().progressProperty().unbind();
                StageManager.getProgressBar().setProgress(0);
            }
        });
    }


    public void planificationNotification(MouseEvent mouseEvent) {

    }

    public void profilNotification(MouseEvent mouseEvent) {
        StageManager.loadContent("/views/profil/profil.fxml");
    }

    public void formationNotification(MouseEvent mouseEvent) {
    }

    public void civiliteBoxNotification(MouseEvent mouseEvent) {
        try {
            Dialog dialog = DialogUtil.dialogTemplate("Ok", "Annuler");
            dialog.getDialogPane().setContent(FXMLLoader.load(getClass().getResource("/views/accueil/civiliteBox.fxml")));
            dialog.showAndWait();
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    private void notificationPlanificationUpdate() {
        if (LoginController.servicePlanification != null) {
            LoginController.servicePlanification.setOnSucceeded(event -> {
                ArrayList<ObservableList<Planification>> array = LoginController.servicePlanification.getValue();
                ObservableList<Planification> planif1 = array.get(0);
                ObservableList<Planification> planif2 = array.get(1);
                ObservableList<Planification> planif3 = array.get(2);
                ObservableList<Planification> planif4 = array.get(3);

                if (!planif1.isEmpty()) {
                    for (Planification planification : planif1) {
                        Platform.runLater(() -> {
                            showNotification(planification, "JJ - " + planification.getDuration(), TrayIcon.MessageType.INFO);
                            System.out.println(planification.getSujet().getLibelle());
                        });
                    }
                }

                if (!planif2.isEmpty()) {
                    for (Planification planification : planif2) {
                        Platform.runLater(() -> {
                            showNotification(planification, " Passé de " + planification.getDuration() + "jr(s)", TrayIcon.MessageType.WARNING);
                        });
                    }
                }
                if (!planif3.isEmpty()) {
                    for (Planification planification : planif3) {
                        Platform.runLater(() -> {
                            showNotification(planification, "JJ - " + planification.getDuration(), TrayIcon.MessageType.INFO);
                        });
                    }

                }

                if (!planif4.isEmpty()) {
                    for (Planification planification : planif4) {
                        Platform.runLater(() -> {
                            showNotification(planification, " Passé de " + planification.getDuration() + "jr(s)", TrayIcon.MessageType.WARNING);
                        });
                    }
                }
            });
        }
        LoginController.servicePlanification.setOnFailed(event -> {
            System.out.println(event.getSource().getMessage());
        });
    }

    private void showNotification(Planification p, String sms, TrayIcon.MessageType messageType) {
        Main.trayIcon.displayMessage(
                p.getFormation().getTitre(),
                        "\nSujet : " + p.getSujet() + " (" + sms + ")" +
                "\nTaches : " + p.getTaches(), messageType
        );
    }


}
