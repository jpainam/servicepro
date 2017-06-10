package com.cfao.app.controllers;

import com.cfao.app.util.FXMLView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class PersonneController implements Initializable {
    public Tab tabCivilite;

    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(ResourceBundle.getBundle("Bundle").getString("civilite.icon"));
        tabCivilite.setGraphic(new ImageView(img));
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent civilitePane = loader.load(new FileInputStream(FXMLView.CIVILITE.getFXMLFile()));
            tabCivilite.setContent(civilitePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
