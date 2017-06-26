package com.cfao.app;

import com.cfao.app.controllers.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Created by JP on 6/26/2017.
 */
public class Module {
    private Module(){}
    private static FormationController formation;
    private static CiviliteController civilite;
    private static CompetenceController competence;
    private static GroupeController groupe;
    private static ProfilController profil;
    private static ParametreController parametre;

    public static void setFormation(AnchorPane container){
        if(formation == null){
            formation = new FormationController();
        }
        container.getChildren().clear();
        container.getChildren().add(formation);
    }

    public static void setCivilite(AnchorPane container){
        if(civilite == null){
            civilite = new CiviliteController();
        }
        container.getChildren().clear();
        container.getChildren().add(civilite);
    }

    public static void setCompetence(AnchorPane container){
        if(competence == null){
            competence = new CompetenceController();
        }
        container.getChildren().clear();
        container.getChildren().add(competence);
    }

    public static void setGroupe(AnchorPane container){
        if(groupe == null){
            groupe = new GroupeController();
        }
        container.getChildren().clear();
        container.getChildren().add(groupe);
    }

    public static void setProfil(AnchorPane container){
        if(profil == null){
            profil = new ProfilController();
        }
        container.getChildren().clear();
        container.getChildren().add(profil);
    }
    public static void setParametre(AnchorPane container){
        if(parametre == null){
            parametre = new ParametreController();
        }
        container.getChildren().clear();
        container.getChildren().add(parametre);
    }

}
