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
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
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
        /*WebEngine engine = webView.getEngine();
        String url = getClass().getResource("/pdfjs/web/viewer.html").toExternalForm();
        engine.setUserStyleSheetLocation(getClass().getResource("/pdfjs/web/viewer.css").toExternalForm());

        engine.setJavaScriptEnabled(true);
        byte[] data = null;
        try {
            // readFileToByteArray() comes from commons-io library
            //File f = new File("src/main/resources/documents/Final2016.pdf");

            File f = new File(URI.create(getClass().getResource("/documents/Final2016.pdf").toExternalForm()));
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
        */
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
/*
                // add toolbar to the top.
                FlowPane toolBarFlow = new FlowPane();
                JToolBar mainToolbar = factory.buildCompleteToolBar(true);
                buildJToolBar(toolBarFlow, mainToolbar);
                borderPane.setTop(toolBarFlow);

                // main utility pane and viewer
                SwingNode swingNode = new SwingNode();
                viewerPanel = factory.buildUtilityAndDocumentSplitPane(true);
                swingNode.setContent(viewerPanel);
                borderPane.setCenter(swingNode);

                // the page view menubar
                FlowPane statusBarFlow = new FlowPane();
                buildButton(statusBarFlow, factory.buildPageViewSinglePageNonConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewSinglePageConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewFacingPageNonConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewFacingPageConToggleButton());
                borderPane.setBottom(statusBarFlow);

*/

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDocument(String document) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingController.openDocument(document);
                viewerPanel.revalidate();
            }
        });
    }

    private void setColumnFactory(){
        encoursCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        acertifierCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        certifieCompetenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        /*
        selectedItems = FXCollections.observableArrayList();
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            possedeCompetence.setCellFactory((TableColumn<Competence, Competence> param) -> {
                BooleanProperty selected = new SimpleBooleanProperty();
                CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {

                    Competence competence = task.getValue().get(index);
                    if (formation.getCompetences().contains(competence)) {
                        selected.set(true);
                    }

                    return selected;
                });
                selected.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        if (!selectedItems.contains(cell.getItem())) {
                            selectedItems.add(cell.getItem());
                        }
                        competenceTable.getSelectionModel().select(cell.getItem());
                    } else {
                        if (selectedItems.contains(cell.getItem())) {
                            selectedItems.remove(cell.getItem());
                        }
                        competenceTable.getSelectionModel().clearSelection(cell.getIndex());
                    }
                });

                selectedItems.addListener((ListChangeListener<Competence>) change -> {
                    selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem()));
                });
                cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                    selected.set(newValue != null && selectedItems.contains(newValue));
                });
                return cell;
            });
        });
        */
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
        intituleCompetenceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        new Thread(task).start();
        setColumnFactory();
        competenceTable.setRowFactory(new Callback<TableView<Competence>, TableRow<Competence>>() {
            @Override
            public TableRow<Competence> call(TableView<Competence> param) {
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
        openDocument(getClass().getResource("/documents/documents.pdf").getPath());
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
                for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                    if (pc.getCompetence().equals(competence) && pc.getCompetenceStatut().getStatut().equals(Constante.COMPETENCE_ENCOURS)) {
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
        //webView.getEngine().executeScript("changeLanguage('" + locale.toLanguageTag() + "')");
    }
}
