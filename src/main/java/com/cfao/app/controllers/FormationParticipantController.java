package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Created by JP on 7/13/2017.
 */
public class FormationParticipantController extends AnchorPane implements Initializable {
    private Formation formation = null;
    private FormationModel formationModel = new FormationModel();
    private PersonneModel personneModel = new PersonneModel();

    public TableView<FormationPersonne> participantTable;
    public TableColumn<FormationPersonne, String> matriculeParticipantColumn;
    public TableColumn<FormationPersonne, String> nomParticipantColumn;
    public TableColumn<FormationPersonne, String> prenomParticipantColumn;
    public TableColumn<FormationPersonne, Societe> societeParticipantColumn;
    public TableColumn<FormationPersonne, FontAwesomeIconView> docParticipantColumn;

    public Button btnPreviousParticipant;
    public Button btnNextParticipant;
    public Button btnPrintParticipant;
    public Button btnModifierParticipant;
    public Button btnAnnulerParticipant;
    public Button personneToParticipant;
    public Button personneToParticipantAll;
    public Button participantToPersonne;
    public Button participantToPersonneAll;

    public Button btnVisualiserDoc;
    public Button btnAjouterDoc;
    public Button btnSupprimerDoc;

    public VBox vboxSearchPersonne;
    public TableView<Personne> personneTable;
    public StackPane personneStackPane;


    public TableColumn<Personne, String> matriculePersonneColumn;
    public TableColumn<Personne, String> nomPersonneColumn;
    public TableColumn<Personne, String> prenomPersonneColumn;
    public TableColumn<Personne, Label> potentielPersonneColumn;
    private ObjectProperty<Predicate<Personne>> personneFilter = new SimpleObjectProperty<>();
    private ObjectProperty<Predicate<FormationPersonne>> participantFilter = new SimpleObjectProperty<>();

    public int stateBtnModifierParticipant = 0;
    // search personnes
    SearchBox searchBox = new SearchBox();


    private ObservableList<Personne> personneData = FXCollections.observableArrayList();

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
        personneTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        vboxSearchPersonne.getChildren().addAll(new Label("Personnes : (# pour potentiels)"), searchBox);

        personneFilter.bind(Bindings.createObjectBinding(() -> (Predicate<Personne>) personne -> {
                    if (comparePersonne(personne, searchBox.getText())) {
                        return true;
                    }
                    return false;
                }, searchBox.textProperty())
        );
    }

    private boolean comparePersonne(Personne personne, String newValue) {
        newValue = newValue.toLowerCase();
        if (personne.getNom().toLowerCase().contains(newValue) || personne.getPrenom().contains(newValue)) {
            return true;
        }
        if (personne.getSociete() != null && personne.getSociete().toString().toLowerCase().contains(newValue)) {
            return true;
        }
        if (personne.getSection() != null && personne.getSection().toString().toLowerCase().contains(newValue)) {
            return true;
        }
        if (newValue.contains("#")) {
            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                if (formation.getCompetences().contains(pc.getCompetence()) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void buildTable() {
        if (formation == null)
            return;
        potentielPersonneColumn.setCellValueFactory(param -> {
            Personne personne = param.getValue();
            Label label = new Label("N");
            FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.USER);
            formation.getCompetences();
            for (PersonneCompetence pc : personne.getPersonneCompetences()) {
                if (formation.getCompetences().contains(pc.getCompetence()) && pc.getCompetenceCertification().getCertification().equals(Constante.COMPETENCE_ACERTIFIER)) {
                    iconView.setFill(Color.FORESTGREEN);
                    label.setText("P");
                    break;
                }
            }
            label.setGraphic(iconView);
            return new SimpleObjectProperty<>(label);
        });

        Task<ObservableList<Personne>> task = new Task<ObservableList<Personne>>() {
            @Override
            protected ObservableList<Personne> call() throws Exception {
                if (formation.getFormationPersonnes().isEmpty()) {
                    return FXCollections.observableArrayList(personneModel.getList());
                } else {
                    return FXCollections.observableArrayList(formationModel.getNonParticipants(formation));
                }
            }
        };
        //personneTable.itemsProperty().bind(task.valueProperty());
        ProgressIndicatorUtil.show(personneStackPane, task);
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                personneData.addAll(task.getValue());
                FilteredList<Personne> filteredList = new FilteredList<Personne>(personneData, p -> true);
                SortedList<Personne> sortedList = new SortedList<Personne>(filteredList);
                sortedList.comparatorProperty().bind(personneTable.comparatorProperty());
                personneTable.setItems(sortedList);
                filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> personneFilter.get(), personneFilter));
            }
        });
        participantTable.setItems(FXCollections.observableArrayList(formation.getFormationPersonnes()));
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);
    }

    private void initComponents() {
        ServiceproUtil.setDisable(true, participantToPersonne, participantToPersonneAll, personneToParticipant, personneToParticipantAll);
        ButtonUtil.plusIcon(btnAjouterDoc);
        ButtonUtil.delete(btnSupprimerDoc);
        ButtonUtil.add(btnModifierParticipant);
        ButtonUtil.cancel(btnAnnulerParticipant);
        ButtonUtil.angle_left(participantToPersonne);
        ButtonUtil.angle_right(personneToParticipant);
        ButtonUtil.angle_double_left(participantToPersonneAll);
        ButtonUtil.angle_double_right(personneToParticipantAll);
        ButtonUtil.next(btnNextParticipant);
        ButtonUtil.previous(btnPreviousParticipant);
        ButtonUtil.print(btnPrintParticipant, btnVisualiserDoc);

        /**  Propriete de la table participant */
        matriculeParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().matriculeProperty());
        nomParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().nomProperty());
        prenomParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().prenomProperty());
        societeParticipantColumn.setCellValueFactory(param -> param.getValue().getPersonne().societe());
        matriculePersonneColumn.setCellValueFactory(param -> param.getValue().matriculeProperty());
        nomPersonneColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        prenomPersonneColumn.setCellValueFactory(param -> param.getValue().prenomProperty());
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

    }

    public void participantDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1 && stateBtnModifierParticipant == 1) {
            FormationPersonne fp = participantTable.getSelectionModel().getSelectedItem();
            participantTable.getItems().remove(fp);
            //personneTable.getItems().add(fp.getPersonne());
            personneData.add(fp.getPersonne());
            //participantTable.getSelectionModel().clearSelection();
        }
    }

    public void personneDoubleClick(MouseEvent event) {
        if (event.getClickCount() > 1 && stateBtnModifierParticipant == 1) {
            this.move(personneTable, participantTable);
            //personneTable.getSelectionModel().clearSelection();
        }
    }

    public void personneToParticipantAction(ActionEvent actionEvent) {
        if (personneTable.getSelectionModel().getSelectedItem() != null) {
            this.move(personneTable, participantTable);
            //personneTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la personne à ajouter");
        }
    }

    public void personneToParticipantAllAction(ActionEvent actionEvent) {
        this.move(personneTable, participantTable, new ArrayList(this.personneTable.getItems()));
        //personneTable.getSelectionModel().clearSelection();
    }

    public void participantToPersonneAction(ActionEvent actionEvent) {
        if (participantTable.getSelectionModel().getSelectedItem() != null) {
            List<FormationPersonne> selectedItems = new ArrayList<>(participantTable.getSelectionModel().getSelectedItems());
            Iterator<FormationPersonne> iterator = selectedItems.iterator();
            while (iterator.hasNext()) {
                FormationPersonne fp = iterator.next();
                participantTable.getItems().remove(fp);
                //personneTable.getItems().add(fp.getPersonne());
                personneData.add(fp.getPersonne());
            }
            //participantTable.getSelectionModel().clearSelection();
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le particpant à exclure de la liste");
        }
    }

    public void participantToPersonneAllAction(ActionEvent actionEvent) {
        List<FormationPersonne> items = new ArrayList<>(this.participantTable.getItems());
        Iterator<FormationPersonne> iterator = items.iterator();
        while (iterator.hasNext()) {
            FormationPersonne fp = iterator.next();
            participantTable.getItems().remove(fp);
            //personneTable.getItems().add(fp.getPersonne());
            personneData.add(fp.getPersonne());
        }
        //participantTable.getSelectionModel().clearSelection();
    }

    private void move(TableView<Personne> viewA, TableView<FormationPersonne> viewB) {
        List<Personne> selectedItems = new ArrayList(viewA.getSelectionModel().getSelectedItems());
        this.move(viewA, viewB, selectedItems);
    }

    private void move(TableView<Personne> viewA, TableView<FormationPersonne> viewB, List<Personne> items) {
        Iterator<Personne> var4 = items.iterator();
        while (var4.hasNext()) {
            Personne item = var4.next();
            FormationPersonne fp = new FormationPersonne();
            fp.setPersonne(item);
            fp.setFormation(formation);
            //viewA.getItems().remove(item);
            personneData.remove(item);
            viewB.getItems().add(fp);
        }
    }


    public void modifierParticipant(ActionEvent actionEvent) {
        if (formation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation");
            return;
        }
        if (stateBtnModifierParticipant == 0) {
            ServiceproUtil.setDisable(false, participantToPersonneAll, participantToPersonne, personneToParticipantAll, personneToParticipant);
            stateBtnModifierParticipant = 1;
            btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
        } else {
            btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
            ServiceproUtil.setDisable(true, participantToPersonneAll, participantToPersonne, personneToParticipantAll, personneToParticipant);
            formation.getPersonnes().clear();
            for (FormationPersonne fp : participantTable.getItems()) {
                formation.getPersonnes().add(fp.getPersonne());
            }
            if (formationModel.update(formation)) {
                ServiceproUtil.notify("Participants ajoutés avec succès");
            } else {
                ServiceproUtil.notify("Une erreur est survenue");
            }
            stateBtnModifierParticipant = 0;
        }
    }

    public void annulerParticipant(ActionEvent actionEvent) {
        participantTable.getSelectionModel().clearSelection();
        personneTable.getSelectionModel().clearSelection();
        btnModifierParticipant.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        stateBtnModifierParticipant = 0;
        buildTable();
    }

    public void supprimerDocAction(ActionEvent event) {
        if( stateBtnModifierParticipant != 0)
            return;
        FormationPersonne fp = participantTable.getSelectionModel().getSelectedItem();
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

    public void ajouterDocAction(ActionEvent event) {
        if( stateBtnModifierParticipant != 0)
            return;
        FormationPersonne fp = participantTable.getSelectionModel().getSelectedItem();
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

    public void visualiserDocAction(ActionEvent event) {
        if( stateBtnModifierParticipant != 0)
            return;
        FormationPersonne fp = participantTable.getSelectionModel().getSelectedItem();
        if (fp != null) {
            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
            String document = path.toString() + File.separator + fp.getDocument();
            File f = new File((document));
            if(f.exists() && !f.isDirectory()) {
                ServiceproUtil.openDocument(f);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le participant dont il faut afficher le document d'analyse");
        }
    }
}
