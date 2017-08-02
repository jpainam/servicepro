package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ProgressIndicatorUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {
    public TableColumn<Competence, Competence> intituleCompetenceColumn;
    public TableColumn<Competence, Boolean> encoursCompetenceColumn;
    public TableColumn<Competence, Boolean> acertifierCompetenceColumn;
    public TableColumn<Competence, Boolean> certifieCompetenceColumn;
    public TableView<Competence> competenceTable;
    public Button btnPreviousCompetence;
    public Button btnNextCompetence;
    public Button btnPrintCompetence;

    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> codeFormationColumn;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableColumn<Formation, LocalDate> datedebutFormationColumn;
    public TableColumn<Formation, LocalDate> datefinFormationColumn;
    public ComboBox<CompetenceStatut> comboStatut;
    private Personne personne = null;
    public StackPane competenceStackPane;
    public WebView webView;

    public CiviliteFormationController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/formation.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public CiviliteFormationController(Personne personne) {
        this();
        this.personne = personne;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        WebEngine engine = webView.getEngine();
        String url = getClass().getResource("/pdfjs/web/viewer.html").toExternalForm();
        engine.setUserStyleSheetLocation(getClass().getResource("/pdfjs/web/viewer.css").toExternalForm());

        engine.setJavaScriptEnabled(true);
        byte[] data = null;
        try {
            // readFileToByteArray() comes from commons-io library
            File f = new File("src/main/resources/documents/Final2016.pdf");
            //File f = new File(URI.create(getClass().getResource("/views/documents/Final2016.pdf").toExternalForm()));
            data = Files.readAllBytes(f.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String base64 = Base64.getEncoder().encodeToString(data);
        engine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED){
//                webView.getEngine().executeScript("openFileFromBase64('" + base64 + "')");
            }
        });
        engine.load(url);
    }

    private void initComponents() {
        ButtonUtil.next(btnNextCompetence);
        ButtonUtil.previous(btnPreviousCompetence);
        ButtonUtil.print(btnPrintCompetence);
        Task<ObservableList<CompetenceStatut>> task = new Task<ObservableList<CompetenceStatut>>() {
            @Override
            protected ObservableList<CompetenceStatut> call() throws Exception {
                return FXCollections.observableArrayList(new Model<CompetenceStatut>("CompetenceStatut").getList());
            }
        };
        comboStatut.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        encoursCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifieCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        intituleCompetenceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

        competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
                final TableRow<Competence> row = new TableRow<>();
                final Tooltip tooltip = new Tooltip();
                row.hoverProperty().addListener(observable -> {

                    final Competence competence = row.getItem();
                    if(row.isHover() && competence != null){
                        tooltip.setText(competence.getDescription());
                        row.setTooltip(tooltip);
                    }
                });
                return row;
            }
        });
        /*competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
                return new TableRow<Competence>(){


                    private Tooltip tooltip = new Tooltip();
                    @Override
                    protected void updateItem(Competence item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null){
                            setTooltip(null);
                        }else{
                            tooltip.setText(item.getDescription());
                            setTooltip(tooltip);

                        }
                    }
                };
            }
        });*/
    }

    public void buildFormation() {
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        new ProgressIndicatorUtil(competenceStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            encoursCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                for(PersonneCompetence pc : personne.getPersonneCompetences()){
                    if(pc.getCompetence().equals(competence) && pc.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_ENCOURS)){
                        return new SimpleBooleanProperty(true);
                    }
                }
                return new SimpleBooleanProperty(false);
            });
            certifieCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                if (personne.getCompetences().contains(competence)) {
                    return new SimpleBooleanProperty(true);
                }
                return new SimpleObjectProperty<>(false);
            });
            acertifierCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                if (!personne.getCompetences().contains(competence)) {
                    return new SimpleBooleanProperty(true);
                }
                return new SimpleBooleanProperty(false);
            });
        });
    }

    public void previousCompetenceAction(ActionEvent event) {
    }

    public void nextCompetenceAction(ActionEvent event) {
    }

    public void printCompetenceAction(ActionEvent event) {
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void changeLanguage(Locale locale) {
        webView.getEngine().executeScript("changeLanguage('" + locale.toLanguageTag() + "')");
    }
}
