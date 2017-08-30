package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class FormationImportExcelController implements Initializable {
    static Logger logger = Logger.getLogger(FormationImportExcelController.class);
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


    public FormationImportExcelController() {
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
        int debut = (int)'A';
        for(int i = 0; i < 20; i++){
           data.add(new Contenu(i, (char)(debut + i)));
        }
        comboTache.setItems(data);
        comboResponsable.setItems(data);
        comboValidation.setItems(data);
        comboDocument.setItems(data);
        comboCommentaire.setItems(data);
        comboTiming.setItems(data);
        comboFait.setItems(data);
        comboRemarque.setItems(data);
        comboSujet.setItems(data);
    }

    public boolean getData(){
        return true;
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
        public String toString(){
            return this.display + "";
        }
    }
}
