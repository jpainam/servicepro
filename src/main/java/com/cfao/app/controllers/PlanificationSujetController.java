package com.cfao.app.controllers;

import com.cfao.app.beans.Sujet;
import com.cfao.app.model.Model;
import com.cfao.app.model.SujetModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class PlanificationSujetController extends AnchorPane implements Initializable {

    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnPrint;
    public Button btnValider;
    public TextField txtsujet;

    public TableView<Sujet> sujetPlanificationTable;
    public TableColumn<Sujet, Integer> columnNumero;
    public TableColumn<Sujet, String> columnLibelle;

    public Sujet sujet = null;
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;

    public PlanificationSujetController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/sujet.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents(){
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.print(btnPrint);
        ButtonUtil.plusIcon(btnNouveau);

        ServiceproUtil.setEditable(false, txtsujet);
        buildTableSujet();
    }

    private void buildTableSujet() {
        columnNumero.setCellValueFactory(new PropertyValueFactory<>("idsujet"));
        columnLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        Task<ObservableList<Sujet>> task = new Task<ObservableList<Sujet>>() {
            @Override
            protected ObservableList<Sujet> call() throws Exception {
                return FXCollections.observableList(new com.cfao.app.model.Model<Sujet>("Sujet").select());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(()->{
                sujetPlanificationTable.setItems(task.getValue());
                sujetPlanificationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    changementSujet(newValue);
                });
            });

        });
    }

    private void changementSujet(Sujet newValue) {
        sujet = newValue;
        ServiceproUtil.setEditable(false, txtsujet);
        ServiceproUtil.setDisable(true, btnValider);
        txtsujet.setText(newValue.getLibelle());
    }

    public void modifierAction(javafx.event.ActionEvent event){
        if (sujet != null) {
            ServiceproUtil.setEditable(true, txtsujet);
            ServiceproUtil.setDisable(false, btnValider);
            stateBtnModifier = 1;
            stateBtnNouveau = 0;
        }
    }


    public void supprimerAction(javafx.event.ActionEvent event){
        if(sujet != null){
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<Sujet>().delete(sujet);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(evt -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Suppression OK");
                    sujetPlanificationTable.getItems().removeAll(sujet);
                } else {
                    ServiceproUtil.notify("Erreur de Suppression");
                }
            });
        }
    }

    public void nouveauAction(javafx.event.ActionEvent event){
        ServiceproUtil.setEditable(true, txtsujet);
        txtsujet.setText("");
        ServiceproUtil.setDisable(false, btnValider);
        stateBtnNouveau = 1;
        stateBtnModifier = 0;
    }
    public void printAction(javafx.event.ActionEvent event){}

    public void validerAction(javafx.event.ActionEvent event){
        if(!txtsujet.getText().isEmpty()) {
            /**
             * Test Existence du sujet
             * **/
            Task<Boolean> tsk = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new SujetModel().exist(txtsujet.getText());
                }
            };
            new Thread(tsk).start();

            tsk.setOnSucceeded(event1 -> {
                if(stateBtnNouveau == 1) {
                    stateBtnNouveau = 0;
                    if (tsk.getValue()) {
                        Sujet sujet = new Sujet();
                        sujet.setLibelle(txtsujet.getText());
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                return new Model<Sujet>().save(sujet);
                            }
                        };
                        new Thread(task).start();
                        task.setOnSucceeded(evt -> {
                            if (task.getValue()) {
                                ServiceproUtil.notify("Ajout OK");
                                sujetPlanificationTable.getItems().add(sujet);
                            } else {
                                ServiceproUtil.notify("Erreur d'ajout");
                            }
                            Platform.runLater(() -> {
                                ServiceproUtil.setDisable(true, btnValider);
                                ServiceproUtil.setEditable(false, txtsujet);
                            });
                        });
                    } else
                        ServiceproUtil.notify("Ce Sujet Existe déjà!");
                }else if(stateBtnModifier == 1){
                    stateBtnModifier = 0;
                    if (tsk.getValue()) {
                        sujet.setLibelle(txtsujet.getText());
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                return new Model<Sujet>().update(sujet);
                            }
                        };
                        new Thread(task).start();
                        task.setOnSucceeded(evt -> {
                            if (task.getValue()) {
                                ServiceproUtil.notify("Modification OK");
                                sujetPlanificationTable.refresh();

                            } else {
                                ServiceproUtil.notify("Erreur Modification");
                            }
                            Platform.runLater(() -> {
                                ServiceproUtil.setDisable(true, btnValider);
                                ServiceproUtil.setEditable(false, txtsujet);
                            });
                        });
                    } else
                        ServiceproUtil.notify("Ce Sujet Existe déjà!");
                }
            });


        }
    }

}