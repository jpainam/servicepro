package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Groupe;
import com.cfao.app.beans.Section;
import com.cfao.app.model.GroupeModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.SectionModel;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class SectionController extends Controller implements Initializable{
    public HBox researchBox;
    public JFXButton btnNouveau;
    public JFXButton btnModifier;
    public JFXButton btnSupprimer;
    public JFXButton btnValider;
    public TextField txtLibelle;
    public JFXButton btnAnnuler;
    public ListView<Section> sectionListView;
    private SearchBox searchBox = new SearchBox();
    private MultipleSelectionModel sectionListModel;

    public SectionController(){
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(FXMLView.SECTION.getFXMLFile()));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createSectionList();
        sectionListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sectionListModel = sectionListView.getSelectionModel();
        sectionListModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addListenerToRow();
        });
        txtLibelle.setEditable(false);
    }

    private void addListenerToRow() {
        if(sectionListModel.getSelectedItem() != null){
            Section section = (Section) sectionListModel.getSelectedItem();
            txtLibelle.setText(section.getLibelle());
        }
    }

    private void createSectionList() {
        Label label = new Label("Sections : ");
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        researchBox.setSpacing(10);
        HBox.setMargin(researchBox, new Insets(10, 10, 10, 10));
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().addAll(label, searchBox);
        SectionModel sectionModel = new SectionModel(Model.getBeansClass("Section"));
        Task<ObservableList<Section>> task = new Task<ObservableList<Section>>() {
            @Override
            protected ObservableList<Section> call() throws Exception {

                return FXCollections.observableArrayList(sectionModel.getList());
            }
        };

        new Thread(task).start();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            sectionListView.setItems(task.getValue());
        });
    }

    public void nouveauAction(ActionEvent actionEvent) {
        txtLibelle.setEditable(true);
    }


    public void supprimerAction(ActionEvent actionEvent) {
    }


    public void validerAction(ActionEvent actionEvent) {
        txtLibelle.setEditable(false);
    }

    public void annulerAction(ActionEvent actionEvent) {
        txtLibelle.setEditable(false);
    }

    public void editSection(ActionEvent actionEvent) {
        txtLibelle.setText("");
        txtLibelle.setEditable(true);
    }
}
