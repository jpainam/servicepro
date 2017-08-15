package com.cfao.app.controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    public Label lblNbFormateur;
    public Label lblNbprofil;
    public Label lblFormationInfo2;
    public Label lblFormationInfo1;
    public Label lblFormationInfo3;
    public Label lblFormationInfo4;

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

                Map<String, String> mapCivilite = mapArray.get(0);
                Map<String, String> mapFormation = mapArray.get(1);
                Map<String, String> mapFormateur = mapArray.get(2);
                Map<String, String> mapProfil = mapArray.get(3);

                /**
                 * MISE A JOUR DES LABLES
                 */
                Platform.runLater(()->{
                    /** LABELS CIVILITE **/
                    lblNbCivilite.setText(mapCivilite.get("info1"));
                    lblCiviliteInfo1.setText(mapCivilite.get("info2") + " Dossiers Incomplets");
                    lblCiviliteInfo2.setText(mapCivilite.get("info3") + " en Attente de Certification");

                    /** LABELS FORMATIONS **/
                    lblNbFormation.setText(mapFormation.get("info1"));
                    lblFormationInfo1.setText(mapFormation.get("info2") + "terminées / "+mapFormation.get("info3")+ " annulées");
                    lblFormationInfo2.setText(mapFormation.get("info3") + "en préparation ()");

                    /** LABELS FORMATEURS **/
                    lblNbFormateur.setText(mapFormateur.get("info1"));

                    /** LABELS PROFIL **/
                    lblNbprofil.setText(mapProfil.get("info1"));
                });

            });
    }



    private void initComponents() {

    }


}
