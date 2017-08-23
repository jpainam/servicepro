package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import com.cfao.app.util.TableViewResizeUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
    TableColumn<PersonneCompetence, Competence> intituleColum = new TableColumn<>("Libellé de la compétence");
    TableViewResizeUtil<PersonneCompetence> competenceTable = new TableViewResizeUtil<>();
    TableColumn<PersonneCompetence, Boolean> encoursColumn = new TableColumn<>("En cours");
    TableColumn<PersonneCompetence, Boolean> acertifierColumn = new TableColumn<>("A certifier");
    TableColumn<PersonneCompetence, Boolean> certifierColumn = new TableColumn<>("Certifiée");
    public Button btnAjouterProfil;
    public Button btnDeleteProfil;

    public Personne personne;
    public ObservableMap<String, ObservableList> map;

    public boolean btnActive = false;

    PopOver profilPopOver = new PopOver();

    public CiviliteProfilController(Personne personne) {
        this();
        this.personne = personne;

    }

    public CiviliteProfilController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/profil.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
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
        competenceTable.getColumns().addAll(intituleColum, encoursColumn, certifierColumn, acertifierColumn);

        intituleColum.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCompetence()));
        encoursColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifierColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        tableProfil.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buildCompetencePopover(newValue);
        });


    }

    private void buildCompetencePopover(ProfilPersonne profilPersonne) {
        if (profilPersonne != null) {
            encoursColumn.setCellValueFactory(param -> param.getValue().encoursProperty());
            acertifierColumn.setCellValueFactory(param -> param.getValue().acertifierProperty());
            certifierColumn.setCellValueFactory(param -> param.getValue().certifieeProperty());

            Task<ObservableList<PersonneCompetence>> task = new Task<ObservableList<PersonneCompetence>>() {
                @Override
                protected ObservableList<PersonneCompetence> call() throws Exception {
                    return  FXCollections.observableArrayList(new CompetenceModel().getCompetencePersonneByProfil(profilPersonne));
                }
            };
            new Thread(task).start();
            competenceTable.itemsProperty().bind(task.valueProperty());
            task.setOnFailed(event -> {
                task.getException().printStackTrace();
                ServiceproUtil.notify("Erreur dans le thread de competence pop over");
            });
            competenceTable.setVisibleRowCount(profilPersonne.getProfil().getCompetences().size() + 2);
            competenceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            profilPopOver.setContentNode(competenceTable);
            intituleColum.setMinWidth(300);
            competenceTable.setMinWidth(600);
            profilPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
            profilPopOver.show(tableProfil);

        }
    }

    public ObservableList<ProfilPersonne> getItems() {
        return this.tableProfil.getItems();
    }


    public void addProfil(ActionEvent actionEvent) {
        if (btnActive) {
            Dialog<ProfilPersonne> dialog = DialogUtil.dialogTemplate();
            dialog.setHeaderText("Ajouter un Profil");
            DialogProfilController controller = new DialogProfilController();
            dialog.getDialogPane().setContent(controller);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return controller.getData();
                } else {
                    return null;
                }
            });
            Optional<ProfilPersonne> result = dialog.showAndWait();
            result.ifPresent(profilPersonne -> {
                if (profilPersonne != null) {
                    boolean exiteDeja = false;
                    int i = 0;
                    while (!exiteDeja && i < tableProfil.getItems().size()) {
                        ProfilPersonne pp = tableProfil.getItems().get(i);
                        if (pp.getProfil().equals(profilPersonne.getProfil()) && pp.getNiveau().equals(profilPersonne.getNiveau())) {
                            exiteDeja = true;
                        }
                        i++;
                    }
                    if (!exiteDeja) {
                        tableProfil.getItems().add(profilPersonne);
                    }
                } else {
                    ServiceproUtil.notify("Erreur d'ajout de profil");
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
        if (personne != null) {
            tableProfil.setItems(FXCollections.observableArrayList(personne.getProfilPersonnes()));
        }
    }

    public void setActive(boolean active) {
        this.btnActive = active;
        ServiceproUtil.setDisable(!active, btnAjouterProfil, btnDeleteProfil);
    }


    class DialogProfilController extends AnchorPane implements Initializable {
        @FXML
        public ComboBox<Profil> comboProfil;
        @FXML
        public ComboBox<Niveau> comboLevel;

        public DialogProfilController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogProfil.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
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
                Platform.runLater(() -> {
                    comboProfil.setItems(map.get("profil"));
                    comboLevel.setItems(map.get("niveau"));
                });

            });
        }

        public ProfilPersonne getData() {
            if (comboLevel.getValue() != null && comboProfil.getValue() != null) {
                ProfilPersonne profilPersonne = new ProfilPersonne();
                profilPersonne.setNiveau(comboLevel.getSelectionModel().getSelectedItem());
                profilPersonne.getId().setNiveau(comboLevel.getSelectionModel().getSelectedItem().getIdniveau());
                profilPersonne.setProfil(comboProfil.getSelectionModel().getSelectedItem());
                profilPersonne.getId().setProfil(comboProfil.getSelectionModel().getSelectedItem().getIdprofil());
                if (personne != null) {
                    profilPersonne.setPersonne(personne);
                }
                return profilPersonne;
            }
            return null;
        }

    }

}
