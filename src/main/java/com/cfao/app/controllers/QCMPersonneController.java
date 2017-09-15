package com.cfao.app.controllers;

import com.cfao.app.beans.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.SearchBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 8/12/2017.
 */
public class QCMPersonneController extends AnchorPane implements Initializable{

    private Qcm qcm = null;
    public TableView<PersonneQcm> personneTable;
    public TableColumn<PersonneQcm, String> matriculePersonneColumn;
    public TableColumn<PersonneQcm, String> nomPersonneColumn;
    public TableColumn<PersonneQcm, Societe> societePersonneColumn;
    public TableColumn<PersonneQcm, String> notePersonneColumn;
    public TableColumn<PersonneQcm, Groupe> groupePersonneColumn;
    public TableColumn<PersonneQcm, Section> sectionPersonneColumn;


    public VBox searchBoxPersonne;
    public SearchBox searchBox = new SearchBox();

    public QCMPersonneController(Qcm qcm){
        this();
        this.qcm = qcm;
    }
    public QCMPersonneController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/qcm/personne.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBoxPersonne.getChildren().addAll(new Label("Personnes"), searchBox);
        matriculePersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().matriculeProperty());
        nomPersonneColumn.setCellValueFactory(param -> {
            Personne p = param.getValue().getPersonne();
            return new SimpleStringProperty(p.getNom() + " " + p.getPrenom());
        });
        societePersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().societe());
        groupePersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().groupeProperty());
        sectionPersonneColumn.setCellValueFactory(param -> param.getValue().getPersonne().sectionProperty());
        notePersonneColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNote() + " / " + param.getValue().getQcm().getBase()));
        notePersonneColumn.setCellFactory(param -> {
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

    public void setQcm(Qcm qcm){
        this.qcm = qcm;
    }
    public void buildPersonneTable(){
        personneTable.setItems(FXCollections.observableArrayList(qcm.getPersonneQcms()));
    }
}
