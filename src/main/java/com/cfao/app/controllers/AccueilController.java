package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.reports.PrintCivilite;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/21/2017.
 */
public class AccueilController implements Initializable {


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
    }
    private void notificationUpdate() {
        if(LoginController.serviceNotification != null)
            LoginController.serviceNotification.setOnSucceeded(event -> {

                ArrayList<Map<String, String>> mapArray = LoginController.serviceNotification.getValue();
                if(mapArray.size() > 0) {
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


}
