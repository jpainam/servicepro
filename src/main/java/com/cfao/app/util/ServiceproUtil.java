package com.cfao.app.util;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


/**
 * Created by JP on 6/21/2017.
 */
public class ServiceproUtil {
    public static String loggedUser = null;
    public static String loggedTime = null;
    public static void setAccordionExpanded(Accordion accordion, TitledPane expandedPane) {
        accordion.setExpandedPane(expandedPane);
        accordion.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) -> {
            // This value will change to false if there's (at least) one pane that is in "expanded" state, so we don't have to expand anything manually
            boolean expand = true;
            for(TitledPane pane: accordion.getPanes()) {
                if(pane.isExpanded()) {
                    expand = false;
                }
            }
        /* Here we already know whether we need to expand the old pane again */
            if((expand == true) && (oldPane != null)) {
                Platform.runLater( () -> {
                    accordion.setExpandedPane(oldPane);
                });
            }
        });
    }

    public static String getLoggedUser() {
        return loggedUser;
    }

    public static String getLoggedTime() {
        return loggedTime;
    }
    public static void setLoggedUser(String login){
        ServiceproUtil.loggedUser = login;
    }
    public static void setLoggedTime(Calendar cal){
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        loggedTime = dateFormat.format(cal.getTime());
    }

}
