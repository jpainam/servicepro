package com.cfao.app.util;

import javafx.scene.control.ComboBox;
import org.controlsfx.control.textfield.TextFields;

import java.util.Collection;

public class ComboAutoCompletionUtil<T> {
    public ComboAutoCompletionUtil(ComboBox<T> comboBox, Collection data) {
        comboBox.setEditable(true);
        TextFields.bindAutoCompletion(comboBox.getEditor(), data);
    }

    public static <T> T getComboBoxValue(ComboBox<T> comboBox) {
        if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
            return null;
        } else {
            return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        }
    }

}