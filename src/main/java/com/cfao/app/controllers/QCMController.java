package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Qcm;
import com.cfao.app.beans.QcmType;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/1/2017.
 */
public class QCMController implements Initializable {
    public TableColumn<Qcm, String> titreColumn;
    public TableColumn<Qcm, QcmType> typeColumn;
    public StackPane qcmStackPane;
    public ComboBox<QcmType> comboTypeTest;
    public TextField txtBase;
    public TextField txtTitre;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnAnnuler;
    public TableView<Competence> competenceTable;
    public TableColumn<Competence, Void> numeroCompetenceColumn;
    public TableColumn<Competence, String> descriptionCompetenceColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TableColumn<Competence, Boolean> possedeCompetenceColumn;
    public StackPane competenceStackPane;
    private SearchBox searchBox = new SearchBox();
    public VBox vboxSearch;
    public TableView<Qcm> qcmTable;
    public QCMController(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        ServiceproUtil.setDisable(true, comboTypeTest);
        ServiceproUtil.setEditable(false, txtTitre, txtBase);
        vboxSearch.getChildren().setAll(new Label("Tests :"), searchBox);
        initComponents();

        qcmTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Qcm>() {
            @Override
            public void changed(ObservableValue<? extends Qcm> observable, Qcm oldValue, Qcm newValue) {
                buildCompetenceTable(newValue);
            }
        });
        Task<ObservableList<QcmType>> task = new Task<ObservableList<QcmType>>() {
            @Override
            protected ObservableList<QcmType> call() throws Exception {
                return FXCollections.observableArrayList(new Model("QcmType").getList());
            }
        };
        comboTypeTest.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        task.setOnFailed(event -> AlertUtil.showErrorMessage(task.getException()));

    }

    private void buildCompetenceTable(Qcm qcm) {
        if(qcm != null){
            comboTypeTest.setValue(qcm.getQcmType());
            txtBase.setText(qcm.getBase() + "");
            txtTitre.setText(qcm.getTitre());
            competenceTable.setItems(FXCollections.observableArrayList(qcm.getCompetences()));
        }
    }

    private void initComponents(){
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.cancel(btnAnnuler);
        ButtonUtil.print(btnPrint);
        ButtonUtil.previous(btnPrevious);
        ButtonUtil.next(btnNext);
        ButtonUtil.add(btnNouveau);
        titreColumn.setCellValueFactory(param -> param.getValue().titre());
        typeColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getQcmType()));
        Model<Qcm> model = new Model<>("Qcm");
        model.getList();
        Task<ObservableList<Qcm>> task = new Task<ObservableList<Qcm>>() {
            @Override
            protected ObservableList<Qcm> call() throws Exception {
                return FXCollections.observableArrayList(new Model("Qcm").getList());
            }
        };
        qcmTable.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(qcmStackPane, task);
        new Thread(task).start();
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                AlertUtil.showErrorMessage(task.getException());
            }
        });

        descriptionCompetenceColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        possedeCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        numeroCompetenceColumn.setCellFactory(col -> {
            TableCell<Competence, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
    }

    public void nouveauAction(ActionEvent event) {
        ServiceproUtil.emptyFields(txtBase, txtTitre);
        ServiceproUtil.setDisable(false, comboTypeTest);
        ServiceproUtil.setEditable(true, txtTitre, txtBase);
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());

        new ProgressIndicatorUtil(competenceStackPane, task);
        new Thread(task).start();
    }

    public void modifierAction(ActionEvent event) {
    }

    public void supprimerAction(ActionEvent event) {
        Qcm qcm = qcmTable.getSelectionModel().getSelectedItem();
        if(qcm != null){
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<>("Qcm").delete(qcm);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                if(task.getValue()){
                    ServiceproUtil.notify("Suppression OK");
                    qcmTable.getItems().remove(qcm);
                }else{
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
        }else{
            AlertUtil.showSimpleAlert("Information", "Vueillez d'abord choisir le test à supprimer");
        }
    }

    public void annulerAction(ActionEvent event) {
        StageManager.loadContent("/views/qcm/qcm.fxml");
    }
}
