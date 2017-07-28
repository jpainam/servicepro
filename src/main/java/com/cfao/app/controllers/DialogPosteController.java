package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Poste;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by armel on 10/07/2017.
 */
public class DialogPosteController extends AnchorPane implements Initializable {
    public TextField txtPoste;
    public ComboBox<Societe> comboSociete;
    public DatePicker dateTo;
    public DatePicker dateFrom;
    public ObservableList<Societe> data = FXCollections.observableArrayList();

    public Personne personne = null;

    public DialogPosteController(Personne personne) {
        this();
        this.personne = personne;
    }

    public DialogPosteController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogPoste.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        comboSociete.setItems(data);
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
    }

    public Poste getPoste() {
        if (!txtPoste.getText().isEmpty() && comboSociete.getValue() != null) {
            Poste poste = new Poste();
            poste.setTitre(txtPoste.getText());
            poste.setSociete(comboSociete.getSelectionModel().getSelectedItem());
            poste.setDatedebut(Date.valueOf(dateFrom.getValue()));
            poste.setDatefin(Date.valueOf(dateTo.getValue()));
            if(personne != null) {
                poste.setPersonne(personne);
            }
            return poste;
        }
        return null;
    }

    private void setData() {
        Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
            @Override
            protected ObservableList<Societe> call() throws Exception {
                return FXCollections.observableList(new Model<Societe>("Societe").getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            data.setAll(task.getValue());
        });
    }
}
