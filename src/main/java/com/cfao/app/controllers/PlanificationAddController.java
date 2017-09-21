package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.ButtonUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/29/2017.
 */
public class PlanificationAddController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(PlanificationAddController.class);
    public TableView<Tache> tacheTable;
    public TableColumn<Tache, Tache> checkColumn;
    public TableColumn<Tache, String> libelleColumn;
    private Formation formation = null;
    private ObservableList<Tache> selectedItems;

    public VBox vboxDocument;
    public ComboBox<UserProfil> comboValidation;
    public ComboBox<UserProfil> comboResponsable;
    public ComboBox<Sujet> comboSujet;
    public ComboBox<Integer> comboTiming;
    public TextArea txtRemarque;
    public TextArea txtCommentaire;
    public Button btnAjouterPlanification;
    public CheckComboBox comboDocument = new CheckComboBox(FXCollections.observableArrayList());

    private TableView<PlanificationModele> planificationModeleTable;

    public PlanificationAddController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/addPlanificationDialog.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        vboxDocument.getChildren().setAll(comboDocument);
        comboDocument.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(comboDocument, Priority.ALWAYS);
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("sujet", FXCollections.observableArrayList(new Model<Sujet>("Sujet").getList()));
                map.put("tache", FXCollections.observableArrayList(new Model<Tache>("Tache").getList()));
                map.put("document", FXCollections.observableArrayList(new Model<Document>("Document").getList()));
                map.put("userprofil", FXCollections.observableArrayList(new Model<UserProfil>("UserProfil").getList()));
                List<Integer> timings = new ArrayList<>();
                for (int i = -100; i < 110; i++) {
                    timings.add(i);
                }
                map.put("timingColumn", FXCollections.observableArrayList(timings));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                comboSujet.setItems(task.getValue().get("sujet"));
                tacheTable.setItems(task.getValue().get("tache"));
                comboValidation.setItems(task.getValue().get("userprofil"));
                comboResponsable.setItems(task.getValue().get("userprofil"));
                comboTiming.setItems(task.getValue().get("timingColumn"));
                comboDocument.getItems().setAll(task.getValue().get("document"));
            }
        });
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            logger.error(task.getException());
        });
    }

    private void initComponents() {
        tacheTable.setEditable(true);
        ButtonUtil.plusIcon(btnAjouterPlanification);
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
        checkColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        checkColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        selectedItems = FXCollections.observableArrayList();
        Task<ObservableList<Tache>> task = new Task<ObservableList<Tache>>() {
            @Override
            protected ObservableList<Tache> call() throws Exception {
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            checkColumn.setCellFactory((TableColumn<Tache, Tache> param) -> {
                BooleanProperty selected = new SimpleBooleanProperty();
                CheckBoxTableCell<Tache, Tache> cell = new CheckBoxTableCell<>(index -> selected);
                selected.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        if (!selectedItems.contains(cell.getItem())) {
                            selectedItems.add(cell.getItem());
                        }
                        tacheTable.getSelectionModel().select(cell.getItem());
                    } else {
                        if (selectedItems.contains(cell.getItem())) {
                            selectedItems.remove(cell.getItem());
                        }
                        tacheTable.getSelectionModel().clearSelection(cell.getIndex());
                    }
                });

                selectedItems.addListener((ListChangeListener<Tache>) change -> {
                    selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem()));
                });
                cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                    selected.set(newValue != null && selectedItems.contains(newValue));
                });
                return cell;
            });
            checkColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue()));
        });
    }

    public boolean getData() {
        if (formation == null)
            return false;

        return true;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void ajouterPlanificationAction(ActionEvent event) {
        /** Modele de Planification */
        if (formation == null) {
            PlanificationModele pm = new PlanificationModele();
            pm.setTaches(selectedItems);
            pm.setTiming(comboTiming.getValue());
            pm.setSujet(comboSujet.getValue());
            pm.setResponsable(comboResponsable.getValue());
            pm.setValidation(comboValidation.getValue());
            pm.setRemarque(txtRemarque.getText());
            pm.setCommentaire(txtCommentaire.getText());
            pm.setDocuments(comboDocument.getCheckModel().getCheckedItems());
            planificationModeleTable.getItems().add(pm);
        }
        /** Planification d'une formation */
        else {
            Planification planification = new Planification();
            planification.setTaches(selectedItems);
            planification.setTiming(comboTiming.getValue());
            planification.setSujet(comboSujet.getValue());
            planification.setFait(false);
            planification.setAlert(true);
            planification.setResponsable(comboResponsable.getValue());
            planification.setValidation(comboValidation.getValue());
            planification.setRemarque(txtRemarque.getText());
            planification.setCommentaire(txtCommentaire.getText());
            planification.setFormation(formation);
            planification.setDocuments(comboDocument.getCheckModel().getCheckedItems());
            formation.getPlanifications().add(planification);
            //System.out.println("Ajout de la planification");
            /** Netoyer les champs */
            selectedItems.clear();
            comboDocument.getCheckModel().clearChecks();
        }
    }

    public void setPlanificationModeleTable(TableView<PlanificationModele> planificationModeleTable) {
        this.planificationModeleTable = planificationModeleTable;
    }
}
