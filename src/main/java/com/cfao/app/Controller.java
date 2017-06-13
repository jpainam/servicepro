package com.cfao.app;


import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import org.controlsfx.control.NotificationPane;


/**
 * Created by JP on 6/9/2017.
 */
public interface Controller {
    void setContent(Node node);
    ProgressBar getProgressBar();
    Node getHighlightPane();
    NotificationPane getNotificationPane();
}
