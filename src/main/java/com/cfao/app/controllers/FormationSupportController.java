package com.cfao.app.controllers;

import com.cfao.app.beans.Formation;
import com.cfao.app.beans.SupportFormation;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/13/2017.
 */
public class FormationSupportController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(FormationSupportController.class);
    private Formation formation;

    public Button btnAjouterSupport;
    public Button btnSupprimerSupport;
    public Button btnAfficherSupport;
    public TableView<SupportFormation> supportTable;
    public TableColumn<SupportFormation, String> codeSupportColumn;
    public TableColumn<SupportFormation, String> titreSupportColumn;
    public TableColumn<SupportFormation, String> fichierSupportColumn;

    public FormationSupportController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/support.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        codeSupportColumn.setCellValueFactory(param -> param.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());
        fichierSupportColumn.setCellValueFactory(param -> param.getValue().lienProperty());
        GlyphsDude.setIcon(btnAfficherSupport, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouterSupport, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerSupport, FontAwesomeIcon.MINUS_SQUARE);

    }

    public void ajouterSupportAction(ActionEvent actionEvent) {

        javafx.scene.control.Dialog<SupportFormation> dialog = DialogUtil.dialogTemplate();
        try {
            dialog.setTitle("Supports - Formation");
            dialog.setHeaderText("Associer des supports à la formation");
            FormationSupportDialogController formationSupportDialogController = new FormationSupportDialogController();
            dialog.getDialogPane().setContent(formationSupportDialogController);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    return formationSupportDialogController.getSupport();
                else
                    return null;
            });
            Optional<SupportFormation> result = dialog.showAndWait();
            result.ifPresent(support -> {
                formation.getSupportFormations().add(support);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        new Model<SupportFormation>("SupportFormation").saveOrUpdate(support);
                        return null;
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event -> ServiceproUtil.notify("Ajout OK"));
                task.setOnFailed(event -> {
                    task.getException().printStackTrace();
                    logger.error(task.getException());
                });


            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }


    public void supprimerSupportAction(ActionEvent actionEvent) {

        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            SupportFormation support = supportTable.getSelectionModel().getSelectedItem();
            //supportTable.getItems().remove(support);
            formation.getSupportFormations().remove(support);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //new FormationModel().update(formation);
                    new Model<SupportFormation>("SupportFormation").delete(support);
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnFailed(event -> {
                task.getException().printStackTrace();
                logger.error(task.getException());
            });
            task.setOnSucceeded(event -> ServiceproUtil.notify("Suppression OK"));
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à supprimer de la formation");
        }
    }

    public void afficherSupportAction(ActionEvent actionEvent) {
        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            try {
                SupportFormation support = supportTable.getSelectionModel().getSelectedItem();
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
                File file = new File(path.toString(), support.getLien());
                if (file.exists() && !file.isDirectory()) {
                    ServiceproUtil.openDocument(file);
                } else {
                    AlertUtil.showWarningMessage("Fichier", "Fichier introuvable");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à afficher");
        }
    }

    public void buildTable() {
        //if (formation.getSupportFormations() != null) {
            supportTable.itemsProperty().bind(formation.supportFormationsProperty());
        //}
    }


    /**
     * Created by JP on 7/11/2017.
     */
    class FormationSupportDialogController extends AnchorPane implements Initializable {
        @FXML
        public Label fileStatus;
        @FXML
        public TextField txtCodeSupport;
        @FXML
        public TextField txtTitreSupport;
        private String destination = null;

        public FormationSupportDialogController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formation/addSupportDialog.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        }

        public SupportFormation getSupport() {
            if (txtCodeSupport.getText().isEmpty() || txtTitreSupport.getText().isEmpty() || fileStatus.getText().isEmpty()) {
                AlertUtil.showSimpleAlert("Information", "Veuillez remplir tous les champs et choisir un fichier");
                return null;
            }
            if (this.destination != null) {
                SupportFormation support = new SupportFormation();
                support.setFormation(formation);
                support.setCode(txtCodeSupport.getText());
                support.setLien(destination);
                support.setTitre(txtTitreSupport.getText());
                return support;
            }

            return null;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
        }

        @FXML
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
    }
}

