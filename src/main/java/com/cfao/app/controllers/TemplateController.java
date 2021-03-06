package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.Login;
import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Personnel;
import com.cfao.app.beans.User;
import com.cfao.app.model.Model;
import com.cfao.app.model.PersonnelModel;
import com.cfao.app.model.UserModel;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by JP on 6/9/2017.
 */
public class TemplateController implements Initializable, Controller {
    @FXML
    public AnchorPane content;
    @FXML
    public ImageView cfaoLogo;
    public ProgressBar progressBar;
    public StackPane shortcutContent;
    //public StackPane rappelContent;
    public Label exitButton;

    public NotificationPane notificationPane = new NotificationPane(new StackPane());

    public StackPane notificationStack;

    public ToolBar breadCrumbContainer;

    public Label userLabel;
    public Label caretLabel;
    public Label userNameLabel;
    public Label currentLogTimeLabel;
    // public StackPane contentPane;

    /**
     * MENU
     */
    public StackPane principalLayout;
    public MenuItem menuQuitter;
    public MenuItem menuImporter;
    public MenuItem menuExporter;
    public MenuItem menuListeFormateurs;
    public MenuItem mnuMiseAjour;


    BreadcrumbUtil breadCrumb = new BreadcrumbUtil();
    PopOver profilPopOver = new PopOver();

    static Logger logger = Logger.getLogger(TemplateController.class);

    public void initialize(URL location, ResourceBundle resources) {
        StageManager.setMainController(this);
        initComponents();
        breadCrumbContainer.getItems().add(breadCrumb);
        try {
            cfaoLogo.setImage(new Image(getClass().getResourceAsStream(ResourceBundle.getBundle("Application").getString("app.logo"))));

            if (ServiceproUtil.getLoggedUser() != null) {
                userNameLabel.setText(ServiceproUtil.getLoggedUser().getLogin());
            }
            currentLogTimeLabel.setText(ServiceproUtil.getLoggedTime());
            ScrollPane leftMenuPane = FXMLLoader.load(getClass().getResource("/views/menu/leftmenu.fxml"));
            shortcutContent.getChildren().setAll(leftMenuPane);
            notificationPane.getActions().addAll(new Action("Cacher/Hide", ae -> {
                notificationPane.hide();
            }));
            notificationStack.getChildren().add(notificationPane);
            StageManager.setNotificationPane(notificationPane);
            Pane accueil = FXMLLoader.load(getClass().getResource(FXMLView.ACCUEIL.getFXMLFile()));
            content.getChildren().setAll(accueil);

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    private void initComponents() {
        FontAwesomeIconView exitIcon = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
        exitIcon.setGlyphSize(30);
        exitIcon.setFill(Color.DARKBLUE);
        exitButton.setGraphic(exitIcon);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        icon.setGlyphSize(30);
        icon.setFill(Color.DARKBLUE);
        userLabel.setGraphic(icon);
        GlyphsDude.setIcon(caretLabel, FontAwesomeIcon.CARET_DOWN);
        /** MENU CONFIGURATION */
        menuExporter.setGraphic(new ImageView(new Image("images/icons/database_go.png")));
        menuImporter.setGraphic(new ImageView(new Image("images/icons/folder_database.png")));
        menuQuitter.setGraphic(new ImageView(new Image("images/icons/sign_out.png")));
        menuListeFormateurs.setGraphic(new ImageView(new Image("images/icons/personnel.png")));
        mnuMiseAjour.setGraphic(new ImageView(new Image("images/icons/update.png")));
        /*menuQuitter.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        menuExporter.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        menuImporter.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));*/
        menuImporter.setMnemonicParsing(true);
        menuExporter.setMnemonicParsing(true);
        menuQuitter.setMnemonicParsing(true);
    }

    @Override
    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    @Override
    public AnchorPane getContent() {
        return content;
    }

    /**
     * Utiliser pour rendre les fond gris en cas d'alert
     *
     * @return
     */
    @Override
    public StackPane getContentLayout() {
        return principalLayout;
    }

    /*
    @Override
    public Node getHighlightPane() {
        return highlightPane;
    }
*/
    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void setSelectedCrumb(String key) {
        breadCrumb.setSelectedCrumb(key);
    }


    public void openParameterScene(int activeTab) {
        try {
            ParametreController parametreController = new ParametreController(activeTab);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/parametre/parametre.fxml"));
            loader.setController(parametreController);
            content.getChildren().setAll((Node) loader.load());
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void utilisateurAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_UTILISATEUR);
    }


    public void societeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SOCIETE);
    }


    public void groupeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_GROUPE);
    }
    public void agenceAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_AGENCE);
    }

    public void modeleAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_MODELE);
    }
    public void lieuAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_LIEU);
    }
    public void sectionAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SECTION);
    }
    public void domaineAction(ActionEvent actionEvent){
        openParameterScene(ParametreController.TAB_DOMAINE);
    }
    public void situationMatrimonialeAction(ActionEvent event) {
        openParameterScene(ParametreController.TAB_SITUATIONMATRIMONIALE);
    }

    public void planificationModeleAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/planification/planification.fxml"));
            content.getChildren().setAll(pane);
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }


    public void showProfilPopOver(MouseEvent mouseEvent) {
        VBox popOverContent = new VBox();
        //Label profilLink = new Label("Mon Profil");
        Hyperlink profilLink = new Hyperlink("Mon profil");
        //Label changePassword = new Label("Changer mon mot de passe");
        Hyperlink changePasswordLink = new Hyperlink("Changer mon mot de passe");
        VBox.setMargin(profilLink, new Insets(5, 5, 5, 5));
        VBox.setMargin(changePasswordLink, new Insets(5, 5, 5, 5));
        popOverContent.getChildren().setAll(profilLink, changePasswordLink);
        profilPopOver.setCornerRadius(4);
        profilPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        profilPopOver.setContentNode(popOverContent);
        profilPopOver.show(caretLabel);
        profilLink.setOnAction((ActionEvent event) -> {
            Dialog<Pair<String, Personnel>> dialog = DialogUtil.dialogTemplate("Modifier", "Annuler");
            dialog.setHeaderText("Changer le profil : " + ServiceproUtil.getLoggedUser().getLogin());
            DialogProfilController controller = new DialogProfilController();
            dialog.getDialogPane().setContent(controller);
            dialog.setResultConverter((ButtonType param) -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return controller.getData();
                } else {
                    return null;
                }
            });
            Optional<Pair<String, Personnel>> result = dialog.showAndWait();
            result.ifPresent(pairs -> {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        /** Rechercher si le nouveau login existe deja */
                        User user = new UserModel().getByLogin(pairs.getKey());
                        if (null == user) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                new Thread(task).start();
                task.setOnFailed(event1 -> {
                    logger.error(task.getException());
                    task.getException().printStackTrace();
                });
                task.setOnSucceeded(event12 -> {
                    try {
                        if (!task.getValue()) {
                            AlertUtil.showWarningMessage("Attention", "Utilisateur avec le login " +
                                    pairs.getKey() + " existe déjà\nChoisissez un autre nom d'utilisateur");
                        } else {
                            User user = ServiceproUtil.getLoggedUser();
                            user.setLogin(pairs.getKey());
                            user.setPersonnel(pairs.getValue());
                            new Model<User>("User").update(user);
                            //Model.getCurrentSession().
                            Main.stage.close();
                            new Login().start(new Stage());
                        }
                    } catch (Exception ex) {
                        logger.error(ex);
                        ex.printStackTrace();
                    }
                });
            });
        });
        changePasswordLink.setOnAction(event -> {
            Dialog<ChangePassword> dialog = DialogUtil.dialogTemplate("Changer", "Annuler");
            dialog.setHeaderText("Changer son mot de passe");
            DialogPasswordController controller = new DialogPasswordController();
            dialog.getDialogPane().setContent(controller);
            dialog.setResultConverter(param -> {
                if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return controller.getData();
                } else {
                    return null;
                }
            });
            Optional<ChangePassword> result = dialog.showAndWait();
            result.ifPresent(new Consumer<ChangePassword>() {
                @Override
                public void accept(ChangePassword changePassword) {
                    User user = ServiceproUtil.getLoggedUser();
                    if (changePassword.oldPwd.equals(user.getPassword())) {
                        user.setPassword(changePassword.newPwd);
                        new Model<>("User").update(user);
                        try {
                            new Login().start(new Stage());
                            Main.stage.close();
                        } catch (Exception e) {
                            logger.error(e);
                            AlertUtil.showErrorMessage(e);
                        }
                    } else {
                        AlertUtil.showSimpleAlert("Incorrect", "Ancien mot de passe incorrect");
                    }
                }
            });
        });
    }

    public void showFormateurs(ActionEvent event) {
        breadCrumb.setSelectedCrumb("personnel");
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/personnel/personnel.fxml"));
            content.getChildren().setAll(pane);
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void exitButton(MouseEvent mouseEvent) {
        try {
            LoginController.stopServiceNotification();
            LoginController.stopServicePlanification();
            new Login().start(new Stage());
            //Stage stage = (Stage) ((Node) .getSource()).getScene().getWindow();
            Stage stage = Main.stage;
            stage.close();
        } catch (Exception ex) {
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void importerBDAction(ActionEvent event) {
        try {
            LoginController.stopServiceNotification();
            LoginController.stopServicePlanification();

            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Import Database");
            File defaultDirectory = new File(System.getProperty("user.home"));
            chooser.setInitialDirectory(defaultDirectory);
            File selectedDirectory = chooser.showDialog(Main.stage);
            //HibernateUtil.shutdown();
            if (selectedDirectory != null) {
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("data.dir")).toAbsolutePath();
                ServiceproUtil.copyDirs(selectedDirectory, path.toFile());
                AlertUtil.showWarningMessage("Shutdown", "Redemarrer l'application");
                System.exit(0);
                //Main.stage.close();
            }
            //new Login().start(new Stage());
        } catch (Exception e) {
            AlertUtil.showErrorMessage(e);
            logger.error(e);
        }
    }

    public void exporterBDAction(ActionEvent event) {
        try {

            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Export Database");
            File defaultDirectory = new File(System.getProperty("user.home"));
            chooser.setInitialDirectory(defaultDirectory);
            File selectedDirectory = chooser.showDialog(Main.stage);
            if (selectedDirectory != null) {
                /*
                Si le dossier data se trouve dans le AppData user directory
                File appDataDir = ServiceproUtil.getAppDataDir("servicepro", true);
                File dataFile = new File(appDataDir.getAbsolutePath() + File.separator + ResourceBundle.getBundle("Bundle").getString("data.dir"));
                Path = dataFile.toPath();
                */
                Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("data.dir")).toAbsolutePath();
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
                System.err.println(path.toFile());
                //ZipUtil.zipDirectory(dataFile, zipFile);
                //ZipUtil.zipDirectory(path.toFile(), zipFile);
                ServiceproUtil.copyDirs(path.toFile(), selectedDirectory);
                ServiceproUtil.notify("Database exportée avec succès");
                ServiceproUtil.openDocument(selectedDirectory);
            }
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void quitterAction(ActionEvent event) {
        LoginController.stopServiceNotification();
        LoginController.stopServicePlanification();
        Main.tray.remove(Main.trayIcon);
        Platform.exit();
        System.exit(0);
    }

    public void miseAjourAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        VBox loading = new VBox(20);
        loading.setAlignment(Pos.CENTER);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);
        loading.getChildren().add(new ProgressBar());
        loading.getChildren().add(new Label("Check for update"));

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);
        stage.setWidth(200);
        stage.setHeight(100);
        stage.setScene(scene);
        stage.show();

        try {
            Updater updater = new Updater();
            boolean bool = updater.checkForUpdates();
            if (!bool) {
                AlertUtil.showSimpleAlert("Mises à jour", "Aucune mise à jour disponible");
            } else {
                AlertUtil.showSimpleAlert("Mises à jour", "Mises à jour disponible \n" +
                        "Redémarrer puis Exécuter Updater.exe afin de procéder à la mise à jour");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        stage.close();
    }

    public void showAccueil(MouseEvent mouseEvent) {
        StageManager.loadContent("/views/accueil/accueil.fxml");
    }



    class ChangePassword {
        public String oldPwd;
        public String newPwd;

        public ChangePassword(String o, String n) {
            this.oldPwd = o;
            this.newPwd = n;
        }
    }

    class DialogPasswordController extends AnchorPane implements Initializable {
        @FXML
        public PasswordField ancienPwd;
        @FXML
        public PasswordField newPwd;


        public DialogPasswordController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/changepwd.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        public ChangePassword getData() {
            ChangePassword cp = new ChangePassword(ancienPwd.getText(), newPwd.getText());
            return cp;
        }
    }

    class DialogProfilController extends AnchorPane implements Initializable {

        @FXML
        public ComboBox<Personnel> comboPersonnel;
        @FXML
        public TextField txtLogin;

        public DialogProfilController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/profil.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                loader.load();
            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            txtLogin.setText(ServiceproUtil.getLoggedUser().getLogin());
            Task<ObservableList<Personnel>> task = new Task<ObservableList<Personnel>>() {
                @Override
                protected ObservableList<Personnel> call() throws Exception {
                    return FXCollections.observableArrayList(new PersonnelModel().getList());
                }
            };
            new Thread(task).start();
            comboPersonnel.itemsProperty().bind(task.valueProperty());
            task.setOnFailed(event -> {
                logger.error(task.getException());
                task.getException().printStackTrace();
            });
            comboPersonnel.setValue(ServiceproUtil.getLoggedUser().getPersonnel());
        }

        public Pair<String, Personnel> getData() {
            if (txtLogin.getText() != null && !txtLogin.getText().isEmpty() && comboPersonnel.getValue() != null) {
                return new Pair<>(txtLogin.getText(), comboPersonnel.getValue());
            }
            return null;
        }
    }
}
