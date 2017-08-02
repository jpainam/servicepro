package com.cfao.app;

import com.cfao.app.util.FXMLView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Created by JP on 6/26/2017.
 */
public class Module {
    private Module() {
    }


    public static void setFormation(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.FORMATION.getFXMLFile()));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static void setCivilite(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.CIVILITE.getFXMLFile()));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setCompetence(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.COMPETENCE.getFXMLFile()));

            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void setGroupe(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.GROUPE.getFXMLFile()));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setProfil(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.PROFIL.getFXMLFile()));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void setTest(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource("/views/qcm/qcm.fxml"));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setParametre(AnchorPane container) {
        try {
            Pane pane = FXMLLoader.load(Module.class.getResource(FXMLView.PARAMETRE.getFXMLFile()));

            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setImportPersonne(AnchorPane container) {
        try {
            String fxmlFile = "/views/civilite/import.fxml";
            Pane pane = FXMLLoader.load(Module.class.getResource(fxmlFile));
            container.getChildren().clear();
            container.getChildren().add(pane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
