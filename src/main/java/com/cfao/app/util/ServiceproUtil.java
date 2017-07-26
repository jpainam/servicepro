package com.cfao.app.util;

import com.cfao.app.StageManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


/**
 * Created by JP on 6/21/2017.
 */
public class ServiceproUtil {
    public static String loggedUser = null;
    public static String loggedTime = null;

    /**
     * Definir les panel qui reste deroule dans le panel accordion
     * @param accordion
     * @param expandedPane
     */
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

    /**
     * Notification Panel
     * @param sms
     */
    public static void notify(String sms){
        NotificationPane notif = StageManager.getNotificationPane();
        notif.setText(sms);
        notif.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished( event -> notif.hide());
        delay.play();
    }

    /**
     * Toast Notification
     * @param sms
     */
    public static void toast(String sms){

    }

    /**
     * L'utilisateur connecte
     * @return
     */
    public static String getLoggedUser() {
        return loggedUser;
    }

    /**
     * Date de derniere connexion
     * @return
     */
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

    public static void link(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (URISyntaxException | IOException ex) {
            AlertUtil.showSimpleAlert("Information", "");
        }
    }

    public static void setButtonIcon(Button button, FontAwesomeIcon fontAwesomeIcon, double iconSize){
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setGlyphSize(iconSize);
        icon.setFill(Color.WHITE);
        button.setGraphic(icon);
    }
    public static void setButtonIcon(Button button, FontAwesomeIcon fontAwesomeIcon){
        setButtonIcon(button, fontAwesomeIcon, 1.5);
    }
    public static void emptyFields(Control... controls){
        for(Control control : controls){
            // TextField, TextArea
            if(control instanceof TextInputControl) {
                ((TextInputControl)control).setText("");
            }else if(control instanceof DatePicker){
                ((DatePicker)control).setValue(LocalDate.now());
            }
        }
    }
    public static void setEditable( boolean b, Control ... controls){
        for(Control control : controls){
            // TextField, TextArea
            if(control instanceof TextInputControl){
                ((TextInputControl) control).setEditable(b);
            }else if(control instanceof DatePicker){
                ((DatePicker) control).setEditable(b);
            }
        }
    }

    public static void setDisable(boolean b, Control ... controls) {
        for(Control control : controls){
            control.setDisable(b);
        }
    }
}
