package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.User;
import com.cfao.app.model.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.*;


/**
 * Created by JP on 6/9/2017.
 */
public class LoginController implements Initializable {
    public TextField login;
    public PasswordField password;
    public Label errorLabel;
    public VBox connexionPane;
    public StackPane loadingStackContainer;
    public static ScheduledService<ArrayList<Map<String,String>>> serviceNotification;


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
        Task<User> task = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return userModel.isAuthorized(login, pwd);
            }
        };
        progressIndicator(loadingStackContainer, task);
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                if (task.getValue() != null) {
                    // Set static variable
                    ServiceproUtil.setLoggedUser(task.getValue());
                    ServiceproUtil.setLoggedTime(Calendar.getInstance());
                    try {
                        startServiceNotification();
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

    private void startServiceNotification() {
        serviceNotification = new ScheduledService<ArrayList<Map<String,String>>>() {
            @Override
            protected Task <ArrayList<Map<String,String>>> createTask() {
                return new Task<ArrayList<Map<String,String>>>() {
                    @Override
                    protected ArrayList<Map<String,String>> call() throws Exception {
                        ArrayList<Map<String, String>> listMap = new ArrayList<>();
                        listMap.add(buildMapCivilite());
                        listMap.add(buildMapFormation());
                        listMap.add(buildMapFormateur());
                        listMap.add(buildMapProfil());
                        return listMap;
                    }
                };
            }

            private Map<String,String> buildMapProfil() {
                Map<String, String> labelsProfil = new HashMap<>();
                ProfilModel profilModel = new ProfilModel();
                labelsProfil.put("info1", String.valueOf(profilModel.getList().size()));
                labelsProfil.put("nbCompetence", new CompetenceModel().getList().size() + "");
                labelsProfil.put("nbNiveau",new Model<>("Niveau").getList().size() + "");
                labelsProfil.put("nbTest", new Model<>("Qcm").getList().size() + "");
                return labelsProfil;
            }

            private Map<String,String> buildMapFormateur() {
                Map<String, String> labelsformateur = new HashMap<>();
                PersonnelModel personnelModel = new PersonnelModel();
                labelsformateur.put("info1", String.valueOf(personnelModel.countFormateurs()));
                return labelsformateur;
            }

            private Map<String,String> buildMapFormation() {
                Map<String, String> labelsformation = new HashMap<>();
                FormationModel formationModel = new FormationModel();
                labelsformation.put("info1", String.valueOf(formationModel.getList().size()));
                labelsformation.put("info2", String.valueOf(formationModel.countFormationTerminees()));
                labelsformation.put("info3", String.valueOf(formationModel.countFormation(Constante.FORMATION_ANNULEE)));
                labelsformation.put("info4", String.valueOf(formationModel.countFormation(Constante.FORMATION_PREPARATION)));
                return labelsformation;
            }

            private Map<String,String> buildMapCivilite() {
                Map<String, String> labelsCivilite = new HashMap<>();
                PersonneModel personneModel = new PersonneModel();
                // Nbre de civilite
                labelsCivilite.put("info1", String.valueOf(personneModel.getList().size()));
                //Nbre de dossiers incomplet
                labelsCivilite.put("info2", String.valueOf(personneModel.countPersonnePassportNull()));
                //Nbre de personne disposant des competence a certifier
                labelsCivilite.put("info3", String.valueOf(personneModel.countPersonneCompetenceEncours()));
                return labelsCivilite;
            }
        };

        serviceNotification.setPeriod(Duration.seconds(2));
        serviceNotification.setRestartOnFailure(true);
        serviceNotification.setMaximumFailureCount(50);
        serviceNotification.setBackoffStrategy(service -> Duration.seconds(service.getCurrentFailureCount() * 5));
        serviceNotification.setMaximumCumulativePeriod(Duration.minutes(10));
        serviceNotification.start();
    }
    public static void stopServiceNotification(){
        serviceNotification.cancel();
    }
}
