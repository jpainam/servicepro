package com.cfao.app.controllers;

import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.SearchBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/22/2017.
 */
public class PersonnelController implements Initializable{
    public Button btnPrintPersonnel;
    public Button btnNouveauPersonnel;
    public Button btnModifierPersonnel;
    public Button btnAnnulerPersonnel;
    public VBox vboxsearchBox;
    public Button btnSupprimerPersonnel;

    private SearchBox searchBox = new SearchBox();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        vboxsearchBox.getChildren().setAll(new Label("Personnels : "), searchBox);

        ButtonUtil.print(btnPrintPersonnel);
        ButtonUtil.add(btnNouveauPersonnel);
        ButtonUtil.cancel(btnAnnulerPersonnel);
        ButtonUtil.edit(btnModifierPersonnel);
        ButtonUtil.delete(btnSupprimerPersonnel);
    }

    public void printPersonnelAction(ActionEvent event) {
    }

    public void nouveauPersonnelAction(ActionEvent event) {
    }

    public void modifierPersonnelAction(ActionEvent event) {
    }

    public void annulerPersonnelAction(ActionEvent event) {
    }

    public void supprimerPersonnelAction(ActionEvent event) {
    }
}
