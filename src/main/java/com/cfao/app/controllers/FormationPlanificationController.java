package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.*;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.reports.FormationExcel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by JP on 8/29/2017.
 */
public class FormationPlanificationController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(FormationPlanificationController.class);

    public StackPane planificationStackPane;
    public Formation formation = null;
    public TableView<Planification> planificationTable;
    public TableColumn<Planification, String> commentaireColumn;
    public TableColumn<Planification, String> remarqueColumn;
    public TableColumn<Planification, UserProfil> validationColumn;
    public TableColumn<Planification, ObservableList<Document>> documentColumn;
    public TableColumn<Planification, Boolean> faitColumn;
    public TableColumn<Planification, UserProfil> responsableColumn;
    public TableColumn<Planification, Planification> dateColumn;
    public TableColumn<Planification, Integer> dureeColumn;
    public TableColumn<Planification, ObservableList<Tache>> tacheColumn;
    public TableColumn<Planification, Sujet> sujetColumn;
    public TableColumn<Planification, Void> numeroColumn;

    public Button btnAjouter;
    public Button btnSupprimer;
    public Button btnEditer;
    /* public Button btnImporterExcel;*/
    public Button btnExporterExcel;
    private boolean btnStateEditer = false;

    public Button btnGenererPlanification;

    public FormationPlanificationController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/planification.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        planificationTable.setTableMenuButtonVisible(true);
        planificationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initComponents();
        ButtonUtil.delete(btnSupprimer);
        ButtonUtil.plusIcon(btnAjouter);
        ButtonUtil.edit(btnEditer);
        ButtonUtil.excel(btnExporterExcel);
        GlyphsDude.setIcon(btnGenererPlanification, FontAwesomeIcon.CALENDAR);

    }

    private void initComponents() {
        numeroColumn.setCellFactory(col -> {
            TableCell<Planification, Void> cell = new TableCell<>();
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
        documentColumn.setCellFactory(new Callback<TableColumn<Planification, ObservableList<Document>>, TableCell<Planification, ObservableList<Document>>>() {
            @Override
            public TableCell<Planification, ObservableList<Document>> call(TableColumn<Planification, ObservableList<Document>> param) {
                return new TableCell<Planification, ObservableList<Document>>() {
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
        faitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planification, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Planification, Boolean> param) {
                BooleanProperty value = param.getValue().faitProperty();
                Planification planification = param.getValue();
                value.addListener((observable, oldValue, newValue) -> planification.setFait(newValue));
                return value;
            }
        });
        faitColumn.setCellFactory(new Callback<TableColumn<Planification, Boolean>, TableCell<Planification, Boolean>>() {
            @Override
            public TableCell<Planification, Boolean> call(TableColumn<Planification, Boolean> param) {
                CheckBoxTableCell<Planification, Boolean> cell = new CheckBoxTableCell<Planification, Boolean>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            if (item) {
                                Iterator<String> iterator = getStyleClass().iterator();
                                while (iterator.hasNext()) {
                                    String style = iterator.next();
                                    if (style.equals("non-fait"))
                                        iterator.remove();
                                }
                            } else {
                                getStyleClass().add("non-fait");
                            }
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        dureeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTiming()));
        dureeColumn.setCellFactory(new Callback<TableColumn<Planification, Integer>, TableCell<Planification, Integer>>() {
            @Override
            public TableCell<Planification, Integer> call(TableColumn<Planification, Integer> param) {
                return new TableCell<Planification, Integer>() {
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
        tacheColumn.setCellFactory(new Callback<TableColumn<Planification, ObservableList<Tache>>, TableCell<Planification, ObservableList<Tache>>>() {
            @Override
            public TableCell<Planification, ObservableList<Tache>> call(TableColumn<Planification, ObservableList<Tache>> param) {
                return new TableCell<Planification, ObservableList<Tache>>() {
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
        dateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
        dateColumn.setCellFactory(new Callback<TableColumn<Planification, Planification>, TableCell<Planification, Planification>>() {
            @Override
            public TableCell<Planification, Planification> call(TableColumn<Planification, Planification> param) {
                TableCell<Planification, Planification> cell = new TableCell<Planification, Planification>() {
                    @Override
                    protected void updateItem(Planification item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            Date debut = formation.getDatedebut();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(debut);
                            cal.add(Calendar.DATE, item.getTiming());
                            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy");
                            setGraphic(new Text(format.format(cal.getTime())));
                            setAlignment(Pos.CENTER_RIGHT);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });

    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void buildTable() {
        //System.err.println(formation.getPlanifications());
        if (formation == null)
            return;
        planificationTable.getItems().clear();
        if (formation.getPlanifications() != null) {
            //planificationTable.setItems(FXCollections.observableArrayList(formation.getPlanifications()));
            planificationTable.itemsProperty().bind(formation.planificationsProperty());
        }
    }

    public void ajouterAction(ActionEvent event) {
        if (formation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir la formation à planifier");
            return;
        }
        Dialog<Boolean> dialog = DialogUtil.dialogTemplate("Terminer", "Annuler");
        dialog.setResizable(true);
        dialog.setHeaderText("Ajouter une planification");
        PlanificationAddController controller = new PlanificationAddController();
        controller.setFormation(formation);
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
            if (planification) {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        new FormationModel().update(formation);
                        return null;
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ServiceproUtil.notify("Planification ajoutée avec succès");
                        planificationTable.refresh();
                    }
                });
                task.setOnFailed(event1 -> {
                    task.getException().printStackTrace();
                    logger.error(task.getException());
                });
            } else {
                AlertUtil.showErrorMessage("Erreur", "Erreur lors de l'ajout de la planification");
            }
        });
    }

    public void supprimerAction(ActionEvent event) {
        Planification planification = planificationTable.getSelectionModel().getSelectedItem();
        if (planification != null) {
            List<Planification> planifications = new ArrayList<>(planificationTable.getSelectionModel().getSelectedItems());
            boolean goahead = AlertUtil.showConfirmationMessage("Etes-vous sûr de vouloir supprimer cette planification");
            if (goahead) {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {

                        if (planifications.size() > 1) {
                            return new Model<Planification>("Planification").delete(planifications);
                        } else {
                            return new Model<Planification>("Planification").delete(planification);
                        }
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event12 -> {
                    if (task.getValue()) {
                        if (planifications.size() > 1) {
                            formation.getPlanifications().removeAll(planifications);
                        } else {
                            formation.getPlanifications().remove(planification);
                        }
                    } else {
                        ServiceproUtil.notify("Erreur dans la suppression de la planification");
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

    public void editerAction(ActionEvent event) {
        if (formation == null)
            return;

        if (!btnStateEditer) {
            planificationTable.setEditable(true);
            btnStateEditer = true;
            btnEditer.setText(ResourceBundle.getBundle("Bundle").getString("button.save"));
        } else {
            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return new FormationModel().update(formation);
                }
            };
            new Thread(task).start();
            task.setOnFailed(event1 -> {
                logger.error(task.getException());
                task.getException().printStackTrace();
            });
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Sauvegarde OK");
                    } else {
                        ServiceproUtil.notify("Erreur de sauvegarde");
                    }
                }
            });
            btnStateEditer = false;
            planificationTable.setEditable(false);
            btnEditer.setText(ResourceBundle.getBundle("Bundle").getString("button.edit"));
        }
    }

    /*public void importerExcelAction(ActionEvent event) {
        if (formation == null) {
            AlertUtil.showWarningMessage("Information", "Veuillez d'abord choisir une formation");
            return;
        }
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers Excel", "*.xls", "*.xlsx")
            );
            final File file = fileChooser.showOpenDialog(Main.stage);
            final HashMap<String, Integer> params = getColParam();
            if (file != null) {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        FormationExcel excelFormation = new FormationExcel();
                        excelFormation.setFormation(formation);
                        return excelFormation.importerPlanification(file, params);
                    }
                };
                ProgressIndicatorUtil.show(planificationStackPane, task);
                new Thread(task).start();
                task.setOnSucceeded(event1 -> {
                    if (task.getValue()) {
                        ServiceproUtil.notify("Fichier excel importé avec succès");
                    } else {
                        AlertUtil.showErrorMessage("Erreur", "Impossible d'importer le fichier excel");
                    }
                });
                task.setOnFailed(event12 -> {
                    task.getException().printStackTrace();
                    logger.error(task.getException());
                });
            }
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }*/
    /*private HashMap<String, Integer> getColParam(){
        Dialog<HashMap<String, Integer>> dialog = DialogUtil.dialogTemplate("Ok", "Annuler");
        dialog.setHeaderText("Définir les paramètre Excel");

        FormationImportExcelDialogController controller = new FormationImportExcelDialogController();
        dialog.getDialogPane().setContent(controller);
        dialog.setResultConverter(param -> {
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getData();
            } else {
                return null;
            }
        });
        Optional<HashMap<String, Integer>> result = dialog.showAndWait();
        if(result.isPresent()) {
            return result.get();
        }
        return null;
    }
*/
    public void exporterExcelAction(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FormationExcel formationExcel = new FormationExcel(formation);
                    formationExcel.printPlanification();
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
                ServiceproUtil.notify("Exportation réussie");
                StageManager.getProgressBar().progressProperty().unbind();
                StageManager.getProgressBar().setProgress(0);
            });

        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void genererPlanificationAction(ActionEvent event) {
        if (formation == null) {
            AlertUtil.showSimpleAlert("Information", "Veuillez d'abord choisir la formation");
            return;
        }
        List<Planification> planifications = formation.getPlanifications();
        if (planifications != null && planifications.size() > 0) {
            // La formation a deja ete planifier, informer l'utilisateur
            boolean goahead = AlertUtil.showConfirmationMessage("Attention", "Cette formation a déjà été planifiée.\n" +
                    "Voulez-vous réinitialiser la planification?");
            if (!goahead) {
                return;
            }
        }
        Task<ObservableList<PlanificationModele>> task = new Task<ObservableList<PlanificationModele>>() {
            @Override
            protected ObservableList<PlanificationModele> call() throws Exception {
                return FXCollections.observableArrayList(new Model<PlanificationModele>("PlanificationModele").getList());
            }
        };
        new Thread(task).start();
        task.setOnFailed(event1 -> {
            logger.error(task.getException());
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(event12 -> {
            if (task.getValue() == null) {
                AlertUtil.showWarningMessage("Impossible", "Aucun modèle de planification\n " +
                        "Vous pouvez créer un modèle de planification via le menu Paramètre");
                return;
            }
            List<Planification> tmpPlanifications = new ArrayList<>();
            Iterator<PlanificationModele> modeles = task.getValue().iterator();
            while (modeles.hasNext()) {
                PlanificationModele modele = modeles.next();
                Planification planification = new Planification();
                planification.setFait(false);
                planification.setFormation(formation);
                planification.setRemarque(modele.getRemarque());
                planification.setSujet(modele.getSujet());
                planification.setTaches(modele.getTaches());
                planification.setTiming(modele.getTiming());
                planification.setResponsable(modele.getResponsable());
                planification.setValidation(modele.getValidation());
                tmpPlanifications.add(planification);
            }
            formation.setPlanifications(tmpPlanifications);
            Task<Void> task1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    new Model<Planification>("Planification").delete(formation.getPlanifications());
                    new FormationModel().update(formation);
                    // Mettre a jour la table des planifications si non automatique
                    planificationTable.refresh();
                    return null;
                }
            };
            new Thread(task1).start();
            task1.setOnFailed(event13 -> {
                task1.getException().printStackTrace();
                logger.error(task1.getException());
            });
            task1.setOnSucceeded(event14 -> {
                ServiceproUtil.notify("Planification générée avec succès");
                planificationTable.itemsProperty().bind(formation.planificationsProperty());
            });
        });


    }

}
