package com.cfao.app;


import com.cfao.app.util.FXMLView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.NotificationPane;


/**
 * Created by JP on 6/9/2017.
 */
public interface Controller{
    public void setContent(Node node);
    public NotificationPane getNotificationPane();
}
