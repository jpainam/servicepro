package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.model.UserModel;
import com.cfao.app.util.FXMLView;

import com.cfao.app.util.SearchBox;
import com.cfao.app.util.ServiceproUtil;
import com.cfao.app.util.Toast;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;


import java.io.FileInputStream;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;


/**
 * Created by JP on 6/9/2017.
 */
public class LoginController implements Initializable {
    public TextField login;
    public PasswordField password;
    public Label errorLabel;
    public VBox connexionPane;


    public void initialize(URL location, ResourceBundle resources) {
        //connexionPane.getChildren().add(new SearchBox());
    }

    public void btnConnexion(ActionEvent actionEvent) throws IOException {
        final String login = this.login.getText();
        final String pwd = this.password.getText();
        final UserModel userModel = new UserModel();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return userModel.isAuthorized(login, pwd);
            }
        };
        task.run();
        task.setOnSucceeded(event ->{
            Platform.runLater(() ->{
                if (task.getValue()){
                    // Set static variable
                    ServiceproUtil.setLoggedUser(login);
                    ServiceproUtil.setLoggedTime(Calendar.getInstance());
                    FXMLLoader loader = new FXMLLoader();

                    Pane mainPane = null;
                    try {
                        mainPane = FXMLLoader.load(getClass().getResource(FXMLView.TEMPLATE.getFXMLFile()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Controller controller = loader.getController();
                    StageManager.setMainController(controller);
                    stage.setTitle(FXMLView.TEMPLATE.getTitle());
                    stage.getScene().setRoot(mainPane);
                }else{
                    //errorLabel.setText(ResourceBundle.getBundle("Application").getString("login.error"));
                    String toastMsg = ResourceBundle.getBundle("Application").getString("login.error");
                    int toastMsgTime = 3500; //3.5 seconds
                    int fadeInTime = 500; //0.5 seconds
                    int fadeOutTime= 500; //0.5 seconds
                    Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

                    Notifications.create().title("Connexion")
                            .text(toastMsg)
                            .showWarning();
                }
            });
        });
    }

    public void linkToCosendai(ActionEvent actionEvent) {
        ServiceproUtil.link("http://uacosendai-edu.net");
    }

    public void linkToCfao(ActionEvent actionEvent) {
        ServiceproUtil.link("");
    }
}
