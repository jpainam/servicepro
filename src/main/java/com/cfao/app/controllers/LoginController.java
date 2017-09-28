package com.cfao.app.controllers;

import com.cfao.app.Main;
import com.cfao.app.beans.Planification;
import com.cfao.app.beans.User;
import com.cfao.app.model.*;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Constante;
import com.cfao.app.util.ServiceproUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.time.Period;
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
    public static ScheduledService<ArrayList<Map<String, String>>> serviceNotification;
    public static ScheduledService<ArrayList<ObservableList<Planification>>> servicePlanification;


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
                        startServicePlanification();
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
        serviceNotification = new ScheduledService<ArrayList<Map<String, String>>>() {
            @Override
            protected Task<ArrayList<Map<String, String>>> createTask() {
                return new Task<ArrayList<Map<String, String>>>() {
                    @Override
                    protected ArrayList<Map<String, String>> call() throws Exception {
                        ArrayList<Map<String, String>> listMap = new ArrayList<>();
                        listMap.add(buildMapCivilite());
                        listMap.add(buildMapFormation());
                        listMap.add(buildMapFormateur());
                        listMap.add(buildMapProfil());
                        listMap.add(buildMapPlanification());
                        return listMap;
                    }
                };
            }

            private Map<String, String> buildMapProfil() {
                Map<String, String> labelsProfil = new HashMap<>();
                ProfilModel profilModel = new ProfilModel();
                labelsProfil.put("info1", String.valueOf(profilModel.getList().size()));
                labelsProfil.put("nbCompetence", new CompetenceModel().getList().size() + "");
                labelsProfil.put("nbNiveau", new Model<>("Niveau").getList().size() + "");
                labelsProfil.put("nbTest", new Model<>("Qcm").getList().size() + "");
                return labelsProfil;
            }

            private Map<String, String> buildMapFormateur() {
                Map<String, String> labelsformateur = new HashMap<>();
                PersonnelModel personnelModel = new PersonnelModel();
                labelsformateur.put("info1", String.valueOf(personnelModel.countFormateurs()));
                return labelsformateur;
            }

            private Map<String, String> buildMapFormation() {
                Map<String, String> labelsformation = new HashMap<>();
                FormationModel formationModel = new FormationModel();
                labelsformation.put("info1", String.valueOf(formationModel.getList().size()));
                labelsformation.put("info2", String.valueOf(formationModel.countFormationTerminees()));
                labelsformation.put("info3", String.valueOf(formationModel.countFormation(Constante.FORMATION_ANNULEE)));
                labelsformation.put("info4", String.valueOf(formationModel.countFormation(Constante.FORMATION_PREPARATION)));
                return labelsformation;
            }

            private Map<String, String> buildMapPlanification() {
                Map<String, String> labelsPlanifications = new HashMap<>();
                Model<Planification> model = new Model<>("Planification");
                labelsPlanifications.put("info1", String.valueOf(model.getList().size()));

                return labelsPlanifications;
            }

            private Map<String, String> buildMapCivilite() {
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


    public static void stopServiceNotification() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serviceNotification.cancel();
            }
        });
    }

    public void startServicePlanification(){
        ResourceBundle resource = ResourceBundle.getBundle("Bundle");
        servicePlanification = new ScheduledService<ArrayList<ObservableList<Planification>>>() {
            @Override
            protected Task <ArrayList<ObservableList<Planification>>> createTask() {
                return new Task<ArrayList<ObservableList<Planification>>>() {
                    @Override
                    protected ArrayList<ObservableList<Planification>> call() throws Exception {

                        ArrayList<ObservableList<Planification>> array = new ArrayList<>();
                        PlanificationModel model = new PlanificationModel();
                        ObservableList<Planification> list1, list2, list3, list4, planifications;
                        list1 = FXCollections.observableArrayList();
                        list2 = FXCollections.observableArrayList();
                        list3 = FXCollections.observableArrayList();
                        list4 = FXCollections.observableArrayList();

                        planifications = FXCollections.observableList(new PlanificationModel().getPlanificationToAlert());

                        for(Planification p: planifications){
                            LocalDate dateDebFormation = new java.sql.Date(p.getFormation().getDatedebut().getTime()).toLocalDate();
                            LocalDate dateFinFormation = new java.sql.Date(p.getFormation().getDatefin().getTime()).toLocalDate();
                            LocalDate dateactuel = LocalDate.now();
                            int timing = p.getTiming();
                            int timeAlert = Integer.valueOf(resource.getString("planification.alertTime"));

                            if(timing > 0){
                                LocalDate dateDebutSujet = dateDebFormation.plusDays(timing);
                                // sujet alert à date jj ...
                                if(Period.between(dateactuel, dateDebutSujet).getDays() >= 0 &&
                                        Period.between(dateactuel, dateDebutSujet).getDays() <= timeAlert ) {
                                    p.setDuration(Period.between(dateactuel, dateDebutSujet).getDays());
                                    list1.add(p);
                                }

                                // sujet passé de tel nombre de ...
                                if(Period.between(dateactuel, dateDebutSujet).getDays() < 0 &&
                                        Period.between(dateactuel, dateFinFormation).getDays() >= 0) {
                                    p.setDuration(Math.abs(Period.between(dateactuel, dateDebutSujet).getDays()));
                                    list2.add(p);
                                }
                            }
                            if(timing < 0){
                                LocalDate dateDebutSujet = dateDebFormation.minusDays(Math.abs(timing));
                                // sujet alert à date jj ...
                                if(Period.between(dateactuel, dateDebutSujet).getDays() >= 0 &&
                                        Period.between(dateactuel, dateDebutSujet).getDays() <= timeAlert){
                                    p.setDuration(Period.between(dateactuel, dateDebutSujet).getDays());
                                    list3.add(p);
                                }
                                // sujet passé de tel nombre de ...
                                if(Period.between(dateactuel, dateDebutSujet).getDays() < 0 &&
                                        Period.between(dateactuel, dateFinFormation).getDays() >= 0){
                                    p.setDuration(Math.abs(Period.between(dateactuel, dateDebutSujet).getDays()));
                                    list4.add(p);
                                }

                            }

                        }
                        /*
                        System.out.println(list1.size()+" list 1");
                        System.out.println(list2.size()+" list 2");
                        System.out.println(list3.size()+" list 3");
                        System.out.println(list4.size()+" list 4");
                        */
                        array.add(list1);
                        array.add(list2);
                        array.add(list3);
                        array.add(list4);

                        return array;
                    }

                };
            }
        };
        servicePlanification.setPeriod(Duration.hours(2));
        servicePlanification.setRestartOnFailure(true);
        servicePlanification.setMaximumFailureCount(50);
        servicePlanification.start();

    }

    public static void stopServicePlanification() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                servicePlanification.cancel();
            }
        });

    }

}
