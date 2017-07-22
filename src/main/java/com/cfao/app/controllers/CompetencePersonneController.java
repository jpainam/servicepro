package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.util.AlertUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/21/2017.
 */
public class CompetencePersonneController extends AnchorPane implements Initializable {
    private Competence competence = null;

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

    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public void buildTable() {

    }
}
