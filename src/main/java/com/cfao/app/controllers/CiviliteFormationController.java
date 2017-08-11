package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {
    public TableColumn<Competence, Competence> intituleCompetenceColumn;
    public TableColumn<Competence, CompetenceCertification> certificationCompetenceColumn;

    public TableView<Competence> competenceTable;
    public Button btnPreviousCompetence;
    public Button btnNextCompetence;
    public Button btnPrintCompetence;
    public Button btnEditerCertification;

    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> codeFormationColumn;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableColumn<Formation, LocalDate> datedebutFormationColumn;
    public TableColumn<Formation, LocalDate> datefinFormationColumn;
    public StackPane historiqueStackPane;
    public ComboBox<CompetenceCertification> comboStatut;
    private Personne personne = null;
    public StackPane competenceStackPane;
    public AnchorPane pdfContainer;

    private boolean editerCertification = false;

    private HashMap<Competence, CompetenceCertification> selectedItems;

    final SwingController swingController = new SwingController();
    private JComponent viewerPanel;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

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
        createViewer(pdfContainer);
        formationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Formation>() {
            @Override
            public void changed(ObservableValue<? extends Formation> observable, Formation oldValue, Formation newValue) {
                //String url = ResourceBundle.getBundle("Bundle").getString("document.dir");
                //openDocument(getClass().getResource(url + "documents.pdf").getPath());

                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                if(!path.toFile().exists()){
                    path.toFile().mkdir();
                }
                openDocument(path.toString() + File.separator + "documents.pdf");
            }
        });

    }


    public void openDocument(String document) {
        SwingUtilities.invokeLater(() -> {
            swingController.openDocument(document);
            viewerPanel.revalidate();
        });
    }

    private void initComponents() {
        ButtonUtil.next(btnNextCompetence);
        ButtonUtil.previous(btnPreviousCompetence);
        ButtonUtil.print(btnPrintCompetence);
        ButtonUtil.edit(btnEditerCertification);
        Task<ObservableList<CompetenceCertification>> task = new Task<ObservableList<CompetenceCertification>>() {
            @Override
            protected ObservableList<CompetenceCertification> call() throws Exception {
                return FXCollections.observableArrayList(new Model<CompetenceCertification>("CompetenceCertification").getList());
            }
        };
        comboStatut.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        task.setOnFailed(event -> {
            System.err.println(task.getException());
            ServiceproUtil.notify("Erreur dans le Thread combo Statut");
        });

        competenceTable.setRowFactory(param -> {
            final TableRow<Competence> row = new TableRow<>();
            final Tooltip tooltip = new Tooltip();
            row.hoverProperty().addListener(observable -> {

                final Competence competence = row.getItem();
                if (row.isHover() && competence != null) {
                    tooltip.setText(competence.getDescription());
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });

        codeFormationColumn.setCellValueFactory(param -> param.getValue().codeformationProperty());
        titreFormationColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        datedebutFormationColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutFormationColumn.setCellFactory(new DateTableCellFactory<Formation>());
        datefinFormationColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinFormationColumn.setCellFactory(new DateTableCellFactory<Formation>());

    }

    public void buildFormation() {
        //Competence table / competence a certifier
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        ProgressIndicatorUtil.show(competenceStackPane, task);
        new Thread(task).start();

        // Historique des formation
        Task<ObservableList<Formation>> task2 = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableArrayList(new FormationModel().getFormationsByPersonne(personne));
            }
        };
        formationTable.itemsProperty().bind(task2.valueProperty());
        ProgressIndicatorUtil.show(historiqueStackPane, task2);
        new Thread(task2).start();

        // Competence a certifier
        Task<ObservableList<CompetenceCertification>> task1 = new Task<ObservableList<CompetenceCertification>>() {
            @Override
            protected ObservableList<CompetenceCertification> call() throws Exception {
                return FXCollections.observableArrayList(new Model("CompetenceCertification").getList());
            }
        };
        new Thread(task1).start();
        intituleCompetenceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
                return new TableRow<Competence>() {
                    @Override
                    protected void updateItem(Competence item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean trouver = false;
                        if (item != null) {
                            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                                if (pc.getCompetence().equals(item)) {
                                    trouver = true;
                                    if (pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_CERTIFIEE)) {
                                        getStyleClass().add("row-certifie");
                                    } else if (pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ENCOURS)) {
                                        getStyleClass().add("row-encours");
                                    } else {
                                        getStyleClass().add("row-acertifier");
                                    }
                                }
                            }
                            if (!trouver) {
                                getStyleClass().add("row-acertifier");
                            }
                        }
                    }
                };
            }
        });
        selectedItems = new HashMap<>();
        task1.setOnSucceeded(event -> {
            certificationCompetenceColumn.setCellValueFactory(param -> {
                Competence competence = param.getValue();
                for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                    if (pc.getCompetence().equals(competence) && !pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                        selectedItems.put(competence, pc.getCompetenceCertification());
                        return new SimpleObjectProperty<>(pc.getCompetenceCertification());
                    }
                }
                return new SimpleObjectProperty<>(new CompetenceCertification("AC", "A certifier"));
            });
            certificationCompetenceColumn.setCellFactory(ComboBoxTableCell.forTableColumn(task1.getValue()));
            certificationCompetenceColumn.setOnEditCommit(event1 -> {
                TablePosition<Competence, CompetenceCertification> pos = event1.getTablePosition();
                CompetenceCertification certif = event1.getNewValue();
                int row = pos.getRow();
                Competence competence = event1.getTableView().getItems().get(row);
                if (certif.getCertification().equals(Constante.COMPETENCE_ENCOURS) || certif.getCertification().equals(Constante.COMPETENCE_CERTIFIEE)) {
                    selectedItems.put(competence, certif);
                } else {
                    selectedItems.remove(competence);
                }
            });
        });
        task2.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ServiceproUtil.notify("Erreur dans le thread d'historique de formation");
                task2.getException().printStackTrace();
                System.err.println(task2.getException());
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ServiceproUtil.notify("Erreur dans le thread de competence");
                task.getException().printStackTrace();
                System.err.println(task.getException());
            }
        });
        task1.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ServiceproUtil.notify("Erreur dans le thread de certification");
                task1.getException().printStackTrace();
                System.err.println(task1.getException());
            }
        });
    }


    public void editerCertificationCompetence(ActionEvent event) {
        if (personne != null) {
            if (!editerCertification) {
                Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
                    @Override
                    protected ObservableList<Competence> call() throws Exception {
                        return FXCollections.observableArrayList(new CompetenceModel().getList());
                    }
                };
                new Thread(task).start();
                btnEditerCertification.setText(ResourceBundle.getBundle("Bundle").getString("button.save.fr"));
                competenceTable.setEditable(true);
                editerCertification = true;
            } else {

                List<PersonneCompetence> competenceList = new ArrayList<>();
                personne.getPersonneCompetences().clear();
                for (Map.Entry<Competence, CompetenceCertification> entry : selectedItems.entrySet()) {
                    PersonneCompetence pc = new PersonneCompetence();
                    pc.setPersonne(personne);
                    pc.setCompetence(entry.getKey());
                    pc.setCompetenceCertification(entry.getValue());
                    competenceList.add(pc);
                    personne.getPersonneCompetences().add(pc);
                }
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return new PersonneModel().saveCompetence(competenceList);
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event1 -> {
                    if (task.getValue()) {
                        editerCertification = false;
                        btnEditerCertification.setText("Editer");
                        competenceTable.setEditable(false);
                        ServiceproUtil.notify("Sauvegarde OK");
                    } else {
                        ServiceproUtil.notify("Une erreur de sauvegarde s'est produite");
                    }
                });
                task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ServiceproUtil.notify("Erreur dans le Thread de formation certification");
                        task.getException().printStackTrace();
                        System.err.println(task.getException());
                    }
                });
            }
        }
    }

    public void previousCompetenceAction(ActionEvent event) {
    }

    public void nextCompetenceAction(ActionEvent event) {
    }

    public void printCompetenceAction(ActionEvent event) {
    }

    public void filterCertification(ActionEvent event) {
        CompetenceCertification certification = comboStatut.getValue();
        if (certification != null) {
            Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
                @Override
                protected ObservableList<Competence> call() throws Exception {
                    return FXCollections.observableArrayList(new CompetenceModel().getCompetencesByCertification(personne, certification));
                }
            };
            new Thread(task).start();
            competenceTable.itemsProperty().bind(task.valueProperty());
            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ServiceproUtil.notify("Erreur dans le Thread [Filtrage certification]");
                    task.getException().printStackTrace();
                    System.err.println(task.getException());
                }
            });
        }
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    private void createViewer(AnchorPane Pane) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // create the viewer ri components.
                    swingController.setIsEmbeddedComponent(true);
                    PropertiesManager properties = new PropertiesManager(System.getProperties(),
                            ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

                    // read/store the font cache.
                    ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                    new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                    properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1");
                    properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, "true");
                    properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, "true");
                    properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, "true");
                    // hide the status bar
                    properties.set(PropertiesManager.PROPERTY_SHOW_STATUSBAR, "false");
                    // hide a few toolbars, just to show how the prefered size of the viewer changes.
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "true");
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");

                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, "false");

                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ZOOM, "true");


                    swingController.getDocumentViewController().setAnnotationCallback(
                            new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));

                    SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);

                    viewerPanel = factory.buildViewerPanel();
                    viewerPanel.revalidate();

                    SwingNode swingNode = new SwingNode();
                    swingNode.setContent(viewerPanel);
                    AnchorPane.setLeftAnchor(swingNode, 0.0);
                    AnchorPane.setRightAnchor(swingNode, 0.0);
                    AnchorPane.setTopAnchor(swingNode, 0.0);
                    AnchorPane.setBottomAnchor(swingNode, 0.0);
                    Pane.getChildren().add(swingNode);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
