package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ProgressIndicatorUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/14/2017.
 */
public class QCMPotentielController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(QCMPotentielController.class);
    public TableView<Personne> potentielTable;
    public TableColumn<Personne, Pays> paysColumn;
    public TableColumn<Personne, String> nomColumn;
    public TableColumn<Personne, String> prenomColumn;
    public TableColumn<Personne, Label> numeroColumn;
    public TableColumn<Personne, Societe> filialeColumn;
    public StackPane potentielStackPane;

    private Qcm qcm;

    public QCMPotentielController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/qcm/potentielpersonne.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        numeroColumn.setCellValueFactory(param -> {
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.USER);
            Label label = new Label();
            iconView.setFill(Color.FORESTGREEN);
            label.setGraphic(iconView);
            return new SimpleObjectProperty<>(label);
        });
        paysColumn.setCellValueFactory(param -> param.getValue().paysProperty());
        filialeColumn.setCellValueFactory(param -> param.getValue().societe());
        nomColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomColumn.setCellValueFactory(param -> param.getValue().prenomProperty());
    }

    public void buildTable() {
        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                List<Competence> competences = qcm.getCompetences();
                List<Personne> personneDeja = new ArrayList<>();
                for (PersonneQcm pq : qcm.getPersonneQcms()) {
                    personneDeja.add(pq.getPersonne());
                }
                List<Personne> personnes = new PersonneModel().getList();
                ObservableList<Personne> data = FXCollections.observableArrayList();
                for (Personne p : personnes) {
                    boolean aBesoin = false;
                    Iterator<PersonneCompetence> iterator = p.getPersonneCompetences().iterator();
                    while (!aBesoin && iterator.hasNext()) {
                        PersonneCompetence pc = iterator.next();
                        Competence c = pc.getCompetence();
                        if (!personneDeja.contains(p)) {
                            if (competences.contains(c) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                                aBesoin = true;
                            }
                        }
                    }
                    if (aBesoin) {
                        data.add(p);
                    }
                }
                return data;
            }
        };
        ProgressIndicatorUtil.show(potentielStackPane, task);
        new Thread(task).start();
        potentielTable.itemsProperty().bind(task.valueProperty());
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            logger.error(task.getException());
        });
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }
}
