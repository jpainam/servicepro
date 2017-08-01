package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.Module;
import com.cfao.app.StageManager;
import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.BreadcrumbUtil;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.ServiceproUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

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
    public StackPane notificationContent;
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
            userNameLabel.setText(ServiceproUtil.getLoggedUser());
            currentLogTimeLabel.setText(ServiceproUtil.getLoggedTime());

            Pane leftMenuPane = FXMLLoader.load(getClass().getResource("/views/menu/leftmenu.fxml"));
            shortcutContent.getChildren().setAll(leftMenuPane);

            notificationPane.getActions().addAll(new Action("Cacher/Hide", ae -> {
                notificationPane.hide();
            }));
            notificationStack.getChildren().add(notificationPane);
            StageManager.setNotificationPane(notificationPane);
            // Charger la vue accueil
            Pane accueil = FXMLLoader.load(getClass().getResource(FXMLView.ACCUEIL.getFXMLFile()));
            content.getChildren().setAll(accueil);

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
        //content.getChildren().setAll(button);
    }

    public void displayCivilite(ActionEvent actionEvent) {
        progressBar = new ProgressBar();
        Module.setCivilite(content);
    }

    public void add(ActionEvent actionEvent) {
    }

    @Override
    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    /**
     * Utiliser pour rendre les fond gris en cas d'alert
     * @return
     */
    @Override
    public StackPane getContentLayout(){
        return principalLayout;
    }
    /*
    @Override
    public Node getHighlightPane() {
        return highlightPane;
    }
*/
    @Override
    public ProgressBar getProgressBar(){
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
            content.getChildren().setAll((Node)loader.load());
        }catch(Exception ex){
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void utilisateurAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_UTILISATEUR);
    }

    public void profilAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_PROFIL);
    }

    public void societeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SOCIETE);
    }

    public void niveauEtudeaction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_NIVEAUETUDE);
    }

    public void groupeAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_GROUPE);
    }

    public void sectionAction(ActionEvent actionEvent) {
        openParameterScene(ParametreController.TAB_SECTION);
    }

    public void exitAction(ActionEvent actionEvent) {
        Pane connexionPane = null;
        try {
            connexionPane = FXMLLoader.load(getClass().getResource(FXMLView.LOGIN.getFXMLFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle(FXMLView.LOGIN.getTitle());
        stage.getScene().setRoot(connexionPane);

    }

    public void showAction(ActionEvent actionEvent) {
        breadCrumb.setSelectedCrumb("competence");
        Module.setCompetence(content);
    }

    public void addAction(ActionEvent actionEvent) {
        StageManager.loadContent("/views/competence/competence.fxml");
    }

    public void showProfil(ActionEvent actionEvent) {
        breadCrumb.setSelectedCrumb("profil");
        Module.setProfil(content);
    }

    @FXML
    public void showFormation(ActionEvent actionEvent) {
        breadCrumb.setSelectedCrumb("formation");
        Module.setFormation(content);
    }


    public void showProfilPopOver(MouseEvent mouseEvent) {
        VBox popOverContent = new VBox();
        Label profilLabel = new Label("Mon Profil");
        Label changePasswordLabel = new Label("Changer mon mot de passe");
        VBox.setMargin(profilLabel, new Insets(10, 10, 10, 10));
        VBox.setMargin(changePasswordLabel, new Insets(10, 10, 10, 10));
        popOverContent.getChildren().setAll(profilLabel, changePasswordLabel);
        profilPopOver.setCornerRadius(4);
        profilPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        profilPopOver.setContentNode(popOverContent);
        profilPopOver.show(caretLabel);
    }
}
