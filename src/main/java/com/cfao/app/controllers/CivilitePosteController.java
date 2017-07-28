package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.util.AlertUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/28/2017.
 */
public class CivilitePosteController extends AnchorPane implements Initializable {
    public Personne personne;
    public CivilitePosteController(Personne personne){
        this();
        this.personne = personne;
    }
    public CivilitePosteController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogPoste.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
