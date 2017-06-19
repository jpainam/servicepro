package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.util.FXMLView;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by JP on 6/9/2017.
 */
public class AccueilController implements Initializable, Controller {
    public StackPane content;
    public ImageView cfaoLogo;
    public ProgressBar progressBar;
    public StackPane shortcutContent;
    public StackPane notificationContent;
    public Button exitButton;
    public NotificationPane notificationPane;
    public HBox highlightPane;
    public StackPane notificationStack;
    public Pane headerPane;
    public BreadCrumbBar breadCrumb;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            cfaoLogo.setImage(new Image(new FileInputStream("src/main/resources/images/logo.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GlyphsDude.setIcon(exitButton, FontAwesomeIcon.SIGN_OUT);
        StackPane pane1 = new StackPane();
        notificationPane = new NotificationPane(pane1);
        notificationPane.setText("Notification Panel");
        notificationPane.getActions().addAll(new Action("Cacher/Hide", ae -> {
            notificationPane.hide();
        }));
       notificationStack.getChildren().add(notificationPane);

        ListSelectionView<String> view = new ListSelectionView<>();
        view.getSourceItems().add("One");
        view.getTargetItems().add("Four");

        content.getChildren().setAll(view);
        
    }

    public void displayCivilite(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {
            @Override public Void call() {
                 final int max = 1000000;
                for (int i=1; i<=max; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        StageManager.loadContent(FXMLView.PERSONNE.getFXMLFile());
    }

    public void add(ActionEvent actionEvent) {
    }

    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public Node getHighlightPane() {
        return highlightPane;
    }

    @Override
    public NotificationPane getNotificationPane() {
        return notificationPane;
    }

    public void openParameterScene(int activeTab){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent parameterPane = loader.load(new FileInputStream(FXMLView.PARAMETRE.getFXMLFile()));
            ParametreController parametreController = loader.getController();
            parametreController.setActiveTab(activeTab);
            setContent(parameterPane);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void utilisateurAction(ActionEvent actionEvent) {
       openParameterScene(ParametreController.TAB_UTILISATEUR);
    }

    public void profilAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_PROFIL);
    }

    public void societeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SOCIETE);
    }

    public void niveauEtudeaction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_NIVEAUETUDE);
    }

    public void groupeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_GROUPE);
    }

    public void sectionAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SECTION);
    }

    public void exitAction(ActionEvent actionEvent) {
        if(notificationPane.isShowing()){
            notificationPane.hide();
        }else {
            notificationPane.show();
        }
    }

    public void showAction(ActionEvent actionEvent) {
        StageManager.loadContent(FXMLView.COMPETENCE.getFXMLFile());
    }

    public void addAction(ActionEvent actionEvent) {
        StageManager.loadContent(FXMLView.ADDCOMPETENCE.getFXMLFile());
    }

    public void showProfil(ActionEvent actionEvent) {
        StageManager.loadContent(FXMLView.PROFIL.getFXMLFile());    }

    public void showFormation(ActionEvent actionEvent) {

            StageManager.loadContent(FXMLView.FORMATION.getFXMLFile());
    }
}
