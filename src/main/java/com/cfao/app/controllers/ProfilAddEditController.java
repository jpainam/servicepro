package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Niveau;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.CompetenceModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/29/2017.
 */
public class ProfilAddEditController extends AnchorPane implements Initializable {
    public TableView<Competence> competenceTable;

    public TableColumn<Competence, String> intituleColumn;
    public TableColumn<Competence, Competence> possedeColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TextField txtProfil;
    public TextField txtAbbreviation;
    public Button btnAnnuler;
    public Button btnValider;
    public ComboBox<Niveau> comboNiveau;
    public StackPane competenceStackPane;

    public VBox vboxRechercheCompetence;
    public SearchBox searchBox = new SearchBox();
    public TableColumn<Competence, String> niveauColumn;
    private ObservableList<Competence> selectedItems;


    private Profil profil = null;

    public ProfilAddEditController(Profil profil) {
        this();
        this.profil = profil;
        initComponents();
        txtAbbreviation.setText(profil.getAbbreviation());
        txtProfil.setText(profil.getLibelle());
    }

    public ProfilAddEditController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profil/add.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        vboxRechercheCompetence.getChildren().setAll(new Label("Compétences : "), searchBox);
        competenceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initComponents();
        ButtonUtil.cancel(btnAnnuler);
        ButtonUtil.add(btnValider);
    }

    private void initComponents() {

        intituleColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
        Task<ObservableList<Niveau>> task = new Task<ObservableList<Niveau>>() {
            @Override
            protected ObservableList<Niveau> call() throws Exception {
                return FXCollections.observableArrayList(new Model<Niveau>("Niveau").getList());
            }
        };
        new Thread(task).start();
        comboNiveau.itemsProperty().bind(task.valueProperty());

        Task<ObservableList<Competence>> task1 = new Task<ObservableList<Competence>>() {
            @Override
            protected ObservableList<Competence> call() throws Exception {
                return FXCollections.observableArrayList(new CompetenceModel().getList());
            }
        };
        new ProgressIndicatorUtil(competenceStackPane, task1);
        competenceTable.itemsProperty().bind(task1.valueProperty());
        new Thread(task1).start();
        competenceTable.setEditable(true);

        competenceColumn.setCellFactory(param -> {
            CheckBoxTableCell<Competence, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(false);
            return cell;
        });
        connaissanceColumn.setCellFactory(param -> {
            CheckBoxTableCell<Competence, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(false);
            return cell;
        });


        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        niveauColumn.setCellValueFactory(param -> param.getValue().getNiveau().libelleProperty());
        selectedItems = FXCollections.observableArrayList();

        task1.setOnSucceeded((WorkerStateEvent event) -> {
            possedeColumn.setCellFactory(param -> {
                BooleanProperty selected = new SimpleBooleanProperty();
                CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> {
                    if(profil != null) {
                        Competence competence = task1.getValue().get(index);
                        if (profil.getCompetences().contains(competence)) {
                            selected.set(true);
                        }
                    }
                    return selected;
                });
                selected.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        if(!selectedItems.contains(cell.getItem())) {
                            selectedItems.add(cell.getItem());
                        }
                        competenceTable.getSelectionModel().select(cell.getItem());
                    } else {
                        if(selectedItems.contains(cell.getItem())) {
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
            possedeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue()));
        });

        task1.setOnFailed(event -> {
            AlertUtil.showErrorMessage(new Exception(task1.getException()));
        });

        task.setOnFailed(event -> {
            AlertUtil.showErrorMessage(new Exception(task.getException()));
        });

    }

    public void validerAction(ActionEvent actionEvent) {
        selectedItems.forEach(System.out::println);
        boolean edit = true;
        if (profil == null) {
            profil = new Profil();
            edit = false;
        }
        profil.setCompetences(selectedItems);
        profil.setAbbreviation(txtAbbreviation.getText());
        profil.setLibelle(txtProfil.getText());
        Model<Profil> model = new Model<>("Profil");
        boolean bool = false;
        String sms = "";
        if (edit) {
            bool = model.update(profil);
            sms = "Modification OK";
        } else {
            sms = "Enregistrement OK";
            bool = model.save(profil);
        }
        if (bool) {
            ServiceproUtil.notify(sms);
        } else {
            ServiceproUtil.notify("Une erreur est survenu");
        }
        StageManager.loadContent("/views/profil/profil.fxml");
    }


    public void annulerAction(ActionEvent event) {
        StageManager.loadContent("/views/profil/profil.fxml");
    }

    public void comboNiveauAction(ActionEvent actionEvent) {
        Niveau niveau = comboNiveau.getSelectionModel().getSelectedItem();
        if (niveau != null) {
            Task<ObservableList<Competence>> task = new Task<ObservableList<Competence>>() {
                @Override
                protected ObservableList<Competence> call() throws Exception {
                    return FXCollections.observableArrayList(new CompetenceModel().findByNiveau(niveau));
                }
            };
            competenceTable.itemsProperty().bind(task.valueProperty());
            new ProgressIndicatorUtil(competenceStackPane, task);
            new Thread(task).start();
        }
    }
}