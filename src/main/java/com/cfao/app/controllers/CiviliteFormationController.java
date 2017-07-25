package com.cfao.app.controllers;

import com.cfao.app.beans.CompetenceStatut;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneCompetence;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {
    public TableColumn<PersonneCompetence, String> intituleCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> encoursCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> acertifierCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> certifieCompetenceColumn;
    public TableView<PersonneCompetence> competenceTable;
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

    public CiviliteFormationController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/formation.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    public CiviliteFormationController(Personne personne){
        this();
        this.personne = personne;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }
    private void initComponents(){
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



    }
    public void buildFormation(){

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
}
