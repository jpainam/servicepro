package com.cfao.app.util;

import com.cfao.app.beans.Formation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by JP on 7/16/2017.
 */
public class DateTableCellFactory implements Callback<TableColumn<Formation, LocalDate>, TableCell<Formation, LocalDate>> {
    @Override
    public TableCell<Formation, LocalDate> call(TableColumn<Formation, LocalDate> param) {
        TableCell cell = new TableCell<Formation, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new Label(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                }
            }
        };
        cell.setAlignment(Pos.CENTER_RIGHT);
        return cell;
    }
}
