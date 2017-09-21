package com.cfao.app.controllers;

import com.cfao.app.beans.Support;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class CompetenceSupportDialogController extends AnchorPane implements Initializable {
    public Label fileStatus;
    public TableColumn<Support, String> codeSupportColumn;
    public TableColumn<Support, String> titreSupportColumn;
    public TableColumn<Support, String> fichierSupportColumn;
    public TableView<Support> supportTable;
    public TextField txtCodeSupport;
    public TextField txtTitreSupport;
    private String destination = null;

    public CompetenceSupportDialogController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/competence/addSupportDialog.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public Support getSupport() {
        if (txtCodeSupport.getText().isEmpty() || txtTitreSupport.getText().isEmpty() || fileStatus.getText().isEmpty()) {
            if (supportTable.getSelectionModel().getSelectedItem() == null) {
                AlertUtil.showSimpleAlert("Information", "Veuillez choisir un support ou remplir tous les champs et choisir un fichier");
            } else {
                return supportTable.getSelectionModel().getSelectedItem();
            }
        } else {
            if (this.destination != null) {
                System.err.println(destination);
                Support support = new Support();
                support.setCode(txtCodeSupport.getText());
                support.setLien(destination);
                support.setTitre(txtTitreSupport.getText());
                return support;
            }
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
        supportTable.setRowFactory(param -> {
            final TableRow<Support> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem viewPassportItem = new MenuItem("Ouvrir le support");
            GlyphsDude.setIcon(viewPassportItem, FontAwesomeIcon.FILE_ALT);
            viewPassportItem.setOnAction(event -> afficherSupport(row.getItem()));
            MenuItem removeItem = new MenuItem("Supprimer/Delete");
            GlyphsDude.setIcon(removeItem, FontAwesomeIcon.TRASH);
            removeItem.setOnAction(event -> supprimerSupport(row.getItem()));
            rowMenu.getItems().addAll(viewPassportItem, new SeparatorMenuItem(), removeItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    private void initComponents() {
        codeSupportColumn.setCellValueFactory(cellValue -> cellValue.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        fichierSupportColumn.setCellValueFactory(param -> param.getValue().lienProperty());
        buildSupportTable();
    }

    public void buildSupportTable() {
        Model<Support> supportModel = new Model<>("Support");

        Task<ObservableList<Support>> task = new Task<ObservableList<Support>>() {
            @Override
            protected ObservableList<Support> call() throws Exception {
                return FXCollections.observableArrayList(supportModel.getList());
            }
        };
        new Thread(task).start();
        supportTable.itemsProperty().bind(task.valueProperty());
    }

    public void choisirFichierAction(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(currentStage);
            if (file != null) {
                Path from = FileSystems.getDefault().getPath(file.getPath());
                Path to = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                if (!to.toFile().exists()) {
                    to.toFile().mkdir();
                }

                File toFile = new File(to.toString(), file.getName());
                destination = file.getName();
                Files.copy(from, toFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                fileStatus.setText(file.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void afficherSupport(Support support) {
        Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
        File file = new File(path.toString(), support.getLien());
        if (file.exists() && !file.isDirectory()) {
            ServiceproUtil.openDocument(file);
        } else {
            AlertUtil.showWarningMessage("Fichier", "Fichier introuvable");
        }
    }

    public void supprimerSupport(Support support) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                File file = new File(path.toString(), support.getLien());
                if (file.exists() && !file.isDirectory()) {
                    file.delete();
                }
                new Model<Support>("Support").delete(support);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                task.getException().printStackTrace();
            }
        });
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Suppression OK");
                supportTable.getItems().remove(support);
            }
        });
    }

}
