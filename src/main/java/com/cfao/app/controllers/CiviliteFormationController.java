package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {
    public TableColumn<PersonneCompetence, Competence> intituleCompetenceColumn;
    //public TableColumn<Competence, CompetenceCertification> certificationCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> encoursCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> certifieeCompetenceColumn;
    public TableColumn<PersonneCompetence, Boolean> acertifierCompetenceColumn;

    public TableView<PersonneCompetence> competenceTable;

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

    public Button btnSupprimerCompetence;
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
        ButtonUtil.delete(btnSupprimerCompetence);
        formationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Formation>() {
            @Override
            public void changed(ObservableValue<? extends Formation> observable, Formation oldValue, Formation newValue) {
                //String url = ResourceBundle.getBundle("Bundle").getString("document.dir");
                //openDocument(getClass().getResource(url + "documents.pdf").getPath());

                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                if (!path.toFile().exists()) {
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

        /*competenceTable.setRowFactory(param -> {
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
        });*/
        codeFormationColumn.setCellValueFactory(param -> param.getValue().codeformationProperty());
        titreFormationColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        datedebutFormationColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutFormationColumn.setCellFactory(new DateTableCellFactory<Formation>());
        datefinFormationColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinFormationColumn.setCellFactory(new DateTableCellFactory<Formation>());
        intituleCompetenceColumn.setCellValueFactory(param -> param.getValue().competence());
        setColumnFactories();
        encoursCompetenceColumn.setCellValueFactory(param -> param.getValue().encoursProperty());
        acertifierCompetenceColumn.setCellValueFactory(param -> param.getValue().acertifierProperty());
        certifieeCompetenceColumn.setCellValueFactory(param -> param.getValue().certifieeProperty());
    }

    private void setColumnFactories() {
        encoursCompetenceColumn.setCellFactory(param -> {
            CheckBoxTableCell<PersonneCompetence, Boolean> cell = new CheckBoxTableCell<>();
            cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (cell.getTableRow().getIndex() >= 0 && cell.getTableView().getItems().size() > 0) {
                    PersonneCompetence pp = cell.getTableView().getItems().get(cell.getTableRow().getIndex());
                    pp.setEncours(newValue);
                    if (newValue) {
                        pp.setCertifiee(false);
                        pp.setAcertifier(false);
                    }
                    cell.setItem(newValue);
                }
            });
            return cell;
        });
        acertifierCompetenceColumn.setCellFactory(param -> {
            CheckBoxTableCell<PersonneCompetence, Boolean> cell = new CheckBoxTableCell<>();
            cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (cell.getTableRow().getIndex() >= 0 && cell.getTableView().getItems().size() > 0) {
                    PersonneCompetence pp = cell.getTableView().getItems().get(cell.getIndex());
                    pp.setAcertifier(newValue);
                    if (newValue) {
                        pp.setCertifiee(false);
                        pp.setEncours(false);
                    }
                    cell.setItem(newValue);
                }
            });
            return cell;
        });
        certifieeCompetenceColumn.setCellFactory(param -> {
            CheckBoxTableCell<PersonneCompetence, Boolean> cell = new CheckBoxTableCell<>();
            cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (cell.getTableRow().getIndex() >= 0 && cell.getTableView().getItems().size() > 0) {
                    PersonneCompetence pp = cell.getTableView().getItems().get(cell.getTableRow().getIndex());
                    pp.setCertifiee(newValue);
                    if (newValue) {
                        pp.setEncours(false);
                        pp.setAcertifier(false);
                    }
                    cell.setItem(newValue);
                }
            });
            return cell;
        });


    }

    public void buildFormation() {
        competenceTable.itemsProperty().bind(personne.personneCompetencesProperty());
        // Historique des formation
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableArrayList(new FormationModel().getFormationsByPersonne(personne));
            }
        };

        formationTable.itemsProperty().bind(task.valueProperty());
        ProgressIndicatorUtil.show(historiqueStackPane, task);
        new Thread(task).start();

        task.setOnFailed(event -> {
            ServiceproUtil.notify("Erreur dans le thread de certification");
            task.getException().printStackTrace();
            System.err.println(task.getException());
        });
    }


    public void editerCertificationCompetence(ActionEvent event) {
        if (personne != null) {
            if (!editerCertification) {
                competenceTable.setEditable(true);
                btnEditerCertification.setText(ResourceBundle.getBundle("Bundle").getString("button.save.fr"));
                editerCertification = true;
            } else {

                /*for(PersonneCompetence pp : competenceTable.getItems()){
                    System.err.println(pp.getCompetence() + "=>" + pp.getCompetenceCertification().getCertification());
                }*/
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return new PersonneModel().save(personne);
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
                task.setOnFailed(event12 -> {
                    ServiceproUtil.notify("Erreur dans le Thread de formation certification");
                    task.getException().printStackTrace();
                    System.err.println(task.getException());
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

    public void supprimerCompetenceAction(ActionEvent event) {
        PersonneCompetence pp = competenceTable.getSelectionModel().getSelectedItem();
        if (pp != null) {
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new Model<PersonneCompetence>().delete(pp);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                if (task.getValue()) {
                    competenceTable.getItems().remove(pp);
                    setColumnFactories();
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            });
            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    task.getException().printStackTrace();
                    System.err.println(task.getException());

                }
            });
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la compétence à retirer de la civilité");
        }
    }

    public void filterCertification(ActionEvent event) {
        CompetenceCertification certification = comboStatut.getValue();
        if (certification != null) {
            Task<ObservableList<PersonneCompetence>> task = new Task<ObservableList<PersonneCompetence>>() {
                @Override
                protected ObservableList<PersonneCompetence> call() throws Exception {
                    return FXCollections.observableArrayList(new PersonneModel().getCompetencesByCertification(personne, certification));
                }
            };
            new Thread(task).start();
            setColumnFactories();

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
                    properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SEARCH, "false");
                    properties.set(PropertiesManager.PROPERTY_HIDE_UTILITYPANE, "false");


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
