package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Niveau;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.FormationModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/18/2017.
 */
public class FormationCompetenceController extends AnchorPane implements Initializable {
    public TableColumn<Competence, Void> numeroCompetence;
    public TableColumn<Competence, String> libelleCompetence;
    public TableColumn<Competence, Niveau> niveauCompetence;
    public TableColumn<Competence, Competence> possedeCompetence;
    private Formation formation = null;
    public HBox hboxCompetenceAssociee;
    // Search competences
    SearchBox searchBox1 = new SearchBox();
    SearchBox searchBoxAssocie = new SearchBox();
    public TableView<Competence> competenceTable;
    public Button btnModifierCompetence;
    public Button btnAnnulerCompetence;
    public Button btnNextCompetence;
    public Button btnPreviousCompetence;
    public Button btnPrintCompetence;

    private boolean stateBtnModifier = false;
    private ObservableList<Competence> selectedItems;

    public FormationCompetenceController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/competence.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBoxAssocie.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBoxAssocie, Priority.ALWAYS);
        hboxCompetenceAssociee.getChildren().addAll(new Label("Compétences associées : "), searchBoxAssocie);
        competenceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initComponents();
    }

    private void initComponents() {
        ButtonUtil.add(btnModifierCompetence);
        ButtonUtil.cancel(btnAnnulerCompetence);
        ButtonUtil.next(btnNextCompetence);
        ButtonUtil.previous(btnPreviousCompetence);
        ButtonUtil.print(btnPrintCompetence);
    }

    public void buildTable() {
        libelleCompetence.setCellValueFactory(param -> param.getValue().descriptionProperty());
        niveauCompetence.setCellValueFactory(param -> param.getValue().niveauProperty());

        numeroCompetence.setCellFactory(col -> {
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
        possedeCompetence.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>());
        competenceTable.setItems(FXCollections.observableArrayList(formation.getCompetences()));
        possedeCompetence.setCellFactory(param -> {
            SimpleBooleanProperty selected = new SimpleBooleanProperty(true);
            CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> selected);
            return cell;
        });
    }

    public void annulerCompetence(ActionEvent actionEvent) {
        StageManager.loadContent("/views/formation/formation.fxml");
        /*stateBtnModifier = false;
        competenceTable.setEditable(false);
        competenceTable.itemsProperty().unbind();
        buildTable();
        btnModifierCompetence.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));*/
    }

    public void modifierCompetence(ActionEvent actionEvent) {
        if (formation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir la formation");
            return;
        }
        if (stateBtnModifier == false) {
            competenceTable.setEditable(true);
            btnModifierCompetence.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
            selectedItems = FXCollections.observableArrayList();
            Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
                @Override
                protected ObservableList<Competence> call() throws Exception {
                    return FXCollections.observableArrayList(new CompetenceModel().getList());
                }
            };
            competenceTable.itemsProperty().bind(task.valueProperty());
            new Thread(task).start();
            task.setOnSucceeded((WorkerStateEvent event) -> {
                possedeCompetence.setCellFactory((TableColumn<Competence, Competence> param) -> {
                    BooleanProperty selected = new SimpleBooleanProperty();
                    CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {

                        Competence competence = task.getValue().get(index);
                        if (formation.getCompetences().contains(competence)) {
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
            possedeCompetence.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

            stateBtnModifier = true;
            task.setOnFailed(event -> {
                AlertUtil.showErrorMessage(new Exception(task.getException()));
            });
        } else {
            selectedItems.forEach(System.out::println);
            FormationModel formationModel = new FormationModel();
            formation.setCompetences(selectedItems);
            if (formationModel.update(formation)) {
                ServiceproUtil.notify("Modification OK");
                StageManager.loadContent("/views/formation/formation.fxml");
            } else {
                ServiceproUtil.notify("Erreur de modification");
            }
            competenceTable.setEditable(false);
            stateBtnModifier = false;
        }
    }

    public void previousCompetence(ActionEvent event) {
        if (competenceTable.getSelectionModel().getSelectedIndex() > 0) {
            competenceTable.getSelectionModel().selectPrevious();
        }
    }

    public void nextCompetence(ActionEvent event) {
        if (competenceTable.getSelectionModel().getSelectedIndex() < competenceTable.getItems().size()) {
            competenceTable.getSelectionModel().selectNext();
        }
    }


    public void printCompetence(ActionEvent event) {
    }
}
