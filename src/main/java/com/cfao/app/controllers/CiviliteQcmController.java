package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.PersonneQcm;
import com.cfao.app.beans.Qcm;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/2/2017.
 */
public class CiviliteQcmController extends AnchorPane implements Initializable {
    public Button btnPrint;
    public Button btnDeaffecterQcm;
    public Button btnAffecterQcm;
    public TableView<PersonneQcm> qcmTable;
    public TableColumn<PersonneQcm, Qcm> titreColumn;
    public TableColumn<PersonneQcm, String> noteColumn;
    public TableColumn<PersonneQcm, LocalDate> dateQcmColumn;
    private Personne personne = null;
    public StackPane qcmDiagramPane;
    public CiviliteQcmDiagram qcmDiagram;

    public CiviliteQcmController(Personne personne) {
        this();
        this.personne = personne;
    }

    public CiviliteQcmController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/qcm.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
        qcmDiagram = new CiviliteQcmDiagram();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ButtonUtil.plusIcon(btnAffecterQcm);
        ButtonUtil.minusIcon(btnDeaffecterQcm);
        ButtonUtil.print(btnPrint);
        titreColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getQcm()));
        dateQcmColumn.setCellValueFactory(param -> param.getValue().dateqcmProperty());
        dateQcmColumn.setCellFactory(new DateTableCellFactory<PersonneQcm>());
        noteColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNote() + " / " + param.getValue().getQcm().getBase()));
        noteColumn.setCellFactory(param -> {
            TableCell cell = new TableCell<PersonneQcm, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new Label(item));
                    }
                }
            };
            cell.setAlignment(Pos.CENTER_RIGHT);
            return cell;
        });
    }

    public void setPersonne(Personne p) {
        this.personne = p;
    }

    public void buildTable() {
        if (personne == null) {
            return;
        }
        qcmDiagram.setPersonne(personne);
        qcmDiagramPane.getChildren().setAll(qcmDiagram.createChart());
        qcmTable.setItems(FXCollections.observableArrayList(personne.getPersonneQcms()));
    }

    public void printAction(ActionEvent event) {
    }

    public void deaffecterQcmAction(ActionEvent event) {
        PersonneQcm personneQcm = qcmTable.getSelectionModel().getSelectedItem();
        if (personneQcm != null) {
            Model<PersonneQcm> model = new Model<>("PersonneQcm");
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return model.delete(personneQcm);
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event12 -> {
                if (task.getValue()) {
                    ServiceproUtil.notify("Deaffectation réussie");
                    qcmTable.getItems().remove(personneQcm);
                    personne.setPersonneQcms(qcmTable.getItems());
                    qcmDiagramPane.getChildren().setAll(qcmDiagram.createChart());
                } else {
                    ServiceproUtil.notify("Erreur de deaffectation de test");
                }
            });
            task.setOnFailed(event1 -> {
                System.err.println(task.getException());
                AlertUtil.showErrorMessage(new Exception(task.getException()));
            });
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le test désaffecter");
        }
    }


    public void affecterQcmAction(ActionEvent event) {
        if (personne == null) {
            return;
        }
        Dialog<PersonneQcm> dialog = DialogUtil.dialogTemplate();
        dialog.setHeaderText("Ajouter un Test");
        DialogQcmController controller = new DialogQcmController();
        dialog.getDialogPane().setContent(controller);
        dialog.setResultConverter(param -> {
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getData();
            } else {
                return null;
            }
        });
        Optional<PersonneQcm> result = dialog.showAndWait();
        result.ifPresent(personneQcm -> {
            if (personneQcm != null) {
                Model<PersonneQcm> model = new Model<>("PersonneQcm");
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        System.out.println(personneQcm);
                        return model.save(personneQcm);
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event1 -> {
                    if (task.getValue()) {
                        Platform.runLater(() -> {
                            qcmTable.getItems().add(personneQcm);
                            personne.setPersonneQcms(qcmTable.getItems());
                            qcmDiagramPane.getChildren().setAll(qcmDiagram.createChart());
                            ServiceproUtil.notify("Affectation Réussie");
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ServiceproUtil.notify("Erreur d'ajout du test");
                            }
                        });

                    }
                });
                task.setOnFailed(event12 -> {
                    System.err.println(task.getException());
                    AlertUtil.showErrorMessage(new Exception(task.getException()));
                });
            } else {
                ServiceproUtil.notify("Erreur d'ajout de test");
            }

        });
    }

    class DialogQcmController extends AnchorPane implements Initializable {
        @FXML
        public ComboBox<Qcm> comboQcm;
        @FXML
        public TextField txtNote;

        @FXML
        public DatePicker dateqcm;


        public DialogQcmController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogQcm.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            Task<ObservableList<Qcm>> task = new Task<ObservableList<Qcm>>() {
                @Override
                protected ObservableList<Qcm> call() throws Exception {
                    return FXCollections.observableArrayList(new Model("Qcm").getList());
                }
            };
            comboQcm.itemsProperty().bind(task.valueProperty());
            HBox.setHgrow(comboQcm, Priority.ALWAYS);
            new Thread(task).start();
        }

        public PersonneQcm getData() {
            if (comboQcm.getValue() != null && !txtNote.getText().isEmpty() && personne != null) {
                PersonneQcm personneQcm = new PersonneQcm();
                personneQcm.setQcm(comboQcm.getValue());
                personneQcm.setPersonne(personne);
                java.util.Date date = new java.util.Date();
                if(dateqcm.getValue() != null) {
                    date = Date.valueOf(dateqcm.getValue());
                }
                personneQcm.setDateqcm(date);
                double note = 0;
                if(!txtNote.getText().isEmpty()){
                    note = Double.valueOf(txtNote.getText());
                }
                personneQcm.setNote(note);
                personneQcm.getId().setPersonne(personne.getIdpersonne());
                personneQcm.getId().setQcm(comboQcm.getValue().getIdqcm());
                return personneQcm;
            }
            return null;
        }

    }
}
