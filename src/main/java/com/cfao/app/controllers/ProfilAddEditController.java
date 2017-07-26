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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
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
    private ObservableSet<Competence> selectedItems;


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
        selectedItems = FXCollections.observableSet(new HashSet<>());
        task1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if(profil != null) {
                    for (Competence competence : task1.getValue()) {
                        Iterator<Competence> iterator = profil.getCompetences().iterator();
                        while (iterator.hasNext()) {
                            if (iterator.next().getIdcompetence().equals(competence.getIdcompetence())) {
                                competenceTable.getSelectionModel().select(competence);
                                selectedItems.add(competence);
                            }
                        }
                    }
                }
                possedeColumn.setCellFactory(param -> {
                    BooleanProperty selected = new SimpleBooleanProperty();
                    CheckBoxTableCell<Competence, Competence> cell = new CheckBoxTableCell<>(index -> selected);
                    selected.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            selectedItems.add(cell.getItem());
                            competenceTable.getSelectionModel().select(cell.getItem());
                        } else {
                            selectedItems.remove(cell.getItem());
                            competenceTable.getSelectionModel().clearSelection(cell.getIndex());
                        }
                    });

                    selectedItems.addListener(new SetChangeListener<Competence>() {
                        @Override
                        public void onChanged(Change<? extends Competence> change) {
                            selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem()));
                        }
                    });
                    cell.itemProperty().addListener(new ChangeListener<Competence>() {
                        @Override
                        public void changed(ObservableValue<? extends Competence> observable, Competence oldValue, Competence newValue) {
                            selected.set(newValue != null && selectedItems.contains(newValue));
                        }
                    });
                    return cell;
                });
                possedeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue()));
            }
        });


    }

    public void validerAction(ActionEvent actionEvent) {
        selectedItems.forEach(System.out::println);

        boolean edit = true;
        if (profil == null) {
            profil = new Profil();
            edit = false;
        }

        profil.setAbbreviation(txtAbbreviation.getText());
        profil.setLibelle(txtProfil.getText());
        for(Competence competence : selectedItems){
            if(profil.getOriginalCompetence(competence) == null){
                profil.getCompetences().add(competence);
            }
        }
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
            System.out.println(niveau);
        }
    }
}