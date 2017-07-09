package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.Module;
import com.cfao.app.StageManager;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class TemplateController implements Initializable, Controller {
    private static TemplateController instance;

    @FXML
    public AnchorPane content;
    @FXML
    public ImageView cfaoLogo;
    public ProgressBar progressBar;
    public StackPane shortcutContent;
    public StackPane notificationContent;
    public Button exitButton;

    public NotificationPane notificationPane = new NotificationPane(new StackPane());
    public HBox highlightPane;
    public StackPane notificationStack;

    public ToolBar breadCrumbContainer;

    public Label userLabel;
    public Label caretLabel;
    public Label userNameLabel;
    public Label currentLogTimeLabel;
    public Button btnPersonne;
    public Button btnCompetence;
    public Button btnProfil;
    public Button btnTest;
    public Button btnRapport;
    public Button btnParametre;
    public Button btnFormation;
    BreadCrumbBar breadCrumb = new BreadCrumbBar();
    PopOver profilPopOver = new PopOver();


    public void initialize(URL location, ResourceBundle resources) {
        StageManager.setMainController(this);

        breadCrumbContainer.getItems().add(createBreadCrumbBar());
        try {
            cfaoLogo.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GlyphsDude.setIcon(exitButton, FontAwesomeIcon.SIGN_OUT, "1.5em");

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        icon.setGlyphSize(40);
        icon.setFill(Color.DARKBLUE);
        userLabel.setGraphic(icon);
        GlyphsDude.setIcon(caretLabel, FontAwesomeIcon.CARET_DOWN);
        userNameLabel.setText(ServiceproUtil.getLoggedUser());
        currentLogTimeLabel.setText(ServiceproUtil.getLoggedTime());
        /*try {
            FXMLLoader loader = new FXMLLoader();
            Pane leftMenuPane = loader.load(getClass().getResourceAsStream(FXMLView.LEFTMENU.getFXMLFile()));
            LeftmenuController leftmenuController = loader.getController();
            leftmenuController.setContainer(content);
            shortcutContent.getChildren().setAll(leftMenuPane);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
        notificationPane.getActions().addAll(new Action("Cacher/Hide", ae -> {
            notificationPane.hide();
        }));
        notificationStack.getChildren().add(notificationPane);
        StageManager.setNotificationPane(notificationPane);

        // Charger la vue accueil
        try {
            Pane accueil = FXMLLoader.load(getClass().getResource(FXMLView.ACCUEIL.getFXMLFile()));
            content.getChildren().setAll(accueil);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLeftMenuSettings();
        //content.getChildren().setAll(button);
    }
    public void setLeftMenuSettings(){
        setLeftMenuSetting(btnPersonne, FontAwesomeIcon.USERS);
        setLeftMenuSetting(btnCompetence, FontAwesomeIcon.SLACK);
        setLeftMenuSetting(btnParametre, FontAwesomeIcon.GEAR);
        setLeftMenuSetting(btnFormation, FontAwesomeIcon.GRADUATION_CAP);
        setLeftMenuSetting(btnRapport, FontAwesomeIcon.FILES_ALT);
        setLeftMenuSetting(btnTest, FontAwesomeIcon.BALANCE_SCALE);
        setLeftMenuSetting(btnProfil, FontAwesomeIcon.TAGS);

    }
    public void setLeftMenuSetting(Button button, FontAwesomeIcon fontAwesomeIcon){
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setGlyphSize(35);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(icon);
    }

    private BreadCrumbBar createBreadCrumbBar() {
        TreeItem<String> root = new TreeItem<String>("Accueil");

        TreeItem<String> personne = new TreeItem<String>("Personne");
        TreeItem<String> formation = new TreeItem<String>("Formation");
        TreeItem<String> competence = new TreeItem<String>("Competence");
        TreeItem<String> nouveau = new TreeItem<>("Nouveau");

        personne.getChildren().add(nouveau);

        root.getChildren().addAll(personne, formation, competence);

        breadCrumb.selectedCrumbProperty().set(nouveau);
        breadCrumb.setAutoNavigationEnabled(false);
        return breadCrumb;
    }

    public void displayCivilite(ActionEvent actionEvent) {
        Module.setCivilite(content);
    }

    public void add(ActionEvent actionEvent) {
    }

    @Override
    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }
    /*
    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public Node getHighlightPane() {
        return highlightPane;
    }
*/

    @Override
    public NotificationPane getNotificationPane() {
        return notificationPane;
    }


    public void openParameterScene(int activeTab) {
        Module.setParametre(content);
    }

    public void utilisateurAction(ActionEvent actionEvent) {
        Module.setParametre(content);
    }

    public void profilAction(ActionEvent actionEvent) {
        Module.setParametre(content);
    }

    public void societeAction(ActionEvent actionEvent) {
        Module.setParametre(content);
    }

    public void niveauEtudeaction(ActionEvent actionEvent) {
        Module.setParametre(content);
    }

    public void groupeAction(ActionEvent actionEvent) {
        Module.setParametre(content);
    }

    public void sectionAction(ActionEvent actionEvent) {
        Module.setParametre(content);
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
        Module.setCompetence(content);
    }

    public void addAction(ActionEvent actionEvent) {
        StageManager.loadContent(FXMLView.ADDCOMPETENCE.getFXMLFile());
    }

    public void showProfil(ActionEvent actionEvent) {
        Module.setProfil(content);
    }

    @FXML
    public void showFormation(ActionEvent actionEvent) {
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
