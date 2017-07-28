package com.cfao.app.controllers;

import com.cfao.app.beans.Niveau;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.ProfilPersonne;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by armel on 10/07/2017.
 */
public class DialogProfilController extends AnchorPane implements Initializable {

    @FXML
    public ComboBox<Profil> comboProfil;
    @FXML
    public ComboBox<Niveau> comboLevel;

    public Personne personne;
    public ObservableMap<String, ObservableList> map;

    public DialogProfilController(Personne personne){
        this();
        this.personne = personne;

    }
    public DialogProfilController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogProfil.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                map = FXCollections.observableHashMap();
                map.put("profil", FXCollections.observableList((new Model<Profil>("Profil")).getList()));
                map.put("niveau", FXCollections.observableList((new Model<Niveau>("Niveau")).getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(()->{
                comboProfil.setItems(map.get("profil"));
                comboLevel.setItems(map.get("niveau"));
            });

        });
    }
    public ProfilPersonne getData(){
        if(comboLevel.getValue() != null && comboProfil.getValue() != null) {
            ProfilPersonne profilPersonne = new ProfilPersonne();
            profilPersonne.setNiveau(comboLevel.getSelectionModel().getSelectedItem());
            profilPersonne.setProfil(comboProfil.getSelectionModel().getSelectedItem());
            if(personne != null) {
                profilPersonne.setPersonne(personne);
            }
            return profilPersonne;
        }
        return null;
    }

}
