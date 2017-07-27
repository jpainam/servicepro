package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.Support;
import com.cfao.app.model.Model;
import com.cfao.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/11/2017.
 */
public class FormationSupportDialogController extends AnchorPane implements Initializable {
    public Label fileStatus;
    public TableColumn<Support, String> codeSupportColumn;
    public TableColumn<Support, String> titreSupportColumn;
    public TableView<Support> supportTable;
    public TextField txtCodeSupport;
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

    public Support getSupport() {
       if(txtCodeSupport.getText().isEmpty() || txtTitreSupport.getText().isEmpty() || fileStatus.getText().isEmpty()){
           if(supportTable.getSelectionModel().getSelectedItem() == null){
               AlertUtil.showSimpleAlert("Information", "Veuillez choisir un support ou remplir tous les champs nécessaires");
           }else{
               return supportTable.getSelectionModel().getSelectedItem();
           }
       }else{
           if(this.destination != null) {
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
    }

    private void initComponents() {
        codeSupportColumn.setCellValueFactory(cellValue -> cellValue.getValue().codeProperty());
        titreSupportColumn.setCellValueFactory(param -> param.getValue().titreProperty());
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
                destination = ResourceBundle.getBundle("Bundle").getString("document.dir");
                URI uri = new URI(destination + Main.DS + file.getName());
                Path to = Paths.get(uri.getPath());
                if(to.getParent() == null){
                    Files.createDirectories(to.getParent());
                }
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                fileStatus.setText(file.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
}
