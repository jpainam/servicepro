package com.cfao.app.controllers;

import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class PlanificationSujetController extends AnchorPane implements Initializable {
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnPrint;

    public PlanificationSujetController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/sujet.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }
    private void initComponents(){
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.print(btnPrint);
        ButtonUtil.plusIcon(btnNouveau);
    }

    public void modifierAction(javafx.event.ActionEvent event){}
    public void supprimerAction(javafx.event.ActionEvent event){}
    public void nouveauAction(javafx.event.ActionEvent event){}
    public void printAction(javafx.event.ActionEvent event){}
}
