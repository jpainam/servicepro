package com.cfao.app.util;

import com.cfao.app.StageManager;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.BreadCrumbBar;

import java.util.HashMap;

/**
 * Created by JP on 6/28/2017.
 */
public class BreadcrumbUtil extends BreadCrumbBar<BreadcrumbUtil.BreadCrumbItem> {
    public HashMap<String, TreeItem<BreadCrumbItem>> items = new HashMap<>();

    public BreadcrumbUtil() {
        super();
        this.setAutoNavigationEnabled(true);
        setOnCrumbAction(event -> {
            TreeItem<BreadCrumbItem> treeItem = event.getSelectedCrumb();
            BreadCrumbItem item = treeItem.getValue();
            if(item.getFxmlFile() != null) {
                StageManager.loadContent(item.getFxmlFile());
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

        TreeItem<BreadCrumbItem> qcm = new TreeItem<>(new BreadCrumbItem("Test", "/views/qcm/qcm.fxml"));
        items.put("qcm", qcm);

        root.getChildren().setAll(personne, competence, formation, profil, qcm);


        setSelectedCrumb(root);
    }

    public void setSelectedCrumb(String key) {
        TreeItem<BreadCrumbItem> leaf = items.get(key);
        if(leaf != null) {
            this.setSelectedCrumb(leaf);
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

}