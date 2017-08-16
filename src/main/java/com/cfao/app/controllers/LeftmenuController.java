package com.cfao.app.controllers;


import com.cfao.app.StageManager;
import com.cfao.app.util.AlertUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Communication on 13/06/2017.
 */
public class LeftmenuController implements Initializable{

    public ToggleButton btnPersonne;
    public ToggleButton btnCompetence;
    public ToggleButton btnProfil;
    public ToggleButton btnTest;
    public ToggleButton btnRapport;
    public ToggleButton btnParametre;
    public ToggleButton btnFormation;
    public final ToggleGroup toggleGroup = new ToggleGroup();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLeftMenuSettings();
        //btnPersonne.setUserData("/views/civilite/civilite.fxml");
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(oldValue != null){
                    ((FontAwesomeIconView)((ToggleButton)oldValue).getGraphic()).setFill(Color.ROYALBLUE);
                }
                if(newValue == null){
                    oldValue.setSelected(true);
                }else{
                    ((FontAwesomeIconView)((ToggleButton)newValue).getGraphic()).setFill(Color.WHITE);
                    //Resolu avec les onAction sur le toggleButton
                    //StageManager.loadContent(toggleGroup.getSelectedToggle().getUserData().toString());
                }
            }
        });
    }
    public void setLeftMenuSettings() {
        setLeftMenuSetting(btnPersonne, FontAwesomeIcon.USERS);
        setLeftMenuSetting(btnCompetence, FontAwesomeIcon.SLACK);
        setLeftMenuSetting(btnParametre, FontAwesomeIcon.GEAR);
        setLeftMenuSetting(btnFormation, FontAwesomeIcon.GRADUATION_CAP);
        setLeftMenuSetting(btnRapport, FontAwesomeIcon.FILES_ALT);
        setLeftMenuSetting(btnTest, FontAwesomeIcon.BALANCE_SCALE);
        setLeftMenuSetting(btnProfil, FontAwesomeIcon.TAGS);
    }
    public void setLeftMenuSetting(ToggleButton button, FontAwesomeIcon fontAwesomeIcon) {
        button.setToggleGroup(toggleGroup);
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setGlyphSize(40);
        icon.setFill(Color.ROYALBLUE);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(icon);
    }
    public void personneAction(ActionEvent event) {
        StageManager.setSelectedCrumb("personne");
        StageManager.loadContent("/views/civilite/civilite.fxml");
    }
    public void formationAction(ActionEvent event) {
        StageManager.setSelectedCrumb("formation");
        StageManager.loadContent("/views/formation/formation.fxml");
    }
    public void competenceAction(ActionEvent event) {
        StageManager.setSelectedCrumb("competence");
        StageManager.loadContent("/views/competence/competence.fxml");
    }
    public void testAction(ActionEvent event) {
        StageManager.setSelectedCrumb("qcm");
        StageManager.loadContent("/views/qcm/qcm.fxml");
    }
    public void rapportAction(ActionEvent event) {
        StageManager.loadContent("/views/rapport/rapport.fxml");
    }

    public void parametreAction(ActionEvent event) {
        try {
            ParametreController parametreController = new ParametreController(ParametreController.TAB_SECTION);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/parametre/parametre.fxml"));
            loader.setController(parametreController);
            StageManager.getContent().getChildren().setAll((Node) loader.load());
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    public void profilAction(ActionEvent event) {
        StageManager.setSelectedCrumb("profil");
        StageManager.loadContent("/views/profil/profil.fxml");
    }
}