package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Societe;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.SearchBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/21/2017.
 */
public class CompetencePersonneController extends AnchorPane implements Initializable {
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, String> telephonePersonneColumn;
    public TableColumn<Personne, Societe> societePersonneColumn;
    public TableColumn<Personne, Boolean> encoursColumn;
    public TableColumn<Personne, Boolean> acertifierColumn;
    public TableColumn<Personne, Boolean> certifieColumn;
    public TableView<Personne> personneTable;
    private Competence competence = null;
    public VBox vboxRecherchePersonne;
    private SearchBox searchBox = new SearchBox();

    public CompetencePersonneController(Competence competence){
        this();
        this.competence = competence;
    }
    public CompetencePersonneController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/competence/personne.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    public void initComponents(){
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        vboxRecherchePersonne.getChildren().setAll(new Label("Personnes : "), searchBox);
    }
    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public void buildTable() {

    }
}
