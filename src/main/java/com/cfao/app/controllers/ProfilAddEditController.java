package com.cfao.app.controllers;

import antlr.collections.Stack;
import com.cfao.app.Module;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Niveau;
import com.cfao.app.beans.Profil;
import com.cfao.app.beans.Profilcompetence;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.ProfilModel;
import com.cfao.app.util.*;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by JP on 6/29/2017.
 */
public class ProfilAddEditController extends StackPane implements Initializable {
    public TableView<Profilcompetence> competenceTable;

    public TableColumn<Profilcompetence, Profilcompetence> intituleColumn;
    public TableColumn<Profilcompetence, Boolean> possedeColumn;
    public TableColumn<Profilcompetence, Boolean> competenceColumn;
    public TableColumn<Profilcompetence, Boolean> connaissanceColumn;
    public TextField txtProfil;
    public TextField txtAbbreviation;
    public Button btnAnnuler;
    public Button btnSupprimerCompetence;
    public Button btnAjouterCompetence;
    public Button btnValider;
    public ComboBox<Niveau> comboNiveau;
    public StackPane competenceStackPane;
    public ComboBox<Competence> comboCompetence;


    private Profil profil = null;

    public ProfilAddEditController(Profil profil) {
        this();
        this.profil = profil;
        txtAbbreviation.setText(profil.getAbbreviation());
        txtProfil.setText(profil.getLibelle());
        if (profil != null && !profil.getProfilcompetences().isEmpty()) {
            comboNiveau.setValue(profil.getProfilcompetences().iterator().next().getNiveau());
            buildCompetenceTable(profil.getProfilcompetences().iterator().next().getNiveau());
        }
    }

    public ProfilAddEditController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profil/add.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        ButtonUtil.cancel(btnAnnuler);
        ButtonUtil.add(btnValider, btnAjouterCompetence);
        ButtonUtil.delete(btnSupprimerCompetence);
    }

    private void initComponents() {
        intituleColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue().getCompetence()));
        Task<ObservableList<Niveau>> task = new Task<ObservableList<Niveau>>() {
            @Override
            protected ObservableList<Niveau> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Niveau>("Niveau").getList());
            }
        };
        new Thread(task).start();
        comboNiveau.itemsProperty().bind(task.valueProperty());

        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        possedeColumn.setCellFactory(param -> new CheckBoxTableCell<>());

        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue().getCompetence();
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue().getCompetence();
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        possedeColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true));
    }

    public void buildCompetenceTable(Niveau niveau) {
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                if (profil != null) {
                    return FXCollections.observableArrayList(new CompetenceModel().getNonCompetences(profil.getCompetences(niveau)));
                } else {
                    return FXCollections.observableArrayList(new CompetenceModel().getList());
                }
            }
        };
        comboCompetence.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(competenceStackPane, task);
        new Thread(task).start();
        if (profil != null) {
            //competenceTable.itemsProperty().bind(FXCollections.observableArrayList(profil.getProfilcompetences()));
            competenceTable.setItems(FXCollections.observableArrayList(profil.getProfilcompetences()));
        }

    }

    public void validerAction(ActionEvent actionEvent) {
        try {
            boolean edit = true;
            if (profil == null) {
                profil = new Profil();
                edit = false;
            }
            /*Model<Profilcompetence> model = new Model<>("Profilcompetence");
            for (Profilcompetence competence : profil.getProfilcompetences()) {
                model.saveOrUpdate(competence);
            }*/
            //profil.getProfilcompetences().clear();
            profil.setAbbreviation(txtAbbreviation.getText());
            profil.setLibelle(txtProfil.getText());
            ProfilModel profilModel = new ProfilModel();

            boolean bool;
            String sms = "";
            if (edit) {
                //System.exit(0);
                bool = profilModel.update(profil);
                sms = "Modification OK";
            } else {
                sms = "Enregistrement OK";
                bool = profilModel.save(profil);
            }
            if (bool) {
                ServiceproUtil.notify(sms);
            } else {
                ServiceproUtil.notify("Une erreur est survenu");
            }
            StageManager.loadContent("/views/profil/profil.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void annulerAction(ActionEvent event) {
        //Module.setProfil(StageManager.);
        StageManager.loadContent("/views/profil/profil.fxml");
    }

    public void comboNiveauAction(ActionEvent actionEvent) {
        Niveau niveau = comboNiveau.getSelectionModel().getSelectedItem();
        if (niveau != null) {
            System.out.println(niveau);
            buildCompetenceTable(niveau);
        }
    }

    public void ajouterCompetenceAction(ActionEvent event) {
        if (comboCompetence.getSelectionModel().getSelectedItem() != null) {
            Competence competence = comboCompetence.getSelectionModel().getSelectedItem();
            Niveau niveau = comboNiveau.getSelectionModel().getSelectedItem();
            Profilcompetence profilcompetence = new Profilcompetence(profil, competence, niveau);
            competenceTable.getItems().add(profilcompetence);
            comboCompetence.getItems().remove(competence);
        }
    }

    public void supprimerAction(ActionEvent event) {
        if (competenceTable.getSelectionModel().getSelectedItem() != null) {
            Profilcompetence profilcompetence = competenceTable.getSelectionModel().getSelectedItem();
            competenceTable.getItems().remove(profilcompetence);
            comboCompetence.getItems().add(profilcompetence.getCompetence());
        }
    }

}