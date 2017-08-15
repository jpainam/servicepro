package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/13/2017.
 */
public class AccueilPersonneController extends AnchorPane implements Initializable {
    public TableView<Personne> personneTable;
    public StackPane participantPerformance;
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, String> matriculePersonneColumn;
    public SearchBox searchBox = new SearchBox();

    public StackPane personneStackPane;
    public TableColumn<Personne, Societe> societePersonneColumn;
    public TableColumn<Personne, Groupe> groupePersonneColumn;
    public TableColumn<Personne, Section> sectionPersonneColumn;
    public TableColumn<Personne, String> telephonePersonneColumn;
    public TableColumn<Personne, String> emailPersonneColumn;
    public TableColumn<Personne, Pays> paysPersonneColumn;
    public TableColumn<Personne, Ambition> ambitionPersonneColumn;
    public TableColumn<Personne, Langue> languePersonneColumn;
    public TableColumn<Personne, LocalDate> datecontratPersonneColumn;
    public TableColumn<Personne, String> memoPersonneColumn;
    public TableColumn<Personne, Potentiel> potentielPersonneColumn;
    public TableColumn<Personne, ImageView> photoPersonneColumn;
    public TableColumn<Personne, LocalDate> datenaissPersonneColumn;


    public TableView<ProfilPersonne> profilTable;
    public TableColumn<ProfilPersonne, Profil> profilColumn;
    public TableColumn<ProfilPersonne, Niveau> niveauColumn;

    public TableColumn<PersonneCompetence, Boolean> encoursColumn = new TableColumn<>("En cours");
    public TableColumn<PersonneCompetence, Boolean> acertifierColumn = new TableColumn<>(" A certifier");
    public TableColumn<PersonneCompetence, Boolean> certifieeColumn = new TableColumn<>("Certifiée");
    public TableColumn<PersonneCompetence, Competence> intituleColum = new TableColumn<>("Intitulé de la compétence");
    public TableViewResizeUtil<PersonneCompetence> competenceTable = new TableViewResizeUtil<>();
    public PopOver profilPopOver = new PopOver();
    public PopOver personneDetailsPopOver = new PopOver();
    private AccueilProfilController profilController;

    private PieChart chart;

    public VBox reserchePanel;


    public AccueilPersonneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accueil/personne.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
        createChartPersonne();
        profilController = new AccueilProfilController();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        profilColumn.setCellValueFactory(param -> param.getValue().profil());
        niveauColumn.setCellValueFactory(param -> param.getValue().niveau());
        competenceTable.getColumns().addAll(intituleColum, encoursColumn, certifieeColumn, acertifierColumn);
        personneTable.setTableMenuButtonVisible(true);

        competenceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        intituleColum.setMinWidth(300);
        competenceTable.setMinWidth(600);
        profilPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        personneDetailsPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);

        encoursColumn.setCellValueFactory(param -> param.getValue().encoursProperty());
        acertifierColumn.setCellValueFactory(param -> param.getValue().acertifierProperty());
        certifieeColumn.setCellValueFactory(param -> param.getValue().certifieeProperty());
        intituleColum.setCellValueFactory(param -> param.getValue().competence());

    }


    private void initComponents() {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        reserchePanel.getChildren().addAll(new Label("Civilités "), searchBox);

        nomPersonneColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        matriculePersonneColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        societePersonneColumn.setCellValueFactory(param -> param.getValue().societe());
        groupePersonneColumn.setCellValueFactory(param -> param.getValue().groupeProperty());
        sectionPersonneColumn.setCellValueFactory(param -> param.getValue().sectionProperty());
        telephonePersonneColumn.setCellValueFactory(param -> param.getValue().telephoneProperty());
        photoPersonneColumn.setCellValueFactory(param -> {
            CivilitePhoto civilitePhoto = new CivilitePhoto();
            Image image = civilitePhoto.getImage(param.getValue(), 25, 25);
            return new SimpleObjectProperty<>(new ImageView(image));
        });
        emailPersonneColumn.setCellValueFactory(param -> param.getValue().emailProperty());
        paysPersonneColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        languePersonneColumn.setCellValueFactory(param -> param.getValue().langueProperty());
        potentielPersonneColumn.setCellValueFactory(param -> param.getValue().potentielProperty());
        datecontratPersonneColumn.setCellValueFactory(param -> param.getValue().datecontratProperty());
        datecontratPersonneColumn.setCellFactory(new DateTableCellFactory<>());
        datenaissPersonneColumn.setCellValueFactory(param -> param.getValue().datenaissProperty());
        datenaissPersonneColumn.setCellFactory(new DateTableCellFactory<>());
        ambitionPersonneColumn.setCellValueFactory(param -> param.getValue().ambitionProperty());
        memoPersonneColumn.setCellValueFactory(param -> param.getValue().memoProperty());

        PersonneModel personneModel = new PersonneModel();
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                return FXCollections.observableArrayList(personneModel.getList());
            }

        };
        new Thread(task).start();
        personneTable.itemsProperty().bind(task.valueProperty());
        ProgressIndicatorUtil.show(personneStackPane, task);

        personneTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Personne>() {
            @Override
            public void changed(ObservableValue<? extends Personne> observable, Personne oldValue, Personne newValue) {
                buildProfilTable(newValue);
                createChartPersonne();
            }
        });

        personneTable.setRowFactory(param -> {
            TableRow<Personne> row = new TableRow<>();
            row.hoverProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (isHover()) {
                        profilController.setPersonne(row.getItem());
                        profilController.buildProfilDetails();
                        personneDetailsPopOver.setContentNode(profilController);
                        personneDetailsPopOver.show(row);
                    }
                }
            });
            return row;
        });

    }

    private void createChartPersonne() {
        Personne personne = personneTable.getSelectionModel().getSelectedItem();

        Task<ObservableList<PersonneCompetence>> task = new Task<ObservableList<PersonneCompetence>>() {
            @Override
            protected ObservableList<PersonneCompetence> call() throws Exception {
                if (personne != null) {
                    return personne.personneCompetencesProperty();
                } else {
                    return FXCollections.observableArrayList(new Model<PersonneCompetence>("PersonneCompetence").getList());
                }
            }
        };
        ProgressIndicatorUtil.show(participantPerformance, task);
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            int nbCertifier, nbEncours, nbAcertifier;
            nbAcertifier = nbCertifier = nbEncours = 0;
            for (PersonneCompetence pp : task.getValue()) {
                if (pp.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ENCOURS)) {
                    nbEncours++;
                } else if (pp.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_CERTIFIEE)) {
                    nbCertifier++;
                } else {
                    nbAcertifier++;
                }
            }
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                    new PieChart.Data("A certifier", nbCertifier),
                    new PieChart.Data("En cours", nbEncours),
                    new PieChart.Data("Certifiée", nbAcertifier));
            chart = new PieChart(data);
            chart.setTitle("Compétence certifiée, en cours et à certifier");
            chart.setClockwise(false);
            participantPerformance.getChildren().setAll(chart);
        });
        task.setOnFailed(event -> {
            ServiceproUtil.notify("Une erreur dans le thread du create Chart");
            System.err.println(task.getException());
        });

    }

    private void buildProfilTable(Personne personne) {
        profilTable.itemsProperty().bind(personne.profilPersonnesProperty());
        profilTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProfilPersonne>() {
            @Override
            public void changed(ObservableValue<? extends ProfilPersonne> observable, ProfilPersonne oldValue, ProfilPersonne newValue) {
                Task<ObservableList<PersonneCompetence>> task = new Task<ObservableList<PersonneCompetence>>() {
                    @Override
                    protected ObservableList<PersonneCompetence> call() throws Exception {
                        return FXCollections.observableArrayList(new CompetenceModel().getCompetencePersonneByProfil(newValue));
                    }
                };
                competenceTable.itemsProperty().bind(task.valueProperty());
                competenceTable.setVisibleRowCount(newValue.getProfil().getCompetences().size() + 2);
                profilPopOver.setContentNode(competenceTable);
                profilPopOver.show(profilTable);
            }
        });
    }

}
