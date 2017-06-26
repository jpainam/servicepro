package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.util.FXMLView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ParametreController extends Controller implements Initializable{
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
    private SingleSelectionModel<Tab> singleSelectionModel;

    public ParametreController(){
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(FXMLView.PARAMETRE.getFXMLFile()));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        setTabIcon();
        tabSociete.setContent(new SocieteController());
        //tabUtilsateur.setContent(getTabContent(FXMLView.USER.getFXMLFile()));
        tabGroupe.setContent(new GroupeController());
        tabSection.setContent(new SectionController());

        singleSelectionModel = tabPane.getSelectionModel();

        singleSelectionModel.select(getActiveTab());
    }

    public Parent getTabContent(String fxmlFile){
        try {
            Parent tab = FXMLLoader.load(getClass().getResource(fxmlFile));
            return tab;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public void setTabIcon(){
        tabUtilsateur.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("utilisateur.icon")));
        tabProfil.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("profil.icon")));
        tabNiveauetude.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("niveauetude.icon")));
        tabSection.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("section.icon")));
        tabSociete.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("societe.icon")));
        tabGroupe.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("groupe.icon")));

    }
    public void setActiveTab(int activeTab){
        this.activeTab = activeTab;
    }

    public Tab getActiveTab(){
        switch (activeTab){
            case TAB_UTILISATEUR: return tabUtilsateur;
            case TAB_GROUPE: return tabGroupe;
            case TAB_NIVEAUETUDE: return tabNiveauetude;
            case TAB_PROFIL: return tabProfil;
            case TAB_SECTION: return tabSection;
            case TAB_SOCIETE: return tabSociete;
        }
        return tabSociete;
    }
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }
}
