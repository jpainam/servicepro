package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.Model;
import com.cfao.app.reports.ExcelFormation;
import com.cfao.app.util.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/29/2017.
 */
public class PlanificationModeleController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(PlanificationModeleController.class);
    public Button btnNouveau;
    public Button btnSupprimer;

    public Button btnExporterExcel;
    public VBox vboxSearch;
    private SearchBox searchBox = new SearchBox();

    public TableColumn<PlanificationModele, String> commentaireColumn;
    public TableColumn<PlanificationModele, String> remarqueColumn;
    public TableColumn<PlanificationModele, UserProfil> validationColumn;
    public TableColumn<PlanificationModele, ObservableList<Document>> documentColumn;
    public TableColumn<PlanificationModele, UserProfil> responsableColumn;
    public TableColumn<PlanificationModele, Integer> timingColumn;
    public TableColumn<PlanificationModele, ObservableList<Tache>> tacheColumn;
    public TableColumn<PlanificationModele, Sujet> sujetColumn;
    public TableColumn<PlanificationModele, Void> numeroColumn;

    public TableView<PlanificationModele> planificationTable;

    public PlanificationModeleController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/modele.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
            logger.error(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        vboxSearch.getChildren().setAll(new Label("Modèle de planification"), searchBox);
        initComponents();
    }

    private void initComponents() {
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.plusIcon(btnNouveau);
        ButtonUtil.excel(btnExporterExcel);
        Task<ObservableList<PlanificationModele>> task = new Task<ObservableList<PlanificationModele>>() {
            @Override
            protected ObservableList<PlanificationModele> call() throws Exception {
                return FXCollections.observableArrayList(new Model<PlanificationModele>("PlanificationModele").getList());
            }
        };
        new Thread(task).start();
        planificationTable.itemsProperty().bind(task.valueProperty());
        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            logger.error(task.getException());
        });
        numeroColumn.setCellFactory(col -> {
            TableCell<PlanificationModele, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        commentaireColumn.setCellValueFactory(param -> param.getValue().commentaireProperty());
        remarqueColumn.setCellValueFactory(param -> param.getValue().remarqueProperty());
        validationColumn.setCellValueFactory(param -> param.getValue().validation());
        responsableColumn.setCellValueFactory(param -> param.getValue().responsable());
        documentColumn.setCellValueFactory(param -> param.getValue().documents());
        documentColumn.setCellFactory(new Callback<TableColumn<PlanificationModele, ObservableList<Document>>, TableCell<PlanificationModele, ObservableList<Document>>>() {
            @Override
            public TableCell<PlanificationModele, ObservableList<Document>> call(TableColumn<PlanificationModele, ObservableList<Document>> param) {
                return new TableCell<PlanificationModele, ObservableList<Document>>() {
                    @Override
                    protected void updateItem(ObservableList<Document> item, boolean empty) {
                        super.updateItem(item, empty);
                        VBox lines = new VBox();
                        if (!empty && item != null) {
                            for (Document doc : item) {
                                lines.getChildren().add(new Text(doc.getLibelle()));
                            }
                            setGraphic(lines);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        timingColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTiming()));
        timingColumn.setCellFactory(new Callback<TableColumn<PlanificationModele, Integer>, TableCell<PlanificationModele, Integer>>() {
            @Override
            public TableCell<PlanificationModele, Integer> call(TableColumn<PlanificationModele, Integer> param) {
                return new TableCell<PlanificationModele, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Label label = new Label(Math.abs(item) + "");
                            setGraphic(label);
                            setAlignment(Pos.CENTER_RIGHT);
                        }
                    }
                };
            }
        });
        sujetColumn.setCellValueFactory(param -> param.getValue().sujetProperty());
        tacheColumn.setCellValueFactory(param -> param.getValue().tachesProperty());
        tacheColumn.setCellFactory(new Callback<TableColumn<PlanificationModele, ObservableList<Tache>>, TableCell<PlanificationModele, ObservableList<Tache>>>() {
            @Override
            public TableCell<PlanificationModele, ObservableList<Tache>> call(TableColumn<PlanificationModele, ObservableList<Tache>> param) {
                return new TableCell<PlanificationModele, ObservableList<Tache>>() {
                    @Override
                    protected void updateItem(ObservableList<Tache> item, boolean empty) {
                        super.updateItem(item, empty);
                        VBox lines = new VBox();
                        if (!empty && item != null) {
                            for (Tache tache : item) {
                                lines.getChildren().add(new Text(tache.getLibelle()));
                            }
                            setGraphic(lines);
                        } else {
                            setGraphic(null);
                        }
                    }
                };

            }
        });
    }

    public void nouveauAction(javafx.event.ActionEvent event) {
        Dialog<Boolean> dialog = DialogUtil.dialogTemplate("Terminer", "Annuler");
        dialog.setResizable(true);
        dialog.setHeaderText("Ajouter un modèle de planification");
        PlanificationAddController controller = new PlanificationAddController();
        controller.setPlanificationModeleTable(planificationTable);
        dialog.getDialogPane().setContent(controller);
        dialog.setResultConverter(param -> {
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getData();
            } else {
                return null;
            }
        });
        Optional<Boolean> result = dialog.showAndWait();
        result.ifPresent(planification -> {
            if (!planification) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Model<PlanificationModele> model = new Model<>("PlanificationModele");
                        model.truncate();
                        for (PlanificationModele pm : planificationTable.getItems()) {
                            model.save(pm);
                        }
                        return null;
                    }
                };
                new Thread(task).start();
                task.setOnFailed(event1 -> {
                    task.getException().printStackTrace();
                    logger.error(task.getException());
                });
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        StageManager.loadContent("/views/planification/planification.fxml");
                    }
                });
            } else {
                AlertUtil.showErrorMessage("Erreur", "Erreur lors de l'ajout de la planification");
            }
        });
    }

    public void exporterExcelAction(javafx.event.ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ExcelFormation excelFormation = new ExcelFormation();
                    excelFormation.printPlanificationModele();
                    return null;
                }
            };
            StageManager.getProgressBar().progressProperty().bind(task.progressProperty());
            new Thread(task).start();
            task.setOnFailed(event1 -> {
                logger.error(task.getException());
                task.getException().printStackTrace();
                ServiceproUtil.notify("Une erreur est survenue lors de l'exportation");
                StageManager.getProgressBar().progressProperty().unbind();
                StageManager.getProgressBar().setProgress(0);
            });
            task.setOnSucceeded(event12 -> {
                ServiceproUtil.notify("Impression réussie");
                StageManager.getProgressBar().progressProperty().unbind();
                StageManager.getProgressBar().setProgress(0);
            });

        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void supprimerAction(javafx.event.ActionEvent event) {
        PlanificationModele planification = planificationTable.getSelectionModel().getSelectedItem();
        if (planification != null) {
            boolean goahead = AlertUtil.showConfirmationMessage("Etes-vous sûr de vouloir supprimer cette planification");
            if (goahead) {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return new Model<PlanificationModele>("PlanificationModele").delete(planification);
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event12 -> {
                    if (task.getValue()) {
                        planificationTable.getItems().remove(planification);
                    } else {
                        ServiceproUtil.notify("Erreur dans la suppression du modele la planification");
                    }
                });
                task.setOnFailed(event1 -> {
                    task.getException().printStackTrace();
                    logger.error(task.getException());
                });
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la planification à supprimer");
        }
    }
}
