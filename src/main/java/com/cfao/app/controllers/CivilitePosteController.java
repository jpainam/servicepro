package com.cfao.app.controllers;

import com.cfao.app.beans.Personne;
import com.cfao.app.beans.Poste;
import com.cfao.app.beans.Societe;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.DialogUtil;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by armel on 10/07/2017.
 */
public class CivilitePosteController extends AnchorPane implements Initializable {

    public Personne personne = null;
    public boolean btnActive = false;
    public Button btnAjouterPoste;
    public Button btnDeletePoste;

    /**
     * TableView des Postes d'une personne
     */
    public TableView<Poste> tablePoste;
    public TableColumn<Poste, String> columnPoste;
    public TableColumn<Poste, Societe> columnSociete;
    public TableColumn<Poste, LocalDate> columnDebut;
    public TableColumn<Poste, LocalDate> columnFin;

    public CivilitePosteController(Personne personne) {
        this();
        this.personne = personne;
    }

    public CivilitePosteController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/poste.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    public void setActive(boolean active){
        btnActive = active;
        ServiceproUtil.setDisable(!active, btnAjouterPoste, btnDeletePoste);
    }
    public ObservableList<Poste> getItems(){
        return tablePoste.getItems();
    }
    public void buildPoste(){
        if(personne != null) {
            tablePoste.setItems(FXCollections.observableArrayList(personne.getPostes()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlyphsDude.setIcon(btnAjouterPoste, FontAwesomeIcon.PLUS_SQUARE);
        GlyphsDude.setIcon(btnDeletePoste, FontAwesomeIcon.MINUS_SQUARE);
        ServiceproUtil.setDisable(true,  btnAjouterPoste, btnDeletePoste);

        columnPoste.setCellValueFactory(param -> param.getValue().titreProperty());
        columnSociete.setCellValueFactory(param -> param.getValue().societe());
        columnDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        columnFin.setCellValueFactory(new PropertyValueFactory<Poste, LocalDate>("datefin"));
    }

    public void addPoste(ActionEvent actionEvent) {
        if (btnActive) {
            Dialog<Poste> dialog = DialogUtil.dialogTemplate();
            dialog.setTitle("Ajouter un Poste:");
            dialog.setHeaderText("Associer des postes à la personne");
            DialogPosteController controller = new DialogPosteController();
            dialog.getDialogPane().setContent(controller);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return controller.getPoste();
                } else {
                    return null;
                }
            });
            Optional<Poste> result = dialog.showAndWait();
            result.ifPresent(new Consumer<Poste>() {
                @Override
                public void accept(Poste poste) {
                    if (poste != null) {
                        tablePoste.getItems().add(poste);
                    } else {
                        ServiceproUtil.notify("Erreur d'ajout de poste");
                    }
                }
            });
        }
    }

    public void deletePoste(ActionEvent actionEvent) {
        if (tablePoste.getSelectionModel().getSelectedItem() != null) {
            tablePoste.getItems().remove(tablePoste.getSelectionModel().getSelectedItem());
        }
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }


    class DialogPosteController extends AnchorPane implements  Initializable{
        public TextField txtPoste;
        public ComboBox<Societe> comboSociete;
        public DatePicker dateTo;
        public DatePicker dateFrom;
        public ObservableList<Societe> data = FXCollections.observableArrayList();


        public DialogPosteController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/civilite/dialog/dialogPoste.fxml"));
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
            setData();
            comboSociete.setItems(data);
            dateFrom.setValue(LocalDate.now());
            dateTo.setValue(LocalDate.now());
        }

        public Poste getPoste() {
            if (!txtPoste.getText().isEmpty() && comboSociete.getValue() != null) {
                Poste poste = new Poste();
                poste.setTitre(txtPoste.getText());
                poste.setSociete(comboSociete.getSelectionModel().getSelectedItem());
                poste.setDatedebut(Date.valueOf(dateFrom.getValue()));
                poste.setDatefin(Date.valueOf(dateTo.getValue()));
                if(personne != null) {
                    poste.setPersonne(personne);
                }
                return poste;
            }
            return null;
        }

        private void setData() {
            Task<ObservableList<Societe>> task = new Task<ObservableList<Societe>>() {
                @Override
                protected ObservableList<Societe> call() throws Exception {
                    return FXCollections.observableList(new Model<Societe>("Societe").getList());
                }
            };
            task.run();
            task.setOnSucceeded(event -> {
                data.setAll(task.getValue());
            });
        }
    }
}
