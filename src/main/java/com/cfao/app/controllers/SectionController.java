package com.cfao.app.controllers;

import com.cfao.app.beans.Section;
import com.cfao.app.model.SectionModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class SectionController implements Initializable {
    public HBox researchBox;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;

    public TextField txtLibelle;
    public Button btnAnnuler;
    public ListView<Section> sectionListView;
    private SearchBox searchBox = new SearchBox();
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    SectionModel sectionModel = new SectionModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiComponents();
    }

    private void initiComponents() {
        Label label = new Label("Sections : ");
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().addAll(label, searchBox);
        buildListView();
    }

    private void buildListView() {
        ServiceproUtil.setEditable(false, txtLibelle);
        sectionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Section>() {
            @Override
            public void changed(ObservableValue<? extends Section> observable, Section oldValue, Section newValue) {
                if(sectionListView.getSelectionModel().getSelectedItem() != null){
                    Section section = sectionListView.getSelectionModel().getSelectedItem();
                    txtLibelle.setText(section.getLibelle());
                }
            }
        });
        Task<ObservableList<Section>> task = new Task<ObservableList<Section>>() {
            @Override
            protected ObservableList<Section> call() throws Exception {
                return FXCollections.observableArrayList(sectionModel.getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            sectionListView.setItems(task.getValue());
        });
    }

    public void clickNouveau(ActionEvent actionEvent) {
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        ServiceproUtil.setDisable(true, btnModifier);
        if (stateBtnNouveau == 0) {
            ServiceproUtil.emptyFields(txtLibelle);
            ServiceproUtil.setEditable(true, txtLibelle);
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            stateBtnNouveau = 1;
        } else {
            stateBtnNouveau = 0;
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
            ServiceproUtil.setDisable(false, btnModifier);
            if (!txtLibelle.getText().isEmpty()) {
                Section section = new Section();
                section.setLibelle(txtLibelle.getText());
                if (sectionModel.save(section)) {
                    ServiceproUtil.notify("Enregistrement OK");
                } else {
                    ServiceproUtil.notify("Une erreur est survenue");
                }
                ServiceproUtil.emptyFields(txtLibelle);
                ServiceproUtil.setEditable(false, txtLibelle);
            }
            buildListView();
        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (sectionListView.getSelectionModel().getSelectedItem() != null) {
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
            ServiceproUtil.setDisable(true, btnNouveau);
            if (stateBtnModifier == 0) {
                stateBtnModifier = 1;
                ServiceproUtil.setEditable(true, txtLibelle);
                btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            } else {
                stateBtnModifier = 0;
                btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
                ServiceproUtil.setDisable(false, btnNouveau);
                ServiceproUtil.setEditable(false, txtLibelle);
                Section section = sectionListView.getSelectionModel().getSelectedItem();
                section.setLibelle(txtLibelle.getText());
                if (sectionModel.update(section)) {
                    ServiceproUtil.notify("Enregistrement OK");
                } else {
                    ServiceproUtil.notify("Une erreur est survenue");
                }
                ServiceproUtil.emptyFields(txtLibelle);
                buildListView();
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la section à modifier");
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (sectionListView.getSelectionModel().getSelectedItem() != null) {
            Section section = sectionListView.getSelectionModel().getSelectedItem();
            boolean okay = AlertUtil.showConfirmationMessage("Suppression", "Etes vous sûr de vouloir supprimer " + section);
            if (okay) {
                if (sectionModel.delete(section)) {
                    ServiceproUtil.notify("Suppression OK");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
                buildListView();
                ServiceproUtil.emptyFields(txtLibelle);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le section à supprimer");
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
        ServiceproUtil.emptyFields(txtLibelle);
        ServiceproUtil.setEditable(false, txtLibelle);
        ServiceproUtil.setDisable(false, btnModifier, btnNouveau);
        stateBtnModifier = 0;
        stateBtnNouveau = 0;
    }
}
