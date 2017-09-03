package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Qcm;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/16/2017.
 */
public class RapportControllerOld implements Initializable {
    public VBox researchPanel;
    public TableView<Personne> personneTable;
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, Societe> personneSocieteColumn;
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableView<Qcm> qcmTable;
    public TableColumn<Qcm, String> titreQcmColumn;
    public AnchorPane rapportDisplay;

    public SearchBox searchBox = new SearchBox();

    final SwingController swingController = new SwingController();
    private JComponent viewerPanel;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        researchPanel.getChildren().addAll(new Label("Civilité"), searchBox);
        initComponent();
        personneTable.setTableMenuButtonVisible(true);
        createViewer(rapportDisplay);
        openDocument("documents.pdf");
    }

    private void initComponent() {
        nomPersonneColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom() + " " + param.getValue().getPrenom()));
        personneSocieteColumn.setCellValueFactory(param -> param.getValue().societe());
        titreFormationColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        titreQcmColumn.setCellValueFactory(param -> param.getValue().titre());
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personne", FXCollections.observableArrayList(new PersonneModel().getList()));
                map.put("formation", FXCollections.observableArrayList(new FormationModel().getList()));
                map.put("qcm", FXCollections.observableArrayList(new Model<Qcm>("Qcm").getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            personneTable.setItems(task.getValue().get("personne"));
            formationTable.setItems(task.getValue().get("formation"));
            qcmTable.setItems(task.getValue().get("qcm"));
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.err.println(task.getException());
                ServiceproUtil.notify("Erreur dans le thread de rapport");
            }
        });
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

    public void openDocument(String document) {
        SwingUtilities.invokeLater(() -> {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
            swingController.openDocument(path.toString() + File.separator + document);
            viewerPanel.revalidate();
        });
    }
}
