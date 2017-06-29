package com.cfao.app;

import com.cfao.app.controllers.*;
import com.cfao.app.util.FXMLView;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by JP on 6/26/2017.
 */
public class Module {
    private Module() {
    }

    public static Pane formation = null;
    public static Pane civilite = null;
    public static Pane competence = null;
    public static Pane groupe = null;
    public static Pane profil = null;
    public static Pane parametre = null;
    public static Pane importPeronne = null;

    public static void setFormation(StackPane container) {
        try {
            if (formation == null) {
                formation = FXMLLoader.load(Module.class.getResource(FXMLView.FORMATION.getFXMLFile()));
                ;
            }
            container.getChildren().clear();
            container.getChildren().add(formation);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static void setCivilite(StackPane container) {
        try {
            if (civilite == null) {
                civilite = FXMLLoader.load(Module.class.getResource(FXMLView.CIVILITE.getFXMLFile()));
                ;
            }
            container.getChildren().clear();
            container.getChildren().add(civilite);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setCompetence(StackPane container) {
        try {
            if (competence == null) {
                competence = FXMLLoader.load(Module.class.getResource(FXMLView.COMPETENCE.getFXMLFile()));
                ;
            }
            container.getChildren().clear();
            container.getChildren().add(competence);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void setGroupe(StackPane container) {
        try {
            if (groupe == null) {
                groupe = FXMLLoader.load(Module.class.getResource(FXMLView.GROUPE.getFXMLFile()));
                ;
            }
            container.getChildren().clear();
            container.getChildren().add(groupe);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setProfil(StackPane container) {
        try {
            if (profil == null) {
                profil = FXMLLoader.load(Module.class.getResource(FXMLView.PROFIL.getFXMLFile()));
            }
            container.getChildren().clear();
            container.getChildren().add(profil);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setParametre(StackPane container) {
        try {
            if (parametre == null) {
                parametre = FXMLLoader.load(Module.class.getResource(FXMLView.PARAMETRE.getFXMLFile()));
                ;
            }
            container.getChildren().clear();
            container.getChildren().add(parametre);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setImportPersonne(StackPane container) {
        try {
            if (importPeronne == null) {
                String fxmlFile = "/views/civilite/import.fxml";
                importPeronne = FXMLLoader.load(Module.class.getResource(fxmlFile));
            }
            container.getChildren().clear();
            container.getChildren().add(importPeronne);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
