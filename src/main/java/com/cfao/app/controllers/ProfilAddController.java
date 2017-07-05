package com.cfao.app.controllers;

import com.cfao.app.Module;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.model.CompetenceModel;
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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
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
public class ProfilAddController implements Initializable {
    public TableView<Competence> competenceTable;
    public VBox rootContentPane;
    public JFXButton btnValider;
    public JFXButton btnAnnuler;
    public TableColumn<Competence, Competence> intituleColumn;
    public TableColumn<Competence, Competence> possedeColumn;
    public TableColumn competenceColumn;
    public TableColumn connaissanceColumn;
    public TextField txtProfil;
    public TextField txtAbbreviation;
    ObservableSet<Competence> selectedItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootContentPane.setMaxWidth(Double.MAX_VALUE);
        rootContentPane.setMaxHeight(Double.MAX_VALUE);
        competenceTable.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(competenceTable, Priority.ALWAYS);
        GlyphsDude.setIcon(btnValider, FontAwesomeIcon.SAVE);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.CARET_DOWN);
        buildCompetenceTable();

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
        new Thread(task).run();
        task.setOnSucceeded(event -> {
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
            connaissanceColumn.setCellFactory(param -> {
                CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {
                    Competence competence = task.getValue().get(index);
                    if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                        return new SimpleBooleanProperty(true);
                    } else {
                        return new SimpleBooleanProperty(false);
                    }
                });
                cell.setDisable(true);
                return cell;
            });
        });

        possedeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Competence, Competence> param) -> new SimpleObjectProperty<>(param.getValue()));
        selectedItems = FXCollections.observableSet(new HashSet<>());

        possedeColumn.setCellFactory(param -> {
            BooleanProperty selected = new SimpleBooleanProperty();
            CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> selected);
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
        Profil profil = new Profil();
        profil.setAbbreviation(txtAbbreviation.getText());
        profil.setLibelle(txtProfil.getText());
        ProfilModel profilModel = new ProfilModel();
        if(profilModel.insert(profil)){
            if(profilModel.insert(selectedItems, profil)) {
                ServiceproUtil.notify("Enregistrement OK");
            }else{
                ServiceproUtil.notify("Erreur d'enregistrement des competences");
            }
        }else{
            ServiceproUtil.notify("Erreur d'enregistrement du profil");
        }

    }

    public void annulerAction(ActionEvent actionEvent) {
        //Module.setProfil(TemplateController.get);
    }
}