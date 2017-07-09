package com.cfao.app.controllers;

import antlr.collections.Stack;
import com.cfao.app.Module;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.ProfilModel;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ServiceproUtil;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by JP on 6/29/2017.
 */
public class ProfilAddEditController implements Initializable {
    public TableView<Competence> competenceTable;
    public VBox rootContentPane;
    public TableColumn<Competence, Competence> intituleColumn;
    public TableColumn<Competence, Competence> possedeColumn;
    public TableColumn competenceColumn;
    public TableColumn connaissanceColumn;
    public TextField txtProfil;
    public TextField txtAbbreviation;
    public Button btnAnnuler;
    public Button btnValider;
    ObservableSet<Competence> selectedItems;

    private Profil profil = null;

    public ProfilAddEditController(Profil profil){
        this.profil = profil;
    }
    public ProfilAddEditController(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlyphsDude.setIcon(btnValider, FontAwesomeIcon.SAVE);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.CARET_DOWN);
        buildCompetenceTable();
        // S'il s'agit d'une modification
        if(profil != null){
            txtAbbreviation.setText(profil.getAbbreviation());
            txtProfil.setText(profil.getLibelle());
        }
    }

    public void buildCompetenceTable() {
        CompetenceModel competenceModel = new CompetenceModel();
        intituleColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue()));
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(competenceModel.select());
            }
        };
        task.run();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            competenceTable.setItems(task.getValue());
            competenceColumn.setCellFactory(param -> {
                CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {
                    Competence competence = task.getValue().get(index);
                    if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                        return new SimpleBooleanProperty(true);
                    } else {
                        return new SimpleBooleanProperty(false);
                    }
                });
                cell.setDisable(true);
                return cell;
            });
            connaissanceColumn.setCellFactory((Object param) -> {
                CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        Competence competence = task.getValue().get(index);
                        if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                            return new SimpleBooleanProperty(true);
                        } else {
                            return new SimpleBooleanProperty(false);
                        }
                    }
                });
                cell.setDisable(true);
                return cell;
            });
        });

        possedeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Competence, Competence> param) -> new SimpleObjectProperty<>(param.getValue()));
        selectedItems = FXCollections.observableSet(new HashSet<>());


        possedeColumn.setCellFactory((TableColumn<Competence, Competence> param) -> {

            BooleanProperty selected = new SimpleBooleanProperty();
            CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(new Callback<Integer, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(Integer index) {
                    return selected;
                }
            });
            if(profil != null) {
                for (Competence competence : task.getValue()) {
                    for (Competence competence1 : profil.getCompetences()) {
                        if (competence.equals(competence1)) {
                            selectedItems.add(competence);
                        }
                    }
                }
            }
            // update set of selected indices if checkbox state changes
            selected.addListener((observable, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedItems.add(cell.getItem());
                } else {
                    selectedItems.remove(cell.getItem());
                }
                competenceTable.getSelectionModel().clearSelection();
                for (Competence c : selectedItems) {
                    competenceTable.getSelectionModel().select(c);
                }
            });
            // update check box state if set of selected indices changes
            selectedItems.addListener((SetChangeListener<Competence>) change -> selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem())));
            // update checkbox when cell is reused for different a different index
            cell.itemProperty().addListener((observable, oldItem, newItem) -> {
                selected.set(newItem != null && selectedItems.contains(newItem));
            });
            return cell;
        });
        competenceTable.setEditable(true);
        competenceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void validerAction(ActionEvent actionEvent) {
        selectedItems.forEach(System.out::println);
        boolean edit = true;
        if(profil == null) {
            profil = new Profil();
            edit = false;
        }
        profil.setAbbreviation(txtAbbreviation.getText());
        profil.setLibelle(txtProfil.getText());
        Model<Profil> model = new Model<>(Model.getBeanPath("Profil"));
        profil.setCompetences(selectedItems);
        boolean bool; String sms = "";
        if(edit){
            bool = model.update(profil);
            sms = "Modification OK";
        }else{
            sms = "Enregistrement OK";
            bool = model.save(profil);
        }
        if (bool) {
            ServiceproUtil.notify(sms);
        } else {
            ServiceproUtil.notify("Une erreur est survenu");
        }
        StageManager.loadContent("/views/profil/profil.fxml");
    }
    public void annulerAction(ActionEvent event) {
        //Module.setProfil(StageManager.);
        StageManager.loadContent("/views/profil/profil.fxml");
    }
}