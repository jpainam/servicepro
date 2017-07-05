package com.cfao.app.beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by JP on 7/2/2017.
 */
public class Personnel {
    private SimpleIntegerProperty idpersonnel = new SimpleIntegerProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private  SimpleStringProperty adresse = new SimpleStringProperty();

    @Override
    public String toString() {
        return getNom() + " " +getPrenom();
    }

    public Personnel(){

    }
    public Personnel(SimpleIntegerProperty idpersonnel, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty adresse) {
        this.idpersonnel = idpersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public int getIdpersonnel() {
        return idpersonnel.get();
    }

    public SimpleIntegerProperty idpersonnelProperty() {
        return idpersonnel;
    }

    public void setIdpersonnel(int idpersonnel) {
        this.idpersonnel.set(idpersonnel);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }
}
