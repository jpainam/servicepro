package com.cfao.app.controllers;


import com.cfao.app.StageManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by Communication on 13/06/2017.
 */
public class LeftmenuController implements Initializable{

    public Button btnPersonne;
    public Button btnCompetence;
    public Button btnProfil;
    public Button btnTest;
    public Button btnRapport;
    public Button btnParametre;
    public Button btnFormation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLeftMenuSettings();
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
    public void setLeftMenuSetting(Button button, FontAwesomeIcon fontAwesomeIcon) {
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setGlyphSize(35);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(icon);
    }
    public void personneAction(ActionEvent event) {
        StageManager.loadContent("/views/civilite/civilite.fxml");
    }
    public void formationAction(ActionEvent event) {
        StageManager.loadContent("/views/formation/formation.fxml");
    }
    public void competenceAction(ActionEvent event) {
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