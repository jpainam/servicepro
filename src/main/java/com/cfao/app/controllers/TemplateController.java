package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.Login;
import com.cfao.app.Main;
import com.cfao.app.StageManager;
import com.cfao.app.beans.User;
import com.cfao.app.model.Model;
import com.cfao.app.util.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by JP on 6/9/2017.
 */
public class TemplateController implements Initializable, Controller {

    private static final String EMPTY_FIELD = "VIDE";
    @FXML
    public AnchorPane content;
    @FXML
    public ImageView cfaoLogo;
    public ProgressBar progressBar;
    public StackPane shortcutContent;
    //public StackPane rappelContent;
    public Button exitButton;

    public NotificationPane notificationPane = new NotificationPane(new StackPane());

    public StackPane notificationStack;

    public ToolBar breadCrumbContainer;

    public Label userLabel;
    public Label caretLabel;
    public Label userNameLabel;
    public Label currentLogTimeLabel;
    public StackPane contentPane;
    public StackPane principalLayout;


    BreadcrumbUtil breadCrumb = new BreadcrumbUtil();
    PopOver profilPopOver = new PopOver();

    static Logger logger = Logger.getLogger(TemplateController.class);

    public void initialize(URL location, ResourceBundle resources) {
        StageManager.setMainController(this);
        breadCrumbContainer.getItems().add(breadCrumb);
        try {
            cfaoLogo.setImage(new Image(getClass().getResourceAsStream(ResourceBundle.getBundle("Application").getString("app.logo"))));

            GlyphsDude.setIcon(exitButton, FontAwesomeIcon.SIGN_OUT, "1.5em");

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER);
            icon.setGlyphSize(40);
            icon.setFill(Color.DARKBLUE);
            userLabel.setGraphic(icon);
            GlyphsDude.setIcon(caretLabel, FontAwesomeIcon.CARET_DOWN);
            if (ServiceproUtil.getLoggedUser() != null) {
                userNameLabel.setText(ServiceproUtil.getLoggedUser().getLogin());
            }
            currentLogTimeLabel.setText(ServiceproUtil.getLoggedTime());

            Pane leftMenuPane = FXMLLoader.load(getClass().getResource("/views/menu/leftmenu.fxml"));
            shortcutContent.getChildren().setAll(leftMenuPane);

            notificationPane.getActions().addAll(new Action("Cacher/Hide", ae -> {
                notificationPane.hide();
            }));

            notificationStack.getChildren().add(notificationPane);
            StageManager.setNotificationPane(notificationPane);

            /*DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
            Node popupContent = datePickerSkin.getPopupContent();
            calendarPanel.getChildren().setAll(popupContent);*/
            //Pane rappelContentPane = FXMLLoader.load(getClass().getResource("/views/rappel/rappel.fxml"));
            //rappelContent.getChildren().addAll(rappelContentPane);
            // Charger la vue accueil
            Pane accueil = FXMLLoader.load(getClass().getResource(FXMLView.ACCUEIL.getFXMLFile()));
            content.getChildren().setAll(accueil);

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
        //content.getChildren().setAll(button);
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

    public void sectionAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SECTION);
    }

    public void planificationModeleAction(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/planification/modele.fxml"));
            content.getChildren().setAll(pane);
        }catch (Exception ex){
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void exitAction(ActionEvent actionEvent) {
        try {
            new Login().start(new Stage());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
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
        profilLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AlertUtil.showSimpleAlert("Implémentation", "Fonctionnalité indisponible");
            }
        });
        changePasswordLink.setOnAction(event -> {
            Dialog<ChangePassword> dialog = new Dialog<>();
            Region region = new Region();
            region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
            region.setVisible(false);
            StageManager.getContentLayout().getChildren().add(region);
            region.visibleProperty().bind(dialog.showingProperty());
            ButtonType okButton = new ButtonType("Changer", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
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

    public void showPersonnel(ActionEvent event) {
        breadCrumb.setSelectedCrumb("personnel");
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/personnel/personnel.fxml"));
            content.getChildren().setAll(pane);
        } catch (Exception ex) {
            logger.error(ex);
            AlertUtil.showErrorMessage(ex);
        }
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
}
