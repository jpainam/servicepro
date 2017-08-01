package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by armel on 10/07/2017.
 */
public class CiviliteProfilController extends AnchorPane implements Initializable {
    /**
     * TableView des Profils d'une personne
     */
    public TableView<ProfilPersonne> tableProfil;
    public TableColumn<ProfilPersonne, Profil> columnProfil;
    public TableColumn<ProfilPersonne, Niveau> columnNiveau;
    TableColumn<Competence, Competence> intituleColum = new TableColumn<>("Libellé de la compétence");
    TableView<Competence> competenceTable = new TableView<>();
    TableColumn<Competence, Boolean> encoursColumn = new TableColumn<>("En cours");
    TableColumn<Competence, Boolean> acertifierColumn = new TableColumn<>("A certifier");
    TableColumn<Competence, Boolean> certifierColumn = new TableColumn<>("Certifiée");
    public Button btnAjouterProfil;
    public Button btnDeleteProfil;

    public Personne personne;
    public ObservableMap<String, ObservableList> map;

    public boolean btnActive = false;

    PopOver profilPopOver = new PopOver();

    public CiviliteProfilController(Personne personne){
        this();
        this.personne = personne;

    }
    public CiviliteProfilController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/profil.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnProfil.setCellValueFactory(param -> param.getValue().profil());
        columnNiveau.setCellValueFactory(param -> param.getValue().niveau());
        GlyphsDude.setIcon(btnAjouterProfil, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnDeleteProfil, FontAwesomeIcon.MINUS_SQUARE);
        ServiceproUtil.setDisable(true, btnDeleteProfil, btnAjouterProfil);
        profilPopOver.setCornerRadius(4);
        profilPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        competenceTable.setMinSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        profilPopOver.setMinWidth(Double.NEGATIVE_INFINITY);
        VBox.setVgrow(competenceTable, Priority.ALWAYS);
        HBox.setHgrow(competenceTable, Priority.ALWAYS);
        competenceTable.getColumns().addAll(intituleColum, encoursColumn, acertifierColumn, certifierColumn);

        intituleColum.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        encoursColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifierColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        tableProfil.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProfilPersonne>() {
            @Override
            public void changed(ObservableValue<? extends ProfilPersonne> observable, ProfilPersonne oldValue, ProfilPersonne newValue) {
                buildCompetencePopover(newValue);
            }
        });
    }

    private void buildCompetencePopover(ProfilPersonne profilPersonne) {
        if(profilPersonne != null){
            List<PersonneCompetence> competences = personne.getPersonneCompetences();
            encoursColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                int i = 0;
                while(i < competences.size()){
                    PersonneCompetence co = competences.get(i);
                    if(co.equals(competence) && co.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_ENCOURS)){
                        return  new SimpleBooleanProperty(true);
                    }
                    i++;
                }
                return new SimpleBooleanProperty(false);
            });
            acertifierColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                int i = 0;
                while(i < competences.size()){
                    PersonneCompetence co = competences.get(i);
                    if(co.equals(competence) && !co.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_ACERTIFIER)){
                        return new SimpleBooleanProperty(false);
                    }
                }
                return new SimpleBooleanProperty(true);
            });
            certifierColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                int i = 0;
                while(i < competences.size()){
                    PersonneCompetence co = competences.get(i);
                    if(co.equals(competence) && co.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_CERTIFIEE)){
                        return new SimpleBooleanProperty(true);
                    }
                }
                return new SimpleBooleanProperty(false);
            });
            competenceTable.setItems(FXCollections.observableArrayList(profilPersonne.getProfil().getCompetences()));
            profilPopOver.setContentNode(competenceTable);
            profilPopOver.show(tableProfil);
        }
    }

    public ObservableList<ProfilPersonne> getItems(){
        return this.tableProfil.getItems();
    }


    public void addProfil(ActionEvent actionEvent) {
        if (btnActive) {
            Dialog<ProfilPersonne> dialog = DialogUtil.dialogTemplate();
            dialog.setHeaderText("Ajouter un Profil");
            DialogProfilController controller = new DialogProfilController();
            dialog.getDialogPane().setContent(controller);
            dialog.setResultConverter(new Callback<ButtonType, ProfilPersonne>() {
                @Override
                public ProfilPersonne call(ButtonType param) {
                    if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        return controller.getData();
                    } else {
                        return null;
                    }
                }
            });
            Optional<ProfilPersonne> result = dialog.showAndWait();
            result.ifPresent(new Consumer<ProfilPersonne>() {
                @Override
                public void accept(ProfilPersonne profilPersonne) {
                    if (profilPersonne != null) {
                        tableProfil.getItems().add(profilPersonne);
                    } else {
                        ServiceproUtil.notify("Erreur d'ajout de profil");
                    }

                }
            });
        }
    }

    public void deleteProfil(ActionEvent actionEvent) {
        if (tableProfil.getSelectionModel().getSelectedItem() != null) {
            tableProfil.getItems().remove(tableProfil.getSelectionModel().getSelectedItem());
        }
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void buildProfil() {
        if(personne != null) {
            tableProfil.setItems(FXCollections.observableArrayList(personne.getProfilPersonnes()));
        }
    }

    public void setActive(boolean active) {
        this.btnActive = active;
        ServiceproUtil.setDisable(!active, btnAjouterProfil, btnDeleteProfil);
    }


    class DialogProfilController extends  AnchorPane implements  Initializable{
        @FXML
        public ComboBox<Profil> comboProfil;
        @FXML
        public ComboBox<Niveau> comboLevel;

        public DialogProfilController(){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogProfil.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            }catch (Exception ex){
                AlertUtil.showErrorMessage(ex);
            }
        }
        @Override
        public void initialize(URL location, ResourceBundle resources) {
            Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
                @Override
                protected ObservableMap<String, ObservableList> call() throws Exception {
                    map = FXCollections.observableHashMap();
                    map.put("profil", FXCollections.observableList((new Model<Profil>("Profil")).getList()));
                    map.put("niveau", FXCollections.observableList((new Model<Niveau>("Niveau")).getList()));
                    return map;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                Platform.runLater(()->{
                    comboProfil.setItems(map.get("profil"));
                    comboLevel.setItems(map.get("niveau"));
                });

            });
        }
        public ProfilPersonne getData(){
            if(comboLevel.getValue() != null && comboProfil.getValue() != null) {
                ProfilPersonne profilPersonne = new ProfilPersonne();
                profilPersonne.setNiveau(comboLevel.getSelectionModel().getSelectedItem());
                profilPersonne.setProfil(comboProfil.getSelectionModel().getSelectedItem());
                if(personne != null) {
                    profilPersonne.setPersonne(personne);
                }
                return profilPersonne;
            }
            return null;
        }

    }

}