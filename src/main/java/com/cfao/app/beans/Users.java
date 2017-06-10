package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 6/9/2017.
 */
public class Users {
    private SimpleIntegerProperty iduser = new SimpleIntegerProperty();
    private SimpleStringProperty login = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleIntegerProperty profile = new SimpleIntegerProperty();

    public Users(int iduser, String login, String password, int profile) {
        this.iduser.set(iduser);
        this.login.set(login);
        this.password.set(password);
        this.profile.set(profile);
    }

    public Users() {

    }

    public int getIduser() {
        return iduser.get();
    }

    public String getLogin() {
        return login.get();
    }

    public String getPassword() {
        return password.get();
    }

    public int getProfile() {
        return profile.get();
    }


    public void setIduser(int iduser) {
        this.iduser.set(iduser);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setProfile(int profile) {
        this.profile.set(profile);
    }
}
