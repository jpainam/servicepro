package com.cfao.app.util;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Created by JP on 7/19/2017.
 */
public class ProgressIndicatorUtil {
    Region veil = new Region();

    ProgressIndicator p = new ProgressIndicator();

    public ProgressIndicatorUtil(StackPane container, Task task) {
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        p.setMaxSize(50, 50);
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        container.getChildren().addAll(veil, p);
    }
}
