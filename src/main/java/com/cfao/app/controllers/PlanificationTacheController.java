package com.cfao.app.controllers;

import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/30/2017.
 */
public class PlanificationTacheController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(PlanificationTacheController.class);

    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public Button btnPrint;
    public PlanificationTacheController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/tache.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
            logger.error(ex);
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