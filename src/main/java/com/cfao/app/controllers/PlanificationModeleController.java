package com.cfao.app.controllers;

import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.SearchBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/29/2017.
 */
public class PlanificationModeleController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(PlanificationModeleController.class);
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;

    public Button btnExporterExcel;
    public VBox vboxSearch;
    private SearchBox searchBox = new SearchBox();

    public PlanificationModeleController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/modele.fxml"));
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
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        vboxSearch.getChildren().setAll(new Label("Modèle de planification"), searchBox);
        initComponents();
    }
    private void initComponents(){
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimer);

        ButtonUtil.plusIcon(btnNouveau);
        ButtonUtil.excel(btnExporterExcel);
    }

    public void modifierAction(javafx.event.ActionEvent event){}
    public void nouveauAction(javafx.event.ActionEvent event){}

    public void exporterExcelAction(javafx.event.ActionEvent event){}
    public void supprimerAction(javafx.event.ActionEvent event){}
}
