package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.FormationPersonne;
import com.cfao.app.beans.Personne;
import com.cfao.app.model.FormationModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ButtonUtil;
import com.cfao.app.util.DateTableCellFactory;
import com.cfao.app.util.ServiceproUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/25/2017.
 */
public class CiviliteFormationController extends AnchorPane implements Initializable {

    public TableView<FormationPersonne> formationTable;
    public TableColumn<FormationPersonne, String> codeFormationColumn;
    public TableColumn<FormationPersonne, String> titreFormationColumn;
    public TableColumn<FormationPersonne, LocalDate> datedebutFormationColumn;
    public TableColumn<FormationPersonne, LocalDate> datefinFormationColumn;

    public TableView<Formation> souhaitTable;
    public TableColumn<Formation, String> codeSouhaitColumn;
    public TableColumn<Formation, String> titreSouhaitColumn;
    public TableColumn<Formation, LocalDate> datefinSouhaitColumn;
    public TableColumn<Formation, LocalDate> datedebutSouhaitColumn;

    public Button btnPreviousFormation;
    public Button btnNextFormation;
    public Button btnPrintFormation;

    private Personne personne = null;

    public AnchorPane pdfContainer;


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

        formationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getDocument() != null)
                if (!newValue.getDocument().isEmpty()) {
                    Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                    File f = new File(path.toString() + File.separator + newValue.getDocument());
                    if (f.exists() && !f.isDirectory()) {
                        openDocument(f.toPath().toString());
                    }
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
        ButtonUtil.next(btnNextFormation);
        ButtonUtil.previous(btnPreviousFormation);
        ButtonUtil.print(btnPrintFormation);

        codeFormationColumn.setCellValueFactory(param -> param.getValue().getFormation().codeformationProperty());
        titreFormationColumn.setCellValueFactory(param -> param.getValue().getFormation().titreProperty());
        datedebutFormationColumn.setCellValueFactory(param -> param.getValue().getFormation().datedebutProperty());
        datedebutFormationColumn.setCellFactory(new DateTableCellFactory<FormationPersonne>());
        datefinFormationColumn.setCellValueFactory(param -> param.getValue().getFormation().datefinProperty());
        datefinFormationColumn.setCellFactory(new DateTableCellFactory<>());

        titreSouhaitColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        codeSouhaitColumn.setCellValueFactory(param -> param.getValue().codeformationProperty());
        datedebutSouhaitColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutSouhaitColumn.setCellFactory(new DateTableCellFactory<>());
        datefinSouhaitColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinSouhaitColumn.setCellFactory(new DateTableCellFactory<>());

    }


    public void buildFormation() {
        formationTable.itemsProperty().bind(personne.formationPersonnesProperty());
        //System.err.println(new FormationModel().getFormationsSouhaitees(personne));
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                List<Formation> list = new FormationModel().getFormationsSouhaitees(personne);
                if(list != null) {
                    return FXCollections.observableArrayList(list);
                }
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            if(task.getValue() != null){
                souhaitTable.setItems(task.getValue());
            }else{
                souhaitTable.getItems().clear();
            }
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            ServiceproUtil.notify("Erreur dans le thread de souhait formation");
        });

        if (swingController.getDocument() != null) {
            swingController.closeDocument();
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

    public void previousFormationAction(ActionEvent event) {
    }

    public void nextFormationAction(ActionEvent event) {
    }

    public void printFormationAction(ActionEvent event) {
    }
}