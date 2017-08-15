package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.model.FormationModel;
import com.cfao.app.reports.PrintCivilite;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/14/2017.
 */
public class AccueilProfilController extends AnchorPane implements Initializable {
    private Personne personne;
    public Button btnPrintProfil;
    public Label lblCompetence;
    public Label lblCertifiee;
    public Label lblAcertifier;
    public Label lblEncours;
    public Label lblNom;
    public Label lblProfil;
    public ImageView photoView;
    public Label lblFormationSouhaitee;
    public Label lblFormationSuivie;
    public Label lblTest;
    public Label lblEmail;
    public CivilitePhoto civilitePhoto = new CivilitePhoto();


    public AccueilProfilController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accueil/profil.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        /**
         * cir2.setFill(new ImagePattern(im));
         cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
         */
        photoView.setClip(new Circle(50, 50, 50));
        ButtonUtil.print(btnPrintProfil);
        GlyphsDude.setIcon(lblNom, FontAwesomeIcon.USER);
        GlyphsDude.setIcon(lblEmail, FontAwesomeIcon.AT);
        GlyphsDude.setIcon(lblProfil, FontAwesomeIcon.TAGS);
    }

    private void initComponents() {

    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void buildProfilDetails() {
        if (personne != null) {
            lblNom.setText(personne.getNom() + " " + personne.getPrenom());
            if (personne.getProfilPersonnes().size() > 0) {
                lblProfil.setText(personne.getProfilPersonnes().get(0).getProfil().getLibelle());
            } else {
                lblProfil.setText("Aucun profil");
            }
            lblEmail.setText(personne.getEmail());
            lblFormationSuivie.setText(personne.getFormationPersonnes().size() + "");
            lblTest.setText(personne.getPersonneQcms().size() + "");
            lblCompetence.setText(personne.getPersonneCompetences().size() + "");
            int certifee, acertifier, encours;
            certifee = acertifier = encours = 0;
            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                if (pc.isEncours()) {
                    encours++;
                } else if (pc.isCertifiee()) {
                    certifee++;
                } else {
                    acertifier++;
                }
            }
            lblCertifiee.setText(certifee + "");
            lblAcertifier.setText(acertifier + "");
            lblEncours.setText(encours + "");
            photoView.setImage(civilitePhoto.getImage(personne));
            // Formation souhaite
            Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
                @Override
                protected ObservableList<Formation> call() throws Exception {
                    return FXCollections.observableArrayList(new FormationModel().getFormationsSouhaitees(personne));
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                lblFormationSouhaitee.setText(task.getValue().size() + "");
            });
            task.setOnFailed(event -> {
                System.err.println(task.getException());
                ServiceproUtil.notify("Erreur dans le thread");
                task.getException().printStackTrace();
            });
        }
    }

    public void printProfilAction(ActionEvent event) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PrintCivilite print = new PrintCivilite();
                if (personne != null) {
                    print.showDetails(personne);
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
