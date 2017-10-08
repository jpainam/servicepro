package com.cfao.app.controllers;

import com.cfao.app.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.awt.*;

/**
 * Created by JP on 10/8/2017.
 */
public class Notification {
    private Dialog dialog;

    public Notification() {
        dialog = new Dialog();
        dialog.initStyle(StageStyle.UNDECORATED);
        // size of the screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setX(screenSize.width - dialog.getWidth());
        dialog.setY(screenSize.height - 40 - dialog.getHeight());
        dialog.getDialogPane().setContent(new NotificationController());
    }

    public void show() {
        dialog.show();
    }

    class NotificationController extends AnchorPane {
        @FXML
        public Label lblSujet;
        @FXML
        public ScrollPane scrollTache;
        @FXML
        public Button btnClose;

        public NotificationController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/notification/notification.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }

        @FXML
        public void closeAction(ActionEvent event) {
            dialog.close();
        }


    }

}