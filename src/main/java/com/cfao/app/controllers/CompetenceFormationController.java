package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Formation;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.SearchBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/18/2017.
 */
public class CompetenceFormationController extends AnchorPane implements Initializable {
    private Formation formation = null;
    public HBox hboxCompetenceAssociee;
    // Search competences
    SearchBox searchBox1 = new SearchBox();
    SearchBox searchBoxAssocie = new SearchBox();
    private TableView<Competence> competenceTable;
    public Button btnModifierCompetence;
    public Button btnAnnulerCompetence;
    public Button btnNextCompetence;
    public Button btnPreviousCompetence;
    public Button btnPrintCompetence;

    public CompetenceFormationController() {
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
        initComponents();
    }

    private void initComponents(){
        ButtonUtil.add(btnModifierCompetence);
        ButtonUtil.cancel(btnAnnulerCompetence);
        ButtonUtil.next(btnNextCompetence);
        ButtonUtil.previous(btnPreviousCompetence);
        ButtonUtil.print(btnPrintCompetence);
    }
    public void buildTable() {

    }

    public void annulerCompetence(ActionEvent actionEvent) {
    }

    public void modifierCompetence(ActionEvent actionEvent) {
    }

}
