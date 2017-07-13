package com.cfao.app.controllers;

import com.cfao.app.beans.Groupe;
import com.cfao.app.model.GroupeModel;
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
public class GroupeController implements Initializable {
    public HBox researchBox;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;

    public TextField txtLibelle;
    public Button btnAnnuler;
    public ListView<Groupe> groupeListView;
    private SearchBox searchBox = new SearchBox();
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    GroupeModel groupeModel = new GroupeModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiComponents();
    }

    private void initiComponents() {
        Label label = new Label("Groupes : ");
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().addAll(label, searchBox);
        buildListView();
    }

    private void buildListView() {
        ServiceproUtil.setEditable(false, txtLibelle);
        groupeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Groupe>() {
            @Override
            public void changed(ObservableValue<? extends Groupe> observable, Groupe oldValue, Groupe newValue) {
                if(groupeListView.getSelectionModel().getSelectedItem() != null){
                    Groupe groupe = groupeListView.getSelectionModel().getSelectedItem();
                    txtLibelle.setText(groupe.getLibelle());
                }
            }
        });
        Task<ObservableList<Groupe>> task = new Task<ObservableList<Groupe>>() {
            @Override
            protected ObservableList<Groupe> call() throws Exception {
                return FXCollections.observableArrayList(groupeModel.getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            groupeListView.setItems(task.getValue());
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
                Groupe groupe = new Groupe();
                groupe.setLibelle(txtLibelle.getText());
                if (groupeModel.save(groupe)) {
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
        if (groupeListView.getSelectionModel().getSelectedItem() != null) {
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
                Groupe groupe = groupeListView.getSelectionModel().getSelectedItem();
                groupe.setLibelle(txtLibelle.getText());
                if (groupeModel.update(groupe)) {
                    ServiceproUtil.notify("Enregistrement OK");
                } else {
                    ServiceproUtil.notify("Une erreur est survenue");
                }
                ServiceproUtil.emptyFields(txtLibelle);
                buildListView();
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le groupe à modifier");
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (groupeListView.getSelectionModel().getSelectedItem() != null) {
            Groupe groupe = groupeListView.getSelectionModel().getSelectedItem();
            boolean okay = AlertUtil.showConfirmationMessage("Suppression", "Etes vous sûr de vouloir supprimer " + groupe);
            if (okay) {
                if (groupeModel.delete(groupe)) {
                    ServiceproUtil.notify("Suppression OK");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
                buildListView();
                ServiceproUtil.emptyFields(txtLibelle);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le Groupe à supprimer");
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
