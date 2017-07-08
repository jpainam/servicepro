package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.beans.*;
import com.cfao.app.model.*;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.prism.impl.FactoryResetException;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.textfield.TextFields;

import javax.jws.WebParam;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    public TableView<Formation> formationTable;
    public TableColumn titreColumn;
    public TableColumn datedebutColumn;
    public TableColumn datefinColumn;
    public TextField txtCode;
    public TextField txtTitre;
    public DatePicker dateDebut;
    public DatePicker dateFin;
    public TextArea txtDescription;
    public ComboBox<Modele> comboModele;
    public ComboBox<Etatformation> comboEtatformation;
    public ComboBox<Personnel> comboFormateur;
    public Button btnAjouterFormateur;
    public Button btnSupprimerFormateur;
    public HBox researchBox;
    public Button btnPrevious;
    public Button btnNext;
    public Button btnPrint;
    public Button btnNouveau;
    public Button btnModifier;
    public Button btnSuppr;
    public Button btnAnnuler;
    public ListView<Personnel> listeViewFormateurs;
    public HBox hboxCompetenceAssociee;
    private SearchBox searchBox = new SearchBox();
    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;
    SearchBox searchBoxAssocie = new SearchBox();

    public Model<Formation> modelFormation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        formationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> fillFormationFields());
        tabParticipant.setContent(new ListSelectionView<Formation>());
        buildCombo();
    }

    private void initComponents() {
        comboModele.setEditable(true);
        comboEtatformation.setEditable(true);
        comboFormateur.setEditable(true);
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datedebutColumn.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);

        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.USER_PLUS);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.USER_TIMES);
        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.HAND_LIZARD_ALT);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);

        GlyphsDude.setIcon(btnNext, FontAwesomeIcon.ARROW_RIGHT);
        GlyphsDude.setIcon(btnPrevious, FontAwesomeIcon.ARROW_LEFT);
        GlyphsDude.setIcon(btnPrint, FontAwesomeIcon.PRINT);
        GlyphsDude.setIcon(btnSuppr, FontAwesomeIcon.TRASH);
        GlyphsDude.setIcon(btnModifier, FontAwesomeIcon.PENCIL);
        GlyphsDude.setIcon(btnNouveau, FontAwesomeIcon.FILE);
        GlyphsDude.setIcon(btnAnnuler, FontAwesomeIcon.SHARE_SQUARE);

        searchBoxAssocie.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchBoxAssocie, Priority.ALWAYS);
        hboxCompetenceAssociee.getChildren().addAll(new Label("Compétences associées : "), searchBoxAssocie);
    }

    private void buildCombo() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                modelFormation = new Model<>();
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableList(new Model<Personnel>(Model.getBeanPath("Personnel")).getList()));
                map.put("modele", FXCollections.observableList(new Model<Modele>(Model.getBeanPath("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList(new Model<Etatformation>(Model.getBeanPath("Etatformation")).getList()));
                map.put("formation", FXCollections.observableList(new Model<Formation>(Model.getBeanPath("Formation")).getList()));
                return map;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            ObservableMap<String, ObservableList> map = task.getValue();
            comboFormateur.setItems(map.get("personnel"));
            comboModele.setItems(map.get("modele"));
            comboEtatformation.setItems(map.get("etatformation"));
            formationTable.setItems(map.get("formation"));
        });
    }


    private void fillFormationFields() {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {
            Formation formation = (Formation) formationTable.getSelectionModel().getSelectedItem();
            txtCode.setText(formation.getCodeformation());
            txtTitre.setText(formation.getTitre());
            txtDescription.setText(formation.getDescription());
            listeViewFormateurs.setItems(FXCollections.observableList(formation.getFormateurs()));
        }
    }


    public void nouveauAction(ActionEvent actionEvent) {
    }


    public void supprimerAction(ActionEvent actionEvent) {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {

        } else {

        }
    }

    public void clickNouveau(ActionEvent actionEvent) {
    }

    public void clickModifier(ActionEvent actionEvent) {
    }

    public void clickSupprimer(ActionEvent actionEvent) {
    }

    public void clickAnnuler(ActionEvent actionEvent) {
    }

    public void ajouterFormateur(ActionEvent actionEvent) {
        if (comboFormateur.getSelectionModel().getSelectedItem() != null && formationTable.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = comboFormateur.getSelectionModel().getSelectedItem();
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            Model<Formateur> model = new Model<Formateur>(Model.getBeanPath("Formateur"));

            Task<ObservableList<Personnel>> task = new Task<ObservableList<Personnel>>() {
                @Override
                protected ObservableList<Personnel> call() throws Exception {
                    return FXCollections.observableArrayList();
                }
            };
            task.run();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    listeViewFormateurs.setItems(task.getValue());
                    formationTable.getSelectionModel().getSelectedItem().formateursProperty().bind(task.valueProperty());
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sélectionner le formateur à ajouter dans la liste des formateurs");
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void supprimerFormateur(ActionEvent actionEvent) {
        if (listeViewFormateurs.getSelectionModel().getSelectedItem() != null && formationTable.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = listeViewFormateurs.getSelectionModel().getSelectedItem();
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            FormationModel formationModel = new FormationModel(Model.getBeanPath("Formation"));
            if (formationModel.deleteFormateurs(personnel, formation)) {
                ServiceproUtil.notify("Suppression OK");
            } else {
                ServiceproUtil.notify("Erreur de suppression");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setTitle("");
            alert.setContentText("Sélectionner le formateur à supprimer dans le tableau des formateurs");
            alert.showAndWait();
        }
    }
}
