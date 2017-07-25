package com.cfao.app.controllers;

import com.cfao.app.StageManager;
import com.cfao.app.beans.Competence;
import com.cfao.app.beans.Profil;
import com.cfao.app.model.Model;
import com.cfao.app.model.ProfilModel;
import com.cfao.app.util.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/11/2017.
 */
public class ProfilController implements Initializable {
    public TableColumn abbreviationColumn;
    public TableColumn libelleColumn;
    public TableView<Profil> profilTable;

    public TableColumn<Competence, Boolean> fondamentalColumn;
    public TableColumn<Competence, Boolean> initialColumn;
    public TableColumn<Competence, Boolean> avanceColumn;
    public TableColumn<Competence, Boolean> expertColumn;
    public TableColumn<Competence, Competence> listecompetenceColumn;
    public TableColumn<Competence, Boolean> connaissanceColumn;
    public TableColumn<Competence, Boolean> competenceColumn;
    public TableView<Competence> competenceTable;
    public HBox researchBox;
    public AnchorPane rootPane1;
    public VBox rootPane2;
    public HBox researchBox2;
    public TabPane tabPane;
    public Tab firstTab;
    private TableView.TableViewSelectionModel<Profil> tableProfilModel;
    private SearchBox searchBox = new SearchBox();
    private SearchBox searchBox2 = new SearchBox();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnSettings();
        buildProfilTable();
        firstTab.setClosable(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        HBox.setHgrow(profilTable, Priority.ALWAYS);
        tableProfilModel = profilTable.getSelectionModel();
        tableProfilModel.selectedItemProperty().addListener(new ChangeListener<Profil>() {
            @Override
            public void changed(ObservableValue<? extends Profil> observable, Profil oldValue, Profil newValue) {
                Profil profil = profilTable.getSelectionModel().getSelectedItem();
                ProfilController.this.fillCompetenceTable(profil);
            }
        });

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        HBox.setHgrow(searchBox2, Priority.ALWAYS);
        searchBox.setMaxWidth(Double.MAX_VALUE);
        searchBox2.setMaxWidth(Double.MAX_VALUE);
        researchBox.setMaxWidth(Double.MAX_VALUE);
        researchBox2.setMaxWidth(Double.MAX_VALUE);
        researchBox.getChildren().setAll(new Label("Profils : "), searchBox);
        researchBox2.getChildren().setAll(new Label("Compétences associées au profil : "), searchBox2);
    }

    private void fillCompetenceTable(Profil profil) {

        System.out.println(profil.getCompetences());
        setColumnProperty(initialColumn,  ProfilModel.INITIAL);
        setColumnProperty(fondamentalColumn,  ProfilModel.FONDAMENTAL);
        setColumnProperty(avanceColumn, ProfilModel.AVANCE);
        setColumnProperty(expertColumn,  ProfilModel.EXPERT);
        competenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.COMPETENCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        competenceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        connaissanceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if (competence.getType().equals(Constante.CONNAISSANCE) || competence.getType().equals(Constante.CONNAISSANCE_COMPETENCE)) {
                return new SimpleBooleanProperty(true);
            } else {
                return new SimpleBooleanProperty(false);
            }
        });
        connaissanceColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        listecompetenceColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            return new SimpleObjectProperty<>(competence);
        });
        competenceTable.setItems(FXCollections.observableArrayList(profil.getCompetences()));

    }

    private void setColumnProperty(TableColumn<Competence, Boolean> tableColumn, int niveau) {
        tableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        tableColumn.setCellValueFactory(param -> {
            Competence competence = param.getValue();
            if(competence.getNiveau() != null) {
                if (competence.getNiveau().getIdniveau() == niveau) {
                    return new SimpleBooleanProperty(true);
                }
            }
            return new SimpleBooleanProperty(false);
        });

    }

    /**
     * Transformer les text des colonnes en vertical et definir le contenu des colonnes
     */
    private void setColumnSettings() {
        setSingleColumnSetting(fondamentalColumn);
        setSingleColumnSetting(avanceColumn);
        setSingleColumnSetting(connaissanceColumn);
        setSingleColumnSetting(competenceColumn);
        setSingleColumnSetting(expertColumn);
        setSingleColumnSetting(initialColumn);
    }

    /**
     * Transformer une colonne passer en parametre en vertical, utiliser dans setColumnSettings
     * Ajouter une rotation pour que les texte soit vertical
     *
     * @param column
     */
    private void setSingleColumnSetting(TableColumn column) {
        Label label = new Label(column.getText());
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        VBox vbox = new VBox(label);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setMaxWidth(120);
        column.setText("");
        vbox.setRotate(-90);
        Group group = new Group(vbox);
        column.setGraphic(group);
    }

    /**
     * Construire la table view profil
     */
    private void buildProfilTable() {
        abbreviationColumn.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        ProfilModel profilModel = new ProfilModel();

        Task<ObservableList<Profil>> task = new Task<ObservableList<Profil>>() {
            @Override
            protected ObservableList<Profil> call() throws Exception {
                return FXCollections.observableArrayList(profilModel.select());
            }
        };
        task.run();
        task.setOnSucceeded(event -> {
            FilteredList<Profil> filteredList = new FilteredList<Profil>(task.getValue(), p -> true);
            SortedList<Profil> sortedList = new SortedList<Profil>(filteredList);
            sortedList.comparatorProperty().bind(profilTable.comparatorProperty());
            profilTable.setItems(sortedList);
        });
    }

    public void clickNouveau(ActionEvent actionEvent) {
        try {
            Tab tab = new Tab("Nouveau profil");
            tab.setClosable(true);
            tab.setContent(new ProfilAddEditController());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);

        }
    }

    public void clickModifier(ActionEvent actionEvent) {
        if (profilTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Profil profil = profilTable.getSelectionModel().getSelectedItem();
                Tab tab = new Tab("Modification du profil " + profil.getLibelle());
                tab.setContent(new ProfilAddEditController(profil));
                tab.setClosable(true);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showErrorMessage(ex);
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le profil à modifier");
        }
    }

    public void clickSupprimer(ActionEvent actionEvent) {
        if (profilTable.getSelectionModel().getSelectedItem() != null) {
            Profil profil = profilTable.getSelectionModel().getSelectedItem();
            boolean bool = AlertUtil.showConfirmationMessage("Etes vous sûr  de vouloir supprimer\n le profil " + profil);
            if (bool) {
                Model<Profil> model = new Model<>(Model.getBeanPath("Profil"));
                if (model.delete(profil)) {
                    ServiceproUtil.notify("Suppression OK");
                    StageManager.loadContent("/views/profil/profil.fxml");
                } else {
                    ServiceproUtil.notify("Erreur de suppression");
                }
            }
        } else {
            AlertUtil.showSimpleAlert("Information", "Veuillez choisir le profil à supprimer ");
        }
    }

    public void clickAnnuler(ActionEvent actionEvent) {
        Tab tab = tabPane.getTabs().get(1);
        tabPane.getSelectionModel().select(tab);
    }


}
