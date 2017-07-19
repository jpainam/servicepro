package com.cfao.app.controllers;

import com.cfao.app.beans.Niveau;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.Model;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by armel on 10/07/2017.
 */
public class DialogProfilController implements Initializable {

    @FXML
    public ComboBox<Profil> comboProfil;
    @FXML
    public ComboBox<Niveau> comboLevel;

    public ObservableMap<String, ObservableList> map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        comboProfil.setItems(map.get("profil"));
        comboLevel.setItems(map.get("niveau"));
    }

    public void setData(){
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                map = FXCollections.observableHashMap();
                map.put("profil", FXCollections.observableList((new Model<Profil>("Profil")).getList()));
                map.put("niveau", FXCollections.observableList((new Model<Niveau>("Niveau")).getList()));
                return map;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            Platform.runLater(()->{
                map = task.getValue();
            });

        });
    }

    public ObservableMap<String, ObservableList> getMapData(){
        return map;
    }

}
