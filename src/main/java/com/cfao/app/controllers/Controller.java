package com.cfao.app.controllers;

import com.cfao.app.beans.Societe;
import com.cfao.app.model.Model;
import com.cfao.app.util.SearchFieldClassTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import org.controlsfx.control.textfield.CustomTextField;



/**
 * Created by JP on 6/15/2017.
 */
public class Controller {
    /*protected void buildTable(TableView tableView, Model model, \, Comparable<Object> comparable, CustomTextField fieldSearch) {
        Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
            @Override
            protected ObservableList<Societe> call() throws Exception {
                return FXCollections.observableArrayList(model.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            FilteredList<> filteredList = new FilteredList<Societe>(task.getValue(), p -> true);
            fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(societe -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Comparer les champs dans la classe Societe
                    String valueCompare = newValue.toLowerCase();
                    if (societe.getNom().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else if (societe.getAdresse() != null && societe.getAdresse().toLowerCase().contains(valueCompare)) {
                        SearchFieldClassTool.updateStateClass(fieldSearch, false);
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Societe> sortedList = new SortedList<Societe>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }*/
}
