package com.cfao.app.controllers;

import com.cfao.app.util.FXMLView;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ParametreController implements Initializable {
    public static final int TAB_UTILISATEUR = 1;
    public static final int TAB_PROFIL = 2;
    public static final int TAB_SOCIETE = 3;
    public static final int TAB_NIVEAUETUDE = 4;
    public static final int TAB_SECTION = 5;
    public static final int TAB_GROUPE = 6;
    public static final int TAB_DOMAINE = 7;
    public static final int TAB_AGENCE = 8;
    public static final int TAB_SITUATIONMATRIMONIALE = 11;
    /**
     * Modele de formation
     */
    public static final int TAB_MODELE = 9;
    public static final int TAB_LIEU = 10;


    public Tab tabUtilsateur;
    //public Tab tabProfil;
    public Tab tabSociete;
    public Tab tabGroupe;
    public Tab tabDomaine;
    public Tab tabAgence;
    public Tab tabSection;
    public Tab tabModele;
    public Tab tabLieu;
    public Tab tabSituationMatrimoniale;

    public TabPane tabPane;
    private int activeTab;
    private SingleSelectionModel<Tab> singleSelectionModel;

    public ParametreController(int activeTab) {
        this.activeTab = activeTab;
    }

    public ParametreController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        setTabIcon();
        tabSociete.setContent(getTabContent(FXMLView.SOCIETE.getFXMLFile()));
        //tabUtilsateur.setContent(getTabContent(FXMLView.USER.getFXMLFile()));
        tabGroupe.setContent(getTabContent(FXMLView.GROUPE.getFXMLFile()));
        tabSection.setContent(getTabContent(FXMLView.SECTION.getFXMLFile()));
        tabDomaine.setContent(getTabContent("/views/domaine/domaine.fxml"));
        tabAgence.setContent(getTabContent("/views/agence/agence.fxml"));
        tabModele.setContent(getTabContent("/views/modele/modele.fxml"));
        tabLieu.setContent(getTabContent("/views/lieu/lieu.fxml"));
        tabSituationMatrimoniale.setContent(getTabContent("/views/situationmatrimoniale/situationmatrimoniale.fxml"));

        singleSelectionModel = tabPane.getSelectionModel();
        singleSelectionModel.select(getActiveTab());
    }

    public Parent getTabContent(String fxmlFile) {
        try {
            Parent tab = FXMLLoader.load(getClass().getResource(fxmlFile));
            return tab;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setTabIcon() {
        //tabUtilsateur.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("utilisateur.icon")));
        //tabProfil.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("profil.icon")));
        //tabNiveauetude.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("niveauetude.icon")));
        tabSection.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("section.icon")));
        tabSociete.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("societe.icon")));
        tabGroupe.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("groupe.icon")));
        tabAgence.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("agence.icon")));
        tabModele.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("modele.icon")));
        tabLieu.setGraphic(buildImage(ResourceBundle.getBundle("Application").getString("lieu.icon")));
        GlyphsDude.setIcon(tabDomaine, FontAwesomeIcon.DASHBOARD);
        GlyphsDude.setIcon(tabSituationMatrimoniale, FontAwesomeIcon.VENUS_MARS);
    }

    public Tab getActiveTab() {
        switch (activeTab) {
            //case TAB_UTILISATEUR: return tabUtilsateur;
            case TAB_GROUPE:
                return tabGroupe;
            //case TAB_NIVEAUETUDE: return tabNiveauetude;
            case TAB_LIEU:
                return tabLieu;
            case TAB_SECTION:
                return tabSection;
            case TAB_SOCIETE:
                return tabSociete;
            case TAB_DOMAINE:
                return tabDomaine;
            case TAB_AGENCE:
                return tabAgence;
            case TAB_MODELE:
                return tabModele;
            case TAB_SITUATIONMATRIMONIALE:
                return tabSituationMatrimoniale;
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
