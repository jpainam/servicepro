package com.cfao.app.controllers;

import com.cfao.app.Controller;
import com.cfao.app.StageManager;
import com.cfao.app.beans.Users;
import com.cfao.app.util.FXMLView;
import com.cfao.app.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by JP on 6/9/2017.
 */
public class LoginController implements Initializable {
    public TextField login;
    public PasswordField password;

    public void initialize(URL location, ResourceBundle resources) {

    }
    private boolean isAuthorized(){
        String login = this.login.getText();
        String pwd = this.password.getText();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String q = "FROM Users WHERE LOGIN = :login AND PASSWORD = :pwd";
        Query query = session.createQuery(q);
        query.setString("login", login);
        query.setString("pwd", pwd);
        Users user = (Users)query.uniqueResult();
        session.flush();
        session.close();
        return user != null;
    }
    public void btnConnexion(ActionEvent actionEvent) throws IOException {
        if(isAuthorized()) {
            FXMLLoader loader = new FXMLLoader();
            FileInputStream f = new FileInputStream(FXMLView.MAIN.getFXMLFile());
            Pane mainPane = loader.load(f);
            Controller controller = loader.getController();
            StageManager.setMainController(controller);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle(FXMLView.MAIN.getTitle());
            stage.getScene().setRoot(mainPane);
        }else{
            System.out.println("User not allowed");
        }
    }
}
