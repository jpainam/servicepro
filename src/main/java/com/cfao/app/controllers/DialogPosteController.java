package com.cfao.app.controllers;

import com.cfao.app.beans.Societe;
import com.cfao.app.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by armel on 10/07/2017.
 */
public class DialogPosteController implements Initializable{
    public TextField txtPoste;
    public ComboBox<Societe> comboSociete;
    public DatePicker dateTo;
    public DatePicker dateFrom;
    public ObservableList<Societe> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        comboSociete.setItems(data);
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
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
