package com.cfao.app.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ParametreController implements Initializable{
    public static final int TAB_UTILISATEUR = 1;
    public static final int TAB_PROFIL = 2;
    public static final int TAB_SOCIETE = 3;
    public static final int TAB_NIVEAUETUDE = 4;
    public static final int TAB_SECTION = 5;
    public static final int TAB_GROUPE = 6;



    public Tab tabUtilsateur;
    public Tab tabProfil;
    public Tab tabSociete;
    public Tab tabGroupe;
    public Tab tabNiveauetude;
    public Tab tabSection;
    public TabPane tabPane;
    private int activeTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void setActiveTab(int activeTab){
        this.activeTab = activeTab;
    }
}
