package com.cfao.app;


import javafx.scene.Node;
import javafx.scene.control.ProgressBar;


/**
 * Created by JP on 6/9/2017.
 */
public interface Controller {
    void setContent(Node node);
    ProgressBar getProgressBar();
}
