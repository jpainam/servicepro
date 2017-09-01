package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class FormationImportExcelDialogController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(FormationImportExcelDialogController.class);
    public ComboBox<Contenu> comboTache;
    public ComboBox<Contenu> comboResponsable;
    public ComboBox<Contenu> comboValidation;
    public ComboBox<Contenu> comboDocument;
    public ComboBox<Contenu> comboCommentaire;
    public ComboBox<Contenu> comboTiming;
    public ComboBox<Contenu> comboFait;
    public ComboBox<Contenu> comboRemarque;
    public TextField txtLigne;
    private Formation formation;
    public ComboBox<Contenu> comboSujet;


    public FormationImportExcelDialogController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/importParamDialog.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        ObservableList<Contenu> data = FXCollections.observableArrayList();
        int debut = (int) 'A';
        for (int i = 0; i < 20; i++) {
            data.add(new Contenu(i, (char) (debut + i)));
        }
        comboSujet.setItems(data);
        comboSujet.getSelectionModel().select(data.get(1));

        comboTache.setItems(data);
        comboTache.getSelectionModel().select(data.get(2));

        comboResponsable.setItems(data);
        comboResponsable.getSelectionModel().select(data.get(3));

        comboValidation.setItems(data);
        comboValidation.getSelectionModel().select(data.get(4));

        comboDocument.setItems(data);
        comboDocument.getSelectionModel().select(data.get(5));

        comboCommentaire.setItems(data);
        comboCommentaire.getSelectionModel().select(data.get(6));

        comboTiming.setItems(data);
        comboTiming.getSelectionModel().select(data.get(7));

        comboFait.setItems(data);
        comboFait.getSelectionModel().select(data.get(9));

        comboRemarque.setItems(data);
        comboRemarque.setValue(data.get(10));

        txtLigne.setText(3 + "");

    }

    public HashMap<String, Integer> getData() {
        HashMap<String, Integer> params = new HashMap<>();
        if (comboSujet.getValue() == null)
            return null;
        params.put("SUJET", comboSujet.getValue().getValue());
        params.put("TACHE", comboTache.getValue().getValue());
        params.put("RESPONSABLE", comboRemarque.getValue().getValue());
        params.put("VALIDATION", comboValidation.getValue().getValue());
        params.put("REMARQUE", comboRemarque.getValue().getValue());
        params.put("COMMENTAIRE", comboRemarque.getValue().getValue());
        params.put("DOCUMENT", comboDocument.getValue().getValue());
        params.put("TIMING", comboTiming.getValue().getValue());
        params.put("FAIT", comboFait.getValue().getValue());
        params.put("LIGNE", Integer.parseInt(txtLigne.getText()));
        return params;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    class Contenu {
        private int value;
        private char display;

        Contenu(int val, char display) {
            this.value = val;
            this.display = display;
        }

        public int getValue() {
            return value;
        }

        public char getDisplay() {
            return display;
        }

        @Override
        public String toString() {
            return this.display + "";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Contenu contenu = (Contenu) o;

            return this.value == contenu.value;
        }

        @Override
        public int hashCode() {
            int result = value;
            result = 31 * result + (int) display;
            return result;
        }
    }
}
