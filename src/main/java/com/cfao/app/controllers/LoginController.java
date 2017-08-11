package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.model.FormationModel;
import com.cfao.app.model.PersonneModel;
import com.cfao.app.model.UserModel;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

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
    public StackPane loadingStackContainer;


    public void initialize(URL location, ResourceBundle resources) {
        password.setOnKeyReleased((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                btnConnexion(new ActionEvent(key.getSource(), key.getTarget()));
            }
        });
    }

    private void progressIndicator(StackPane stackPane, Task task) {
        //Region veil = new Region();
        ProgressIndicator p = new ProgressIndicator();
        //veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        p.setMaxSize(35, 35);
        p.progressProperty().bind(task.progressProperty());
        //veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        stackPane.getChildren().addAll(p);
    }

    public void btnConnexion(ActionEvent actionEvent) {
        final String login = this.login.getText();
        final String pwd = this.password.getText();
        final UserModel userModel = new UserModel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                // Charger une certaine quantite de donnee
                new PersonneModel().getList();
                new FormationModel().getList();
                if (userModel.isAuthorized(login, pwd)) return true;
                else return false;
            }
        };
        progressIndicator(loadingStackContainer, task);
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                if (task.getValue()) {
                    // Set static variable
                    ServiceproUtil.setLoggedUser(login);
                    ServiceproUtil.setLoggedTime(Calendar.getInstance());
                    try {
                        new Main().start(new Stage());
                        stage.close();
                    } catch (Exception ex) {
                        AlertUtil.showErrorMessage(ex);
                    }
                } else {

                    String errorMsg = ResourceBundle.getBundle("Application").getString("login.error");
                    errorLabel.setText(errorMsg);
                    /*int toastMsgTime = 3500; //3.5 seconds
                    int fadeInTime = 500; //0.5 seconds
                    int fadeOutTime= 500; //0.5 seconds
                    Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                    */
                    Notifications.create().title("Connexion")
                            .text(errorMsg)
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

    public void minimizeAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
