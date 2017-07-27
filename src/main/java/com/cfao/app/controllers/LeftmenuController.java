package com.cfao.app.controllers;


import com.cfao.app.StageManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Communication on 13/06/2017.
 */
public class LeftmenuController implements Initializable{

    public ToggleButton btnPersonne;
    public ToggleButton btnCompetence;
    public ToggleButton btnProfil;
    public ToggleButton btnTest;
    public ToggleButton btnRapport;
    public ToggleButton btnParametre;
    public ToggleButton btnFormation;
    public final ToggleGroup toggleGroup = new ToggleGroup();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLeftMenuSettings();
        btnPersonne.setUserData("/views/civilite/civilite.fxml");
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(oldValue != null){
                    ((FontAwesomeIconView)((ToggleButton)oldValue).getGraphic()).setFill(Color.BLACK);
                }
                if(newValue == null){
                    oldValue.setSelected(true);
                }else{
                    ((FontAwesomeIconView)((ToggleButton)newValue).getGraphic()).setFill(Color.WHITE);
                    ((ToggleButton)(newValue)).getStyleClass().add("toggle-button1");
                    //StageManager.loadContent(toggleGroup.getSelectedToggle().getUserData().toString());
                }
            }
        });
    }
    public void setLeftMenuSettings() {
        setLeftMenuSetting(btnPersonne, FontAwesomeIcon.USERS);
        setLeftMenuSetting(btnCompetence, FontAwesomeIcon.SLACK);
        setLeftMenuSetting(btnParametre, FontAwesomeIcon.GEAR);
        setLeftMenuSetting(btnFormation, FontAwesomeIcon.GRADUATION_CAP);
        setLeftMenuSetting(btnRapport, FontAwesomeIcon.FILES_ALT);
        setLeftMenuSetting(btnTest, FontAwesomeIcon.BALANCE_SCALE);
        setLeftMenuSetting(btnProfil, FontAwesomeIcon.TAGS);
    }
    public void setLeftMenuSetting(ToggleButton button, FontAwesomeIcon fontAwesomeIcon) {
        button.setToggleGroup(toggleGroup);
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setGlyphSize(35);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(icon);
    }
    public void personneAction(ActionEvent event) {
        StageManager.setSelectedCrumb("personne");
        StageManager.loadContent("/views/civilite/civilite.fxml");
    }
    public void formationAction(ActionEvent event) {
        StageManager.setSelectedCrumb("formation");
        StageManager.loadContent("/views/formation/formation.fxml");
    }
    public void competenceAction(ActionEvent event) {
        StageManager.setSelectedCrumb("competence");
        StageManager.loadContent("/views/competence/competence.fxml");
    }
    public void testAction(ActionEvent event) {
        StageManager.loadContent("/views/test/test.fxml");
    }
    public void rapportAction(ActionEvent event) {
        StageManager.loadContent("/views/rapport/rapport.fxml");
    }
    public void parametreAction(ActionEvent event) {
        StageManager.loadContent("/views/parametre/parametre.fxml");
    }
    public void profilAction(ActionEvent event) {
        StageManager.loadContent("/views/profil/profil.fxml");
    }

   /* @FXML
    public Accordion Leftmenu;

    @FXML
    public ListView listPersonnes;
    @FXML
    public ListView listFormations;
    @FXML
    public ListView listCompetences;
    @FXML
    public ListView listProfils;
    @FXML
    public ListView listTests;
    @FXML
    public ListView listParametres;
    @FXML
    public ListView listRapports;
    public TitledPane personnePane;

    public AnchorPane container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildleftMenu();
        ServiceproUtil.setAccordionExpanded(Leftmenu, personnePane);
    }

    public void setContainer(AnchorPane container){
        this.container = container;
    }
    private void buildleftMenu() {

        ObservableList<Label> data ;
        data = FXCollections.observableArrayList();
        Label labelmenu1 = GlyphsDude.createIconLabel(FontAwesomeIcon.CLOUD_UPLOAD, "Importer", "11",
                "", ContentDisplay.LEFT);
        Label labelmenu2 = GlyphsDude.createIconLabel(FontAwesomeIcon.BED, "Exporter", "11",
                "", ContentDisplay.LEFT);
        data.addAll(labelmenu1, labelmenu2);
        listPersonnes.setItems(data);
        listPersonnes.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue observable, Object oldValue, Object newValue) -> {
                    switch (listPersonnes.getSelectionModel().getSelectedIndex()){
                        case 0:
                                Module.setImportPersonne(container);
                            break;
                        case 1:
                            Notifications.create().title("Implémentation")
                                    .text("Fonctionnalité non encore implémentée")
                                    .showInformation();

                            break;
                    }
                });

    }
*/

}