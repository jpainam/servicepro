package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Qcm;
import com.cfao.app.beans.QcmType;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
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
    public TableView<Competence> competenceTable;
    public TableColumn<Competence, Void> numeroCompetenceColumn;
    public TableColumn<Competence, String> descriptionCompetenceColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TableColumn<Competence, Competence> possedeColumn;
    public StackPane competenceStackPane;
    private SearchBox searchBox = new SearchBox();
    public VBox vboxSearch;
    public Tab tabPersonne;
    public Tab tabDetails;

    public QCMPersonneController personneController;
    public TableView<Qcm> qcmTable;

    public VBox vboxFlowChart;
    public VBox vboxFlowArea;

    public QcmDiagram qcmDiagram = new QcmDiagram();
    private boolean stateBtnModifier = false;
    private boolean stateBtnNouveau = false;

    private ObservableList<Competence> selectedItems;

    public QCMController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        ServiceproUtil.setDisable(true, comboTypeTest);
        ServiceproUtil.setEditable(false, txtTitre, txtBase);
        vboxSearch.getChildren().setAll(new Label("Tests :"), searchBox);
        initComponents();

        qcmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buildCompetenceTable(newValue);
            buildFlowChart(newValue);
        });
        Task<ObservableList<QcmType>> task = new Task<ObservableList<QcmType>>() {
            @Override
            protected ObservableList<QcmType> call() throws Exception {
                return FXCollections.observableArrayList(new Model("QcmType").getList());
            }
        };
        comboTypeTest.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        task.setOnFailed(event -> AlertUtil.showErrorMessage(new Exception(task.getException())));

        personneController = new QCMPersonneController();
        tabPersonne.setContent(personneController);

        ButtonUtil.detailsTab(tabDetails);
        GlyphsDude.setIcon(tabPersonne, FontAwesomeIcon.USERS);

    }

    private void buildFlowChart(Qcm qcm) {
        qcmDiagram.setQcm(qcm);
        vboxFlowArea.getChildren().setAll(qcmDiagram.createPieChart());
    }

    private void buildCompetenceTable(Qcm qcm) {
        if (qcm != null) {
            comboTypeTest.setValue(qcm.getQcmType());
            txtBase.setText(qcm.getBase() + "");
            txtTitre.setText(qcm.getTitre());
            competenceTable.setItems(FXCollections.observableArrayList(qcm.getCompetences()));
            personneController.setQcm(qcm);
            personneController.buildPersonneTable();
        }
    }

    private void initComponents() {
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
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
        ProgressIndicatorUtil.show(qcmStackPane, task);
        new Thread(task).start();
        task.setOnFailed(event -> AlertUtil.showErrorMessage(new Exception(task.getException())));

        descriptionCompetenceColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        possedeColumn.setCellFactory(param -> {
            SimpleBooleanProperty selected = new SimpleBooleanProperty(true);
            CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> selected);
            return cell;
        });
        possedeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>());

        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
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
        if(!stateBtnNouveau) {
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

            ProgressIndicatorUtil.show(competenceStackPane, task);
            new Thread(task).start();
            stateBtnNouveau = true;
        }else{
            stateBtnNouveau = false;
        }
    }

    public void modifierAction(ActionEvent event) {
        Qcm qcm = qcmTable.getSelectionModel().getSelectedItem();
        if(qcm == null){
            return;
        }
        if(!stateBtnModifier){
            ServiceproUtil.setDisable(false, comboTypeTest);
            ServiceproUtil.setEditable(true, txtBase, txtTitre);
            stateBtnModifier = true;
            competenceTable.setEditable(true);
            btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            selectedItems = FXCollections.observableArrayList();
            Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
                @Override
                protected ObservableList<Competence> call() throws Exception {
                    return FXCollections.observableArrayList(new CompetenceModel().getList());
                }
            };
            competenceTable.itemsProperty().bind(task.valueProperty());
            new Thread(task).start();
            task.setOnSucceeded((WorkerStateEvent event1) -> {
                possedeColumn.setCellFactory((TableColumn<Competence, Competence> param) -> {
                    BooleanProperty selected = new SimpleBooleanProperty();
                    CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {

                        Competence competence = task.getValue().get(index);
                        if (qcm.getCompetences().contains(competence)) {
                            selected.set(true);
                        }
                        return selected;
                    });
                    selected.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            if (!selectedItems.contains(cell.getItem())) {
                                selectedItems.add(cell.getItem());
                            }
                            competenceTable.getSelectionModel().select(cell.getItem());
                        } else {
                            if (selectedItems.contains(cell.getItem())) {
                                selectedItems.remove(cell.getItem());
                            }
                            competenceTable.getSelectionModel().clearSelection(cell.getIndex());
                        }
                    });

                    selectedItems.addListener((ListChangeListener<Competence>) change -> {
                        selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem()));
                    });
                    cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                        selected.set(newValue != null && selectedItems.contains(newValue));
                    });
                    return cell;
                });
            });
            task.setOnFailed(event12 -> {
                task.getException().printStackTrace();
                ServiceproUtil.notify("Erreur dans le thread de modification Qcm");
            });
            possedeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

        }else{
            selectedItems.forEach(System.out::println);
            Model<Qcm> model = new Model<>("Qcm");
            qcm.setCompetences(selectedItems);
            qcm.setBase(Integer.parseInt(txtBase.getText()));
            qcm.setTitre(txtTitre.getText());
            if (model.update(qcm)) {
                ServiceproUtil.notify("Modification OK");
                StageManager.loadContent("/views/qcm/qcm.fxml");
            } else {
                ServiceproUtil.notify("Erreur de modification");
            }
            /*competenceTable.setEditable(false);
            stateBtnModifier = false;
            btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));*/
        }
    }

    public void supprimerAction(ActionEvent event) {
        Qcm qcm = qcmTable.getSelectionModel().getSelectedItem();
        if (qcm != null) {
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<>("Qcm").delete(qcm);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Suppression OK");
                    qcmTable.getItems().remove(qcm);
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
        } else {
            AlertUtil.showSimpleAlert("Information", "Vueillez d'abord choisir le test à supprimer");
        }
    }
}
