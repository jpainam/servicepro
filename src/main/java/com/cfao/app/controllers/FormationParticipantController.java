package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Created by JP on 9/3/2017.
 */
public class FormationParticipantController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(FormationParticipantController.class);
    private Formation formation = null;

    public TableView<FormationPersonne> participantTable;
    public TableColumn<FormationPersonne, Pays> paysColumn;
    public TableColumn<FormationPersonne, String> telephoneColumn;
    public TableColumn<FormationPersonne, String> emailColumn;
    public TableColumn<FormationPersonne, String> passportColumn;
    public TableColumn<FormationPersonne, String> nomParticipantColumn;
    public TableColumn<FormationPersonne, String> prenomParticipantColumn;
    public TableColumn<FormationPersonne, Societe> societeParticipantColumn;
    public TableColumn<FormationPersonne, FontAwesomeIconView> docParticipantColumn;
    private ObjectProperty<Predicate<FormationPersonne>> participantFilter = new SimpleObjectProperty<>();

    /**
     * Obtenu par injection dans formationController
     */
    private ObservableList<Personne> personneData;

    public FormationParticipantController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/participant.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        participantTable.setTableMenuButtonVisible(true);
        participantTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    private void initComponents() {
        /**  Propriete de la table participant */
        paysColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersonne().getPays()));
        emailColumn.setCellValueFactory(param -> param.getValue().getPersonne().emailProperty());
        telephoneColumn.setCellValueFactory(param -> param.getValue().getPersonne().telephoneProperty());
        nomParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().nomProperty());
        prenomParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().prenomProperty());
        societeParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().societe());
        passportColumn.setCellValueFactory(param -> {
            String passport = param.getValue().getPersonne().getPassport();
            if(passport != null && !passport.isEmpty()) {
                String numpassport = passport.substring(0, passport.lastIndexOf("."));
                return new SimpleStringProperty(numpassport);
            }
            return new SimpleStringProperty();
        });
        docParticipantColumn.setCellValueFactory(param -> {
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
            iconView.setFill(Color.ORANGERED);
            FormationPersonne fp = param.getValue();
            if (fp.getDocument() != null && !fp.getDocument().isEmpty()) {
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                String chemin = path.toString() + File.separator + fp.getDocument();
                File f = new File(chemin);
                if (f.exists() && !f.isDirectory()) {
                    iconView.setFill(Color.FORESTGREEN);
                }
            }
            return new SimpleObjectProperty<>(iconView);
        });

        participantTable.setRowFactory(param -> {
            final TableRow<FormationPersonne> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem visualiserAnalyse = new MenuItem("Visualiser Analyse (*.docx)");

            GlyphsDude.setIcon(visualiserAnalyse, FontAwesomeIcon.PRINT);
            visualiserAnalyse.setOnAction(event -> visualiserDocAction(row.getItem()));
            MenuItem ajouterAnalyse = new MenuItem("Ajouter Analyse (*.docx)");
            GlyphsDude.setIcon(ajouterAnalyse, FontAwesomeIcon.PLUS_SQUARE);
            ajouterAnalyse.setOnAction(event -> ajouterDocAction(row.getItem()));
            MenuItem supprimerAnalyse = new MenuItem("Supprimer Analyse (*.docx)");
            GlyphsDude.setIcon(supprimerAnalyse, FontAwesomeIcon.TRASH);
            supprimerAnalyse.setOnAction(event -> supprimerDocAction(row.getItem()));
            rowMenu.getItems().addAll(visualiserAnalyse, new SeparatorMenuItem(), ajouterAnalyse, supprimerAnalyse);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });


    }

    public void buildTable() {
        participantTable.getItems().clear();
        if (formation.getFormationPersonnes() != null) {
            participantTable.setItems(FXCollections.observableArrayList(formation.getFormationPersonnes()));
        }
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public TableView<FormationPersonne> getParticipantTable() {
        return participantTable;
    }

    public void ajouterDocAction(FormationPersonne fp) {
        if (fp != null) {
            FileChooser choosePic = new FileChooser();
            File file;
            try {
                if ((file = choosePic.showOpenDialog(Main.stage)) != null) {
                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                            File f = new File(path.toString() + File.separator + fp.getDocument());
                            if (f.exists() && !f.isDirectory()) {
                                f.delete();
                            }
                            String chemin = path.toString() + File.separator + file.getName();
                            Path src = file.toPath();
                            Files.copy(src, Paths.get(chemin), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                            fp.setDocument(file.getName());
                            return new Model<>("FormationPersonne").update(fp);
                        }
                    };
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            if (task.getValue()) {
                                participantTable.refresh();
                                ServiceproUtil.notify("Document ajouté avec succès");
                            } else {
                                ServiceproUtil.notify("Erreur d'ajout du document");
                            }
                        }
                    });
                    new Thread(task).start();
                    task.setOnFailed(event1 -> {
                        ServiceproUtil.notify("Erreur dans le thread lors de la sauvegarde du fichier");
                        task.getException().printStackTrace();
                    });

                }
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le participant, titulaire du document");
        }
    }

    public void visualiserDocAction(FormationPersonne fp) {
        if (fp != null) {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
            String document = path.toString() + File.separator + fp.getDocument();
            File f = new File((document));
            if (f.exists() && !f.isDirectory()) {
                ServiceproUtil.openDocument(f);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le participant dont il faut afficher le document d'analyse");
        }
    }

    public void supprimerDocAction(FormationPersonne fp) {
        if (fp != null) {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
            File f = new File(path.toString() + File.separator + fp.getDocument());
            if (f.exists() && !f.isDirectory()) {
                if (f.delete()) {
                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            fp.setDocument("");
                            return new Model<>("FormationPersonne").update(fp);
                        }
                    };
                    new Thread(task).start();
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            if (task.getValue()) {
                                participantTable.refresh();
                                ServiceproUtil.notify("Suppression OK");
                            } else {
                                ServiceproUtil.notify("Erreur de suppression");
                            }
                        }
                    });
                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            ServiceproUtil.notify("Erreur dans the thread de suppression");
                            task.getException().printStackTrace();
                        }
                    });
                }
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le participant dont il faut supprimer le document d'analyse");
        }
    }

    public void participantDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1) {
            FormationPersonne fp = participantTable.getSelectionModel().getSelectedItem();
            formation.getFormationPersonnes().remove(fp);
            participantTable.getItems().remove(fp);
            //personneTable.getItems().add(fp.getPersonne());
            personneData.add(fp.getPersonne());
            //participantTable.getSelectionModel().clearSelection();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new FormationModel().update(formation);
                    return null;
                }
            };
            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    logger.error(task.getException());
                    task.getException().printStackTrace();
                    ServiceproUtil.notify("Erreur de suppression du participant");
                }
            });
        }

    }

    public void setPersonneData(ObservableList<Personne> personneData) {
        this.personneData = personneData;
    }
}
