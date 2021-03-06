package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Niveau;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/14/2017.
 */
public class CompetenceController implements Initializable {
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSupprimer;
    public TableView<Competence> competenceTable;
    public TableColumn<Competence, Void> numeroColumn;
    public TableColumn<Competence, String> libelleColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public Button btnAnnuler;
    public SearchBox searchBox = new SearchBox();
    public VBox researchBox;
    public StackPane competenceStackPane;
    public Tab competenceTabDetails;
    public Tab competenceTabPersonne;
    public TableView<Formation> formationTable;
    public StackPane formationStackPane;
    public TableColumn<Formation, String> titreFormationColumn;
    public TableColumn<Formation, LocalDate> datedebutFormationColumn;
    public TableColumn<Formation, LocalDate> datefinFormationColumn;
    public TableColumn<Profil, String> libelleProfilColumn;
    public TableView<Profil> profilTable;
    public StackPane profilStackPane;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public TableColumn<Competence, String> niveauCompetenceColumn;
    public TextField txtLibelleCompetence;
    public ComboBox<Niveau> comboNiveau;
    public CheckBox chkCompetence;
    public CheckBox chkConnaissance;
    public int stateBtnModifier = 0;
    public int stateBtnAjouter = 0;
    public VBox vboxSupport;

    CompetencePersonneController personneController;
    CompetenceSupportController supportController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        buildCompetenceTable();
    }

    private void initComponents() {
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.add(btnNouveau);
        ButtonUtil.cancel(btnAnnuler);
        ButtonUtil.print(btnPrint);
        ButtonUtil.next(btnNext);
        ButtonUtil.previous(btnPrevious);
        ServiceproUtil.setEditable(false, txtLibelleCompetence);
        ServiceproUtil.setDisable(true, chkCompetence, chkConnaissance, comboNiveau);
        personneController = new CompetencePersonneController();
        supportController = new CompetenceSupportController();
        competenceTabPersonne.setContent(personneController);
        vboxSupport.getChildren().setAll(supportController);
        ButtonUtil.detailsTab(competenceTabDetails);
        GlyphsDude.setIcon(competenceTabPersonne, FontAwesomeIcon.USERS);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Liste des compétences : "), searchBox);
        competenceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buildFormationTable(newValue);
            buildPersonneTable(newValue);
        });
        titreFormationColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        datedebutFormationColumn.setCellValueFactory(param -> param.getValue().datedebutProperty());
        datedebutFormationColumn.setCellFactory(new DateTableCellFactory());
        datefinFormationColumn.setCellValueFactory(param -> param.getValue().datefinProperty());
        datefinFormationColumn.setCellFactory(new DateTableCellFactory());
        niveauCompetenceColumn.setCellValueFactory(param -> param.getValue().getNiveau().libelleProperty());

        libelleProfilColumn.setCellValueFactory(param -> param.getValue().libelleProperty());

    }

    private void buildCompetenceTable() {

        numeroColumn.setCellFactory(col -> {
            TableCell<Competence, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });

        libelleColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());

        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType() != null) {
                if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                    return new SimpleBooleanProperty(true);
                }
            }
            return new SimpleBooleanProperty(false);

        });
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType() != null) {
                if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                    return new SimpleBooleanProperty(true);
                }
            }
            return new SimpleBooleanProperty(false);

        });
        CompetenceModel competenceModel = new CompetenceModel();
        Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(competenceModel.getList());
            }
        };
        competenceTable.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                competenceTable.getSelectionModel().selectFirst();

            }
        });
        ProgressIndicatorUtil.show(competenceStackPane, task);
        new Thread(task).start();
        Task<ObservableList<Niveau>> task1 = new Task<ObservableList<Niveau>>() {
            @Override
            protected ObservableList<Niveau> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Niveau>("Niveau").getList());
            }
        };
        comboNiveau.itemsProperty().bind(task1.valueProperty());
        new Thread(task1).start();
    }

    public void buildFormationTable(Competence competence) {
        if (competence == null)
            return;
        txtLibelleCompetence.setText(competence.getDescription());
        comboNiveau.setValue(competence.getNiveau());
        chkCompetence.setSelected(false);
        chkConnaissance.setSelected(false);
        if (competence.getType() != null) {
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                chkConnaissance.setSelected(true);
            }
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                chkCompetence.setSelected(true);
            }
        }
        if(competence.getFormations() != null) {
            formationTable.setItems(FXCollections.observableArrayList(competence.getFormations()));
        }
        if(competence.getProfils() != null) {
            profilTable.setItems(FXCollections.observableArrayList(competence.getProfils()));
        }
    }

    private void buildPersonneTable(Competence competence) {
        personneController.setCompetence(competence);
        supportController.setCompetence(competence);
        personneController.buildTable();
        supportController.buildTable();
    }

    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnAjouter == 0) {
            ButtonUtil.save(btnNouveau);
            ServiceproUtil.emptyFields(txtLibelleCompetence);
            chkConnaissance.setSelected(false);
            chkCompetence.setSelected(false);
            ServiceproUtil.setEditable(true, txtLibelleCompetence);
            ServiceproUtil.setDisable(false, comboNiveau, chkConnaissance, chkCompetence);
            stateBtnAjouter = 1;
        } else {
            Competence competence = new Competence();
            if (chkCompetence.isSelected() && chkConnaissance.isSelected()) {
                competence.setType(Constante.CONNAISSANCE_COMPETENCE);
            } else if (chkConnaissance.isSelected()) {
                competence.setType(Constante.CONNAISSANCE);
            } else if (chkCompetence.isSelected()) {
                competence.setType(Constante.COMPETENCE);
            }
            competence.setDescription(txtLibelleCompetence.getText());
            competence.setNiveau(comboNiveau.getValue());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Competence>("Competence").save(competence);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                ServiceproUtil.notify("Ajout OK");
                //StageManager.loadContent("/views/competence/competence.fxml");
                competenceTable.getItems().add(competence);
                ButtonUtil.add(btnNouveau);
                stateBtnAjouter = 0;
                competenceTable.getSelectionModel().select(competence);
            });
            task.setOnFailed(event -> {
                ServiceproUtil.notify("Une erreur s'est produite");
                task.getException().printStackTrace();
                System.err.println(task.getException());
            });
            /*stateBtnAjouter = 0;
            btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.add"));/*/
        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (competenceTable.getSelectionModel().getSelectedItem() == null)
            return;
        if (stateBtnModifier == 0) {
            ButtonUtil.save(btnModifier);
            ServiceproUtil.setEditable(true, txtLibelleCompetence);
            ServiceproUtil.setDisable(false, comboNiveau, chkConnaissance, chkCompetence);
            stateBtnModifier = 1;
        } else {
            Competence competence = competenceTable.getSelectionModel().getSelectedItem();
            if (chkCompetence.isSelected() && chkConnaissance.isSelected()) {
                competence.setType(Constante.CONNAISSANCE_COMPETENCE);
            } else if (chkConnaissance.isSelected()) {
                competence.setType(Constante.CONNAISSANCE);
            } else if (chkCompetence.isSelected()) {
                competence.setType(Constante.COMPETENCE);
            }
            competence.setDescription(txtLibelleCompetence.getText());
            competence.setNiveau(comboNiveau.getValue());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Competence>("Competence").update(competence);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ServiceproUtil.notify("Modification OK");
                    ButtonUtil.edit(btnModifier);
                    stateBtnModifier = 0;
                    competenceTable.getSelectionModel().select(competence);
                    //StageManager.loadContent("/views/competence/competence.fxml");
                }
            });
            task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ServiceproUtil.notify("Une erreur s'est produite");
                }
            });
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (competenceTable.getSelectionModel().getSelectedItem() != null) {
            Competence competence = competenceTable.getSelectionModel().getSelectedItem();
            boolean okay = AlertUtil.showConfirmationMessage("Suppression", "Etes vous sûr de vouloir supprimer " + competence);
            if (okay) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        new Model<Competence>("Competence").delete(competence);
                        return null;
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ServiceproUtil.notify("Suppression OK");
                        competenceTable.getItems().remove(competence);
                    }
                });
                task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ServiceproUtil.notify("Erreur de suppression");
                    }
                });

            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la compétence à supprimer");
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        StageManager.loadContent("/views/competence/competence.fxml");
    }

    public void previousAction(ActionEvent event) {
        if (competenceTable.getSelectionModel().getSelectedIndex() > 0) {
            competenceTable.getSelectionModel().selectPrevious();
        }
    }

    public void nextAction(ActionEvent event) {
        if (competenceTable.getSelectionModel().getSelectedIndex() < competenceTable.getItems().size()) {
            competenceTable.getSelectionModel().selectNext();
        }
    }

    public void printAction(ActionEvent event) {

    }
}
