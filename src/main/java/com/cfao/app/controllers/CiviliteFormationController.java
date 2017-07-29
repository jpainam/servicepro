package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ProgressIndicatorUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {
    public TableColumn<Competence, Competence> intituleCompetenceColumn;
    public TableColumn<Competence, Boolean> encoursCompetenceColumn;
    public TableColumn<Competence, Boolean> acertifierCompetenceColumn;
    public TableColumn<Competence, Boolean> certifieCompetenceColumn;
    public TableView<Competence> competenceTable;
    public Button btnPreviousCompetence;
    public Button btnNextCompetence;
    public Button btnPrintCompetence;
    public Button btnPreviousFormation;
    public Button btnNextFormation;
    public Button btnPrintFormation;
    public Button btnNextSouhait;
    public Button btnPreviousSouhait;
    public Button btnPrintSouhait;
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> codeFormationColumn;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableColumn<Formation, LocalDate> datedebutFormationColumn;
    public TableColumn<Formation, LocalDate> datefinFormationColumn;
    public TableColumn<Formation, String> codeSouhaitColumn;
    public TableColumn<Formation, String> titreSouhaitColumn;
    public TableColumn<Formation, LocalDate> datedebutSouhaitColumn;
    public TableColumn<Formation, LocalDate> datefinSouhaitColumn;
    public TableView<Formation> souhaitTable;
    public ComboBox<CompetenceStatut> comboStatut;
    private Personne personne = null;
    public StackPane competenceStackPane;

    public CiviliteFormationController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/formation.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public CiviliteFormationController(Personne personne) {
        this();
        this.personne = personne;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        ButtonUtil.next(btnNextCompetence, btnNextFormation, btnNextSouhait);
        ButtonUtil.previous(btnPreviousCompetence, btnPreviousFormation, btnPreviousSouhait);
        ButtonUtil.print(btnPrintCompetence, btnPrintFormation, btnPrintSouhait);
        Task<ObservableList<CompetenceStatut>> task = new Task<ObservableList<CompetenceStatut>>() {
            @Override
            protected ObservableList<CompetenceStatut> call() throws Exception {
                return FXCollections.observableArrayList(new Model<CompetenceStatut>("CompetenceStatut").getList());
            }
        };
        comboStatut.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        encoursCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifieCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        intituleCompetenceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

        competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
                final TableRow<Competence> row = new TableRow<>();
                final Tooltip tooltip = new Tooltip();
                row.hoverProperty().addListener(observable -> {

                    final Competence competence = row.getItem();
                    if(row.isHover() && competence != null){
                        tooltip.setText(competence.getDescription());
                        row.setTooltip(tooltip);
                    }
                });
                return row;
            }
        });
        /*competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
                return new TableRow<Competence>(){


                    private Tooltip tooltip = new Tooltip();
                    @Override
                    protected void updateItem(Competence item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null){
                            setTooltip(null);
                        }else{
                            tooltip.setText(item.getDescription());
                            setTooltip(tooltip);

                        }
                    }
                };
            }
        });*/
    }

    public void buildFormation() {
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(competenceStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            encoursCompetenceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Competence, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Competence, Boolean> param) {
                    Competence competence = param.getValue();
                    for(PersonneCompetence pc : personne.getPersonneCompetences()){
                        if(pc.getCompetence().equals(competence) && pc.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_ENCOURS)){
                            return new SimpleBooleanProperty(true);
                        }
                    }
                    return new SimpleBooleanProperty(false);
                }
            });
            certifieCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                if (personne.getCompetences().contains(competence)) {
                    return new SimpleBooleanProperty(true);
                }
                return new SimpleObjectProperty<>(false);
            });
            acertifierCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                if (!personne.getCompetences().contains(competence)) {
                    return new SimpleBooleanProperty(true);
                }
                return new SimpleBooleanProperty(false);
            });
        });
    }

    public void previousCompetenceAction(ActionEvent event) {
    }

    public void nextCompetenceAction(ActionEvent event) {
    }

    public void printCompetenceAction(ActionEvent event) {
    }

    public void previousFormationAction(ActionEvent event) {
    }

    public void previousSouhaitAction(ActionEvent event) {
    }

    public void nextSouhaitAction(ActionEvent event) {
    }

    public void printSouhaitAction(ActionEvent event) {
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
}
