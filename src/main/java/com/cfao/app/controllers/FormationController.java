package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.Etatformation;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Modele;
import com.cfao.app.beans.Personnel;
import com.cfao.app.model.*;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    public TableView formationTable;
    public TableColumn titreColumn;
    public TableColumn datedebutColumn;
    public TableColumn datefinColumn;
    public TextField txtCode;
    public TextField txtTitre;
    public DatePicker dateDebut;
    public DatePicker dateFin;
    public TextArea txtDescription;
    public ComboBox<Modele> comboModele;
    public ComboBox<Etatformation> comboEtatformation;
    public ComboBox<Personnel> comboFormateur;
    public Button btnAjouterFormateur;
    public Button btnSupprimerFormateur;
    public HBox researchBox;
    private TableView.TableViewSelectionModel<Formation> formationTableModel;
    private SearchBox searchBox = new SearchBox();
    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createFormationTable();
        //TextFields.bindAutoCompletion(comboModele.getEditor(), comboModele.getItems());

        comboModele.setEditable(true);
        fillComboModele();
        comboEtatformation.setEditable(true);
        fillComboEtatformation();
        comboFormateur.setEditable(true);
        fillComboFormateur();
        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.USER_PLUS);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.USER_TIMES);
        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.HAND_LIZARD_ALT);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);

        formationTableModel.selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                fillFormationFields();
            }
        });

        tabParticipant.setContent(new ListSelectionView<Formation>());

    }

    private void fillFormationFields() {
        if (formationTableModel.getSelectedItem() != null) {
            Formation formation = formationTableModel.getSelectedItem();
            txtCode.setText(formation.getCodeformation());
            txtTitre.setText(formation.getTitre());
            txtDescription.setText(formation.getDescription());
        }
    }

    private void fillComboFormateur() {
        PersonnelModel personnelModel = new PersonnelModel(Model.getBeansClass("Personnel"));
        Task<ObservableList<Personnel>> task = new Task<ObservableList<Personnel>>() {
            @Override
            protected ObservableList<Personnel> call() throws Exception {
                return FXCollections.observableArrayList(personnelModel.getList());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                comboFormateur.setItems(task.getValue());
            }
        });
    }

    private void fillComboModele() {
        ModeleModel modeleModel = new ModeleModel(Model.getBeansClass("Modele"));
        Task<ObservableList<Modele>> task = new Task<ObservableList<Modele>>() {
            @Override
            protected ObservableList<Modele> call() throws Exception {
                return FXCollections.observableArrayList(modeleModel.getList());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                comboModele.setItems(task.getValue());
            }
        });
    }

    private void fillComboEtatformation() {
        EtatformationModel etatformationModel = new EtatformationModel(Model.getBeansClass("Etatformation"));
        Task<ObservableList<Etatformation>> task = new Task<ObservableList<Etatformation>>() {
            @Override
            protected ObservableList<Etatformation> call() throws Exception {
                return FXCollections.observableArrayList(etatformationModel.getList());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                comboEtatformation.setItems(task.getValue());
            }
        });
    }

    private void createFormationTable() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datedebutColumn.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        formationTableModel = formationTable.getSelectionModel();

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);

        FormationModel formationModel = new FormationModel(Model.getBeansClass("Formation"));

        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableArrayList(formationModel.getList());
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                formationTable.setItems(task.getValue());
            }
        });
    }

    public void nouveauAction(ActionEvent actionEvent) {
    }

    public void editFormation(ActionEvent actionEvent) {
    }

    public void supprimerAction(ActionEvent actionEvent) {
        if (formationTableModel.getSelectedItem() != null) {

        } else {

        }
    }

    public void validerAction(ActionEvent actionEvent) {
    }

    public void annulerAction(ActionEvent actionEvent) {
    }

    public void SupprimerAction(ActionEvent actionEvent) {
    }

    public void modifierAction(ActionEvent actionEvent) {
    }
}
