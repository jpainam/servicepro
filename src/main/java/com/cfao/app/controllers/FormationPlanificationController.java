package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Planification;
import com.cfao.app.util.AlertUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/29/2017.
 */
public class FormationPlanificationController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(FormationPlanificationController.class);

    public StackPane planificationStackPane;
    public Formation formation = null;
    public TableView<Planification> planificationTable;

    public FormationPlanificationController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/planification.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setFormation(Formation formation){
        this.formation = formation;
    }
    public void buildTable(){
        if(formation == null)
            return;

        planificationTable.setItems(formation.planificationsProperty());


    }
}
