package com.cfao.app.util;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by JP on 8/22/2017.
 */
public class DateTimeTableCellFactory<T> implements javafx.util.Callback<TableColumn<T, LocalDateTime>, TableCell<T, LocalDateTime>> {
    @Override
    public TableCell<T, LocalDateTime> call(TableColumn<T, LocalDateTime> param) {
        TableCell cell = new TableCell<T, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    //Confere DateTimeFormatter Classe pour les pattern
                    setGraphic(new Label(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy k:m:s"))));
                }
            }
        };
        cell.setAlignment(Pos.CENTER_RIGHT);
        return cell;
    }

    /*@Override
    public TableCell<T, LocalDate> call(TableColumn<T, LocalDate> param) {
        return null;
    }*/
}
