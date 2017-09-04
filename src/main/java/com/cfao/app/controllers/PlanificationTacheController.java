package com.cfao.app.controllers;

import com.cfao.app.beans.Tache;
import com.cfao.app.model.Model;
import com.cfao.app.model.TacheModel;
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
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class PlanificationTacheController extends AnchorPane implements Initializable {

    static Logger logger = Logger.getLogger(PlanificationTacheController.class);

    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnPrint;
    public Button btnValider;

    public TextField txtTache;
    public TableView<Tache> tachePlanificationTable;
    public TableColumn<Tache, Integer> columnNumero;
    public TableColumn<Tache, String> columnLibelle;

    public Tache tache = null;
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;


    public PlanificationTacheController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/tache.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
            logger.error(ex);
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
        ServiceproUtil.setEditable(false, txtTache);
        buildTableTache();
    }

    private void buildTableTache() {
        columnNumero.setCellValueFactory(new PropertyValueFactory<>("idtache"));
        columnLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        Task<ObservableList<Tache>> task = new Task<ObservableList<Tache>>() {
            @Override
            protected ObservableList<Tache> call() throws Exception {
                return FXCollections.observableList(new com.cfao.app.model.Model<Tache>("Tache").select());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(()->{
                tachePlanificationTable.setItems(task.getValue());
                tachePlanificationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    changementSujet(newValue);
                });
            });

        });
    }

    private void changementSujet(Tache newValue) {
        tache = newValue;
        ServiceproUtil.setEditable(false, txtTache);
        ServiceproUtil.setDisable(true, btnValider);
        txtTache.setText(newValue.getLibelle());
    }

    public void modifierAction(javafx.event.ActionEvent event){
        if (tache != null) {
            ServiceproUtil.setEditable(true, txtTache);
            ServiceproUtil.setDisable(false, btnValider);
            stateBtnModifier = 1;
            stateBtnNouveau = 0;
        }
    }


    public void supprimerAction(javafx.event.ActionEvent event){
        if(tache != null){
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<Tache>().delete(tache);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(evt -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Suppression OK");
                    tachePlanificationTable.getItems().removeAll(tache);
                } else {
                    ServiceproUtil.notify("Erreur de Suppression");
                }
            });
        }
    }

    public void nouveauAction(javafx.event.ActionEvent event){
        ServiceproUtil.setEditable(true, txtTache);
        txtTache.setText("");
        ServiceproUtil.setDisable(false, btnValider);
        stateBtnNouveau = 1;
        stateBtnModifier = 0;
    }
    public void printAction(javafx.event.ActionEvent event){}

    public void validerAction(javafx.event.ActionEvent event){
        if(!txtTache.getText().isEmpty()) {
            /**
             * Test Existence du sujet
             * **/
            Task<Boolean> tsk = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new TacheModel().exist(txtTache.getText());
                }
            };
            new Thread(tsk).start();

            tsk.setOnSucceeded(event1 -> {
                if(stateBtnNouveau == 1) {
                    stateBtnNouveau = 0;
                    if (tsk.getValue()) {
                        Tache t = new Tache();
                        t.setLibelle(txtTache.getText());
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                return new Model<Tache>().save(t);
                            }
                        };
                        new Thread(task).start();
                        task.setOnSucceeded(evt -> {
                            if (task.getValue()) {
                                ServiceproUtil.notify("Ajout OK");
                                tachePlanificationTable.getItems().add(t);
                            } else {
                                ServiceproUtil.notify("Erreur d'ajout");
                            }
                            Platform.runLater(() -> {
                                ServiceproUtil.setDisable(true, btnValider);
                                ServiceproUtil.setEditable(false, txtTache);
                            });
                        });
                    } else
                        ServiceproUtil.notify("Cette Tache Existe déjà!");
                }else if(stateBtnModifier == 1){
                    stateBtnModifier = 0;
                    if (tsk.getValue()) {
                        tache.setLibelle(txtTache.getText());
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                return new Model<Tache>().update(tache);
                            }
                        };
                        new Thread(task).start();
                        task.setOnSucceeded(evt -> {
                            if (task.getValue()) {
                                ServiceproUtil.notify("Modification OK");
                                tachePlanificationTable.refresh();

                            } else {
                                ServiceproUtil.notify("Erreur Modification");
                            }
                            Platform.runLater(() -> {
                                ServiceproUtil.setDisable(true, btnValider);
                                ServiceproUtil.setEditable(false, txtTache);
                            });
                        });
                    } else
                        ServiceproUtil.notify("Cette tache Existe déjà!");
                }
            });


        }
    }

}