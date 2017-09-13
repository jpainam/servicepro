package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Formation;
import com.cfao.app.beans.Support;
import com.cfao.app.beans.SupportFormation;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
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
        codeSupportColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());
        fichierSupportColumn.setCellValueFactory(param -> param.getValue().getSupport().lienProperty());
        GlyphsDude.setIcon(btnAfficherSupport, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouterSupport, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimerSupport, FontAwesomeIcon.MINUS_SQUARE);

    }

    public void ajouterSupportAction(ActionEvent actionEvent) {

        javafx.scene.control.Dialog<Support> dialog = new javafx.scene.control.Dialog<>();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setVisible(false);
        StageManager.getContentLayout().getChildren().add(region);
        region.visibleProperty().bind(dialog.showingProperty());
        try {
            dialog.setTitle("Supports - Formation");
            dialog.setHeaderText("Associer des supports à la formation");
            ButtonType okButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            FormationSupportDialogController formationSupportDialogController = new FormationSupportDialogController();
            dialog.getDialogPane().setContent(formationSupportDialogController);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    return formationSupportDialogController.getSupport();
                else
                    return null;
            });
            Optional<Support> result = dialog.showAndWait();
            result.ifPresent(support -> {
                boolean existDeja = false;
                Iterator<SupportFormation> iterator = formation.getSupportFormations().iterator();
                while (!existDeja && iterator.hasNext()) {
                    SupportFormation sp = iterator.next();
                    if (sp.getSupport().getIdsupport().equals(support.getIdsupport())) {
                        existDeja = true;
                    }
                }
                if (!existDeja) {
                    SupportFormation sp = new SupportFormation();
                    sp.setSupport(support);
                    sp.setFormation(formation);
                    sp.getId().setFormation(formation.getIdformation());
                    sp.getId().setSupport(support.getIdsupport());
                    formation.getSupportFormations().add(sp);
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            new Model<SupportFormation>("SupportFormation").saveOrUpdate(sp);
                            return null;
                        }
                    };
                    new Thread(task).start();
                    task.setOnSucceeded(event -> ServiceproUtil.notify("Ajout OK"));
                    task.setOnFailed(event -> {
                        task.getException().printStackTrace();
                        logger.error(task.getException());
                    });

                }
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
                    new FormationModel().update(formation);
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
                Support support = supportTable.getSelectionModel().getSelectedItem().getSupport();
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
        if (formation.getSupportFormations() != null) {
            supportTable.itemsProperty().bind(formation.supportFormationsProperty());
        }
    }
}
