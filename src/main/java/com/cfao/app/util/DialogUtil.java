package com.cfao.app.util;


import com.cfao.app.StageManager;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

/**
 * Criar janelas de dialogos
 */
public class DialogUtil {

    public static  <R> javafx.scene.control.Dialog<R> dialogTemplate() {
        javafx.scene.control.Dialog<R> dialog = new javafx.scene.control.Dialog<>();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setVisible(false);
        StageManager.getContentLayout().getChildren().add(region);
        region.visibleProperty().bind(dialog.showingProperty());
        ButtonType okButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        return dialog;
    }

    public static  <R> javafx.scene.control.Dialog<R> dialogTemplate(String okButton, String cancelButton) {
        javafx.scene.control.Dialog<R> dialog = new javafx.scene.control.Dialog<>();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setVisible(false);
        StageManager.getContentLayout().getChildren().add(region);
        region.visibleProperty().bind(dialog.showingProperty());
        ButtonType ok = new ButtonType(okButton, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(cancelButton, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, cancel);

        return dialog;
    }

}
