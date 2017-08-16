package com.cfao.app;


import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


/**
 * Created by JP on 6/9/2017.
 */
public interface Controller{
     void setContent(Node node);
     AnchorPane getContent();
     StackPane getContentLayout();
     ProgressBar getProgressBar();

    void setSelectedCrumb(String key);
}
