package com.cfao.app.controllers;

import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Support;
import com.cfao.app.beans.SupportCompetence;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
public class CompetenceSupportController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(CompetenceSupportController.class);

    public Button btnAjouter;
    public Button btnSupprimer;
    public Button btnAfficher;
    public TableView<SupportCompetence> supportTable;
    public TableColumn<SupportCompetence, String> codeColumn;
    public TableColumn<SupportCompetence, String> titreColumn;
    public TableColumn<SupportCompetence, String> lienColumn;
    private Competence competence;

    public CompetenceSupportController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/competence/support.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        codeColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());
        titreColumn.setCellValueFactory(param -> param.getValue().getSupport().titreProperty());
        lienColumn.setCellValueFactory(param -> param.getValue().getSupport().lienProperty());
        GlyphsDude.setIcon(btnAfficher, FontAwesomeIcon.FILE_PDF_ALT);
        GlyphsDude.setIcon(btnAjouter, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnSupprimer, FontAwesomeIcon.MINUS_SQUARE);

    }

    public void ajouterAction(ActionEvent actionEvent) {

        Dialog<Support> dialog = DialogUtil.dialogTemplate("Ajouter", "Annuler");
        try {
            dialog.setTitle("Supports - Compétence");
            dialog.setHeaderText("Associer des supports à une compétence");
            CompetenceSupportDialogController competenceSupportDialogController = new CompetenceSupportDialogController();
            dialog.getDialogPane().setContent(competenceSupportDialogController);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    return competenceSupportDialogController.getSupport();
                else
                    return null;
            });
            Optional<Support> result = dialog.showAndWait();
            result.ifPresent(support -> {
                boolean existDeja = false;
                Iterator<SupportCompetence> iterator = competence.getSupportCompetences().iterator();
                while (!existDeja && iterator.hasNext()) {
                    SupportCompetence sp = iterator.next();
                    if (sp.getSupport().getIdsupport().equals(support.getIdsupport())) {
                        existDeja = true;
                    }
                }
                if (!existDeja) {
                    if(support.getIdsupport() == null) {
                        new Model<Support>("Support").save(support);
                    }
                    SupportCompetence sc = new SupportCompetence();
                    sc.setSupport(support);
                    sc.setCompetence(competence);
                    sc.getId().setCompetence(competence.getIdcompetence());
                    sc.getId().setSupport(support.getIdsupport());
                    sc.getCompetence().getSupportCompetences().add(sc);
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            new Model<SupportCompetence>("SupportCompetence").saveOrUpdate(sc);
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


    public void supprimerAction(ActionEvent actionEvent) {

        if (supportTable.getSelectionModel().getSelectedItem() != null) {
            SupportCompetence support = supportTable.getSelectionModel().getSelectedItem();
            //supportTable.getItems().remove(support);
            competence.getSupportCompetences().remove(support);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //new Model<Competence>("Competence").update(competence);
                    new Model<SupportCompetence>("SupportFormation").delete(support);
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
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le support à supprimer de la compétence");
        }
    }

    public void afficherAction(ActionEvent actionEvent) {
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
        if (competence.getSupportCompetences() != null) {
            supportTable.itemsProperty().bind(competence.supportCompetencesProperty());
        }
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
}
