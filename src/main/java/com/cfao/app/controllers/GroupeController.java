package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Groupe;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.GroupeModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class GroupeController  implements Initializable{
    public HBox researchBox;
    public JFXButton btnNouveau;
    public JFXButton btnModifier;
    public JFXButton btnSupprimer;
    public JFXButton btnValider;
    public TextField txtLibelle;
    public JFXButton btnAnnuler;
    public ListView groupeListView;
    private SearchBox searchBox = new SearchBox();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createGroupeList();
    }

    private void createGroupeList() {
        Label label = new Label("Groupes : ");
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        researchBox.setSpacing(10);
        HBox.setMargin(researchBox, new Insets(10, 10, 10, 10));
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().addAll(label, searchBox);
        GroupeModel groupeModel = new GroupeModel(Model.getBeansClass("Groupe"));
        groupeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Task<ObservableList<Groupe>> task = new Task<ObservableList<Groupe>>() {
            @Override
            protected ObservableList<Groupe> call() throws Exception {

                return FXCollections.observableArrayList(groupeModel.getList());
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(event -> {
            groupeListView.setItems(task.getValue());
        });
    }

    public void nouveauAction(ActionEvent actionEvent) {
    }

    public void editGroupe(ActionEvent actionEvent) {
    }

    public void supprimerAction(ActionEvent actionEvent) {
    }


    public void validerAction(ActionEvent actionEvent) {
    }

    public void annulerAction(ActionEvent actionEvent) {
    }
}
