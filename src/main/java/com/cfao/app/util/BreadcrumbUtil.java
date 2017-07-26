package com.cfao.app.util;

import com.cfao.app.Module;
import com.cfao.app.StageManager;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.BreadCrumbBar;

import java.util.HashMap;

/**
 * Created by JP on 6/28/2017.
 */
public class BreadcrumbUtil extends BreadCrumbBar<BreadCrumbItem> {
    public HashMap<String, TreeItem<BreadCrumbItem>> items = new HashMap<>();

    public BreadcrumbUtil() {
        super();
        this.setAutoNavigationEnabled(true);
        setOnCrumbAction(new EventHandler<BreadCrumbActionEvent<BreadCrumbItem>>() {
            @Override
            public void handle(BreadCrumbActionEvent<BreadCrumbItem> event) {
                TreeItem<BreadCrumbItem> treeItem = event.getSelectedCrumb();
                BreadCrumbItem item = treeItem.getValue();
                if(item.getFxmlFile() != null) {
                    StageManager.loadContent(item.getFxmlFile());
                }
            }
        });
        TreeItem<BreadCrumbItem> root = new TreeItem<>( new BreadCrumbItem("Accueil", "/views/accueil/accueil.fxml"));
        items.put("accueil", root);
        TreeItem<BreadCrumbItem> personne = new TreeItem<>(new BreadCrumbItem("Civilité", "/views/civilite/civilite.fxml"));
        items.put("personne", personne);
        TreeItem<BreadCrumbItem> addpersonne = new TreeItem<>(new BreadCrumbItem("Ajouter"));
        items.put("addpersonne", addpersonne);
        TreeItem<BreadCrumbItem> editpersonne = new TreeItem<>(new BreadCrumbItem("Modifier"));
        items.put("edidpersonne", editpersonne);
        personne.getChildren().addAll(addpersonne, editpersonne);

        TreeItem<BreadCrumbItem> competence = new TreeItem<>(new BreadCrumbItem("Compétence", "/views/competence/competence.fxml"));
        items.put("competence", competence);
        TreeItem<BreadCrumbItem> addcompetence = new TreeItem<>(new BreadCrumbItem("Ajouter"));
        items.put("addcompetence", addcompetence);
        TreeItem<BreadCrumbItem> editcompetence = new TreeItem<>(new BreadCrumbItem("Modifier"));
        items.put("editcompetence", editcompetence);
        competence.getChildren().addAll(addcompetence, editcompetence);

        TreeItem<BreadCrumbItem> formation = new TreeItem<>(new BreadCrumbItem("Formation", "/views/formation/formation.fxml"));
        items.put("formation", formation);
        TreeItem<BreadCrumbItem> addformation = new TreeItem<>(new BreadCrumbItem("Ajouter"));
        items.put("addformation", addformation);
        TreeItem<BreadCrumbItem> editformation = new TreeItem<>(new BreadCrumbItem("Modifier"));
        items.put("editformation", editformation);
        formation.getChildren().addAll(addformation, editformation);

        TreeItem<BreadCrumbItem> profil = new TreeItem<>(new BreadCrumbItem("Profil", "/views/profil/profil.fxml"));
        items.put("profil", profil);
        root.getChildren().setAll(personne, competence, formation, profil);


        setSelectedCrumb(root);
    }

    public void setSelectedCrumb(String key) {
        TreeItem<BreadCrumbItem> leaf = items.get(key);
        if(leaf != null) {
            this.setSelectedCrumb(leaf);
        }
    }
}

class BreadCrumbItem{
    public  String libelle;
    public String fxmlFile;

    public String getFxmlFile() {
        return fxmlFile;
    }

    public void setFxmlFile(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public BreadCrumbItem(String libelle, String fxmlFile) {
        this.libelle = libelle;
        this.fxmlFile = fxmlFile;
    }
    public BreadCrumbItem(String libelle) {
        this(libelle, null);
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
