package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.model.PersonnelModel;
import com.cfao.app.reports.JasperTableExample;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by JP on 6/19/2017.
 */
public class FormationController implements Initializable {
    public TableView<Formation> formationTable;
    public TableColumn<Formation, String> titreColumn;
    public TableColumn<Formation, LocalDate> datedebutColumn;
    public TableColumn<Formation, LocalDate> datefinColumn;
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
    public Button btnSupprimer;
    public Button btnAnnuler;
    public ListView<Personnel> listeViewFormateurs;
    public ComboBox<Typeformation> comboTypeformation;
    public Button btnAjouterSupport;
    public Button btnSupprimerSupport;
    public Button btnAfficherSupport;
    public TableView<Support> supportTable;
    public TableColumn<Support, String> codeSupportColumn;
    public TableColumn<Support, String> titreSupportColumn;

    private SearchBox searchBox = new SearchBox();

    public Tab tabFormationDetail;
    public Tab tabCompetenceAssociee;
    public Tab tabParticipant;

    public FormationModel formationModel = new FormationModel();
    public int stateBtnNouveau = 0;
    public int stateBtnModifier = 0;
    private ParticipantController participantController;
    private CompetenceFormationController competenceController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();

        formationTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Formation> observable, Formation oldValue, Formation newValue) -> {
            fillFormationFields(formationTable.getSelectionModel().getSelectedItem());
        });
        buildCombo();
        buildTable();

    }

    private void initComponents() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datedebutColumn.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        datedebutColumn.setCellFactory(new DateTableCellFactory());
        datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        datefinColumn.setCellFactory(new DateTableCellFactory());
        codeSupportColumn.setCellValueFactory(param -> param.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Formations : "), searchBox);



        GlyphsDude.setIcon(btnAjouterFormateur, FontAwesomeIcon.USER_PLUS);
        GlyphsDude.setIcon(btnSupprimerFormateur, FontAwesomeIcon.USER_TIMES);
        GlyphsDude.setIcon(tabCompetenceAssociee, FontAwesomeIcon.TASKS);
        GlyphsDude.setIcon(tabParticipant, FontAwesomeIcon.USERS);
        GlyphsDude.setIcon(tabFormationDetail, FontAwesomeIcon.BUILDING_ALT);
        GlyphsDude.setIcon(btnAfficherSupport, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouterSupport, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerSupport, FontAwesomeIcon.MINUS_SQUARE);
        ButtonUtil.next(btnNext);
        ButtonUtil.previous(btnPrevious);
        ButtonUtil.print(btnPrint);
        ButtonUtil.edit(btnModifier);
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.add(btnNouveau);
        ButtonUtil.cancel(btnAnnuler);
        participantController = new ParticipantController();
        competenceController = new CompetenceFormationController();
        tabParticipant.setContent(participantController);
        tabCompetenceAssociee.setContent(competenceController);
    }

    private void buildCombo() {
        Task<ObservableMap<String, ObservableList>> task = new Task<ObservableMap<String, ObservableList>>() {
            @Override
            protected ObservableMap<String, ObservableList> call() throws Exception {
                ObservableMap<String, ObservableList> map = FXCollections.observableHashMap();
                map.put("personnel", FXCollections.observableArrayList((new PersonnelModel()).getList()));
                map.put("modele", FXCollections.observableList((new Model<Modele>("Modele")).getList()));
                map.put("etatformation", FXCollections.observableList((new Model<Etatformation>("Etatformation")).getList()));
                map.put("typeformation", FXCollections.observableList((new Model<Typeformation>("Typeformation")).getList()));
                return map;
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            ObservableMap<String, ObservableList> map = task.getValue();
            comboFormateur.setItems(map.get("personnel"));
            comboModele.setItems(map.get("modele"));
            comboEtatformation.setItems(map.get("etatformation"));
            comboTypeformation.setItems(map.get("typeformation"));
        });
    }

    private void buildTable() {
        btnNouveau.setText(ResourceBundle.getBundle("Bundle").getString("button.new"));
        btnModifier.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        ServiceproUtil.setDisable(false, btnNouveau, btnModifier, btnSupprimer);
        ServiceproUtil.setDisable(true, comboEtatformation, comboFormateur, comboModele, btnAjouterFormateur, btnSupprimerFormateur, comboTypeformation);
        ServiceproUtil.setEditable(false, txtTitre, txtDescription, txtCode, dateDebut, dateFin);
        ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateDebut, dateFin);
        Task<ObservableList<Formation>> task = new Task<ObservableList<Formation>>() {
            @Override
            protected ObservableList<Formation> call() throws Exception {
                return FXCollections.observableList(formationModel.getList());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            formationTable.setItems(task.getValue());
        });
        listeViewFormateurs.getItems().clear();
    }

    private void fillFormationFields(Formation formation) {
        if(formation == null)
            return;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                txtCode.setText(formation.getCodeformation());
                txtTitre.setText(formation.getTitre());
                txtDescription.setText(formation.getDescription());
                comboModele.setValue(formation.getModele());
                comboEtatformation.setValue(formation.getEtatformation());
                comboTypeformation.setValue(formation.getTypeformation());
                listeViewFormateurs.setItems(FXCollections.observableArrayList(formation.getFormateurs()));
                dateDebut.setValue(formation.getDatedebut().toLocalDate());
                dateFin.setValue(formation.getDatefin().toLocalDate());
                supportTable.setItems(FXCollections.observableArrayList(formation.getSupports()));
                System.err.println(formation.getParticipants());
                participantController.setFormation(formation);
                competenceController.setFormation(formation);
                participantController.buildTable();
                competenceController.buildTable();
                return null;
            }
        };
        task.run();
    }


    public void clickNouveau(ActionEvent actionEvent) {
        if (stateBtnNouveau == 0) {
            btnNouveau.setText("Enregistrer/Save");
            btnModifier.setText("Modifier/Edit");
            listeViewFormateurs.getItems().clear();
            ServiceproUtil.setDisable(true, btnModifier, btnSupprimer);
            ServiceproUtil.emptyFields(txtCode, txtDescription, txtTitre, dateFin, dateDebut);
            ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut);
            ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnAjouterFormateur, btnSupprimerFormateur, comboTypeformation);
            stateBtnNouveau = 1;
        } else {
            Formation formation = new Formation();
            formation.setEtatformation(comboEtatformation.getValue());
            formation.setModele(comboModele.getValue());
            formation.setFormateurs(listeViewFormateurs.getItems());
            formation.setSupports(supportTable.getItems());
            formation.setTitre(txtTitre.getText());
            formation.setDescription(txtDescription.getText());
            formation.setCodeformation(txtCode.getText());
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
            formation.setTypeformation(comboTypeformation.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return formationModel.save(formation);
                }
            };
            task.run();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Ajouter avec succees");
                        buildTable();
                    } else {
                        ServiceproUtil.notify("Erreur d'ajout");
                    }
                }
            });
            stateBtnNouveau = 0;
        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (stateBtnModifier == 0) {
            if (formationTable.getSelectionModel().getSelectedItem() != null) {
                btnModifier.setText("Enregistrer/Save");
                btnNouveau.setText("Nouveau/New");
                ServiceproUtil.setDisable(true, btnNouveau, btnSupprimer);
                ServiceproUtil.setEditable(true, txtCode, txtDescription, txtTitre, dateFin, dateDebut);
                ServiceproUtil.setDisable(false, comboModele, comboFormateur, comboEtatformation, btnSupprimerFormateur, btnAjouterFormateur, comboTypeformation);
                stateBtnModifier = 1;
            }
        } else {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            formation.setModele(comboModele.getValue());
            formation.setCodeformation(txtCode.getText());
            formation.setDatedebut(Date.valueOf(dateDebut.getValue()));
            formation.setDatefin(Date.valueOf(dateFin.getValue()));
            formation.setTitre(txtTitre.getText());
            formation.setDescription(txtDescription.getText());
            formation.setEtatformation(comboEtatformation.getValue());
            formation.setFormateurs(listeViewFormateurs.getItems());
            formation.setSupports(supportTable.getItems());
            formation.setTypeformation(comboTypeformation.getValue());
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return formationModel.update(formation);
                }
            };
            task.run();
            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Modification OK");
                    buildTable();
                } else {
                    ServiceproUtil.notify("Erreur de modification");
                }
            });
            stateBtnModifier = 0;
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (formationTable.getSelectionModel().getSelectedItem() != null) {
            Formation formation = formationTable.getSelectionModel().getSelectedItem();
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return formationModel.delete(formation);
                }
            };
            task.run();
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Suppression OK");
                        buildTable();
                    } else {
                        ServiceproUtil.notify("Erreur de suppression");
                    }
                }
            });
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        buildTable();
        stateBtnNouveau = 0;
        stateBtnModifier = 0;
    }

    public void ajouterFormateur(ActionEvent actionEvent) {
        if (comboFormateur.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = comboFormateur.getSelectionModel().getSelectedItem();
            if (!listeViewFormateurs.getItems().contains(personnel)) {
                listeViewFormateurs.getItems().add(personnel);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sélectionner le formateur à ajouter dans la liste des formateurs");
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void supprimerFormateur(ActionEvent actionEvent) {
        if (listeViewFormateurs.getSelectionModel().getSelectedItem() != null) {
            Personnel personnel = listeViewFormateurs.getSelectionModel().getSelectedItem();
            listeViewFormateurs.getItems().remove(personnel);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setTitle("");
            alert.setContentText("Sélectionner le formateur à supprimer dans le tableau des formateurs");
            alert.showAndWait();
        }
    }

    public void ajouterSupportAction(ActionEvent actionEvent) {
        Dialog<Support> dialog = new Dialog<>();
        try {
            dialog.setTitle("Supports - Formation");
            dialog.setHeaderText("Associer des supports à la formation");
            ButtonType okButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            DialogSupportController dialogSupportController = new DialogSupportController();
            dialog.getDialogPane().setContent(dialogSupportController);
            dialog.setResultConverter(new Callback<ButtonType, Support>() {
                @Override
                public Support call(ButtonType param) {
                    return dialogSupportController.getSupport();
                }
            });
            Optional<Support> result = dialog.showAndWait();
            result.ifPresent(new Consumer<Support>() {
                @Override
                public void accept(Support support) {
                    if (!supportTable.getItems().contains(support)) {
                        supportTable.getItems().add(support);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void supprimerSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            Support support = supportTable.getSelectionModel().getSelectedItem();
            supportTable.getItems().remove(support);
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à supprimer de la formation");
        }
    }

    public void afficherSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Support support = supportTable.getSelectionModel().getSelectedItem();
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(support.getLien()));
                System.out.println((new File(support.getLien())).getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à afficher");
        }
    }


    public void printFormation(ActionEvent actionEvent) {
        try {
            new JasperTableExample();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

}
