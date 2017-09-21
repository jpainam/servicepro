package com.cfao.app.controllers;

import com.cfao.app.beans.Planification;
import com.cfao.app.beans.Tache;
import com.cfao.app.util.AlertUtil;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 9/21/2017.
 */
public class PlanificationNotificationController extends AnchorPane implements Initializable {
    static Logger logger = Logger.getLogger(PlanificationNotificationController.class);

    public Label txtSujet;
    public Label txtTiming;
    public TableView<Tache> tacheTable;
    public TableColumn<Tache, Void> numeroColumn;
    public TableColumn<Tache, String> libelleColumn;

    private Planification planification;

    public PlanificationNotificationController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planification/notification.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }catch (Exception ex){
            AlertUtil.showErrorMessage(ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    private void initComponents() {
        numeroColumn.setCellFactory(col -> {
            TableCell<Tache, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        libelleColumn.setCellValueFactory(param -> param.getValue().libelleProperty());
    }

    public void setPlanification(Planification planification) {
        this.planification = planification;
    }
}
